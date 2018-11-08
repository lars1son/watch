package com.edsson.expopromoter.api.service;


import com.edsson.expopromoter.api.model.Apk;
import com.edsson.expopromoter.api.model.json.request.JsonUpdateApk;
import com.edsson.expopromoter.api.model.json.request.JsonUpdateApkList;
import com.edsson.expopromoter.api.repositories.ApkRepository;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ApkService {
    private final ApkRepository repository;
    Logger log = Logger.getLogger(ApkService.class);

    @Value("${file.url}")
    private String PATH;


    private static final int period = 7;

    @Autowired
    public ApkService(ApkRepository repository) {
        this.repository = repository;
    }

    public Apk getApk(String name) {
        Apk apk = repository.findApkByName(name);
        return apk;
    }

    //save file
    public void uploadApk(List<MultipartFile> files) throws IOException {


        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; //next pls
            }

            String url = PATH + file.getOriginalFilename();
            log.info("File name = " + file.getOriginalFilename());

            File f = new File(url);
            if (!f.exists() && !f.isDirectory()) {

                byte[] bytes = file.getBytes();
                Path path = Paths.get(url);
                Files.write(path, bytes);
                log.info("File saved to url");

                try (ApkFile apkFile = new ApkFile(new File(url))) {
                    ApkMeta apkMeta = apkFile.getApkMeta();
                    log.info(apkMeta.getLabel());
                    log.info(apkMeta.getPackageName());
                    log.info(apkMeta.getVersionCode());

                    Apk apk = repository.findApkByName(apkMeta.getPackageName());
                    apk = updateApk(apk, apkMeta);
                    repository.save(apk);
                    apkFile.close();
                }

            }



        }

    }

    private Apk updateApk(Apk apk, ApkMeta apkMeta) {
        if (apk != null) {
            if (apk.getVersion() + 1 == apkMeta.getVersionCode()) {
                apk.setVersion(apkMeta.getVersionCode());
            }
        } else {
            apk = new Apk();
            apk.setName(apkMeta.getPackageName());
            apk.setVersion(apkMeta.getVersionCode());
            apk.setPath("get-file/" + apk.getName());
            log.info("Uploaded new apk: " + apk.toString());
        }
        return apk;
    }

    public ArrayList<String> getUpdatedApks(JsonUpdateApkList list) throws IOException {
        File[] files = new File(PATH).listFiles();
        ArrayList<String> updatedApks = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (File file : files) {

//                DateTime updatePeriod = new DateTime(new Date());
//                updatePeriod = updatePeriod.minusDays(period);
//                DateTime lastUpdate = new DateTime(new Date(file.lastModified()));


//                if ((lastUpdate.isAfter(updatePeriod))) {

                    ApkFile apkFile = new ApkFile(file);
                    ApkMeta apkMeta = apkFile.getApkMeta();
                    log.info("Apk info: [name: "+ apkMeta.getPackageName()+ ",version: "+ apkMeta.getVersionCode()+"]");

                    for (JsonUpdateApk model: list.getModelList()){
                        if (model.getPackageName().trim().equals(apkMeta.getPackageName())){
                            if (model.getVersion()<apkMeta.getVersionCode()){
                                String name = apkMeta.getPackageName().replace(".apk", "");
                                if (repository.findApkByName(name) != null) {
                                    updatedApks.add(name);
                                }
                            }
                        }
                    }


//                    String name = apkMeta.getPackageName().replace(".apk", "");

                    apkFile.close();
//                }
            }
        }
        return updatedApks;
    }


    public void updateFilesInfo() throws IOException {
        log.info("Check path: " + PATH);
        File[] files = new File(PATH).listFiles();
        log.info("Files found: " + files.length);
        if (files != null && files.length > 0) {
            for (File file : files) {
                ApkFile apkFile = new ApkFile(file);
                ApkMeta apkMeta = apkFile.getApkMeta();
                String name = apkMeta.getPackageName().replace(".apk", "");
                Apk apk = repository.findApkByName(name);
                apk = updateApk(apk, apkMeta);
                repository.save(apk);
                apkFile.close();
            }
        } else {
            log.error("Empty apk storage");
        }
    }


}
