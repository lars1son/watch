package com.edsson.expopromoter.api.controller;


import com.edsson.expopromoter.api.model.Watch;
import com.edsson.expopromoter.api.model.json.JsonApk;
import com.edsson.expopromoter.api.model.json.JsonUpdatedApks;
import com.edsson.expopromoter.api.model.json.JsonWatch;
import com.edsson.expopromoter.api.service.ApkService;
import com.edsson.expopromoter.api.service.WatchService;
import io.swagger.annotations.Api;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Api(value = "Admin", description = "Administrator controller")
public class AdministratorController {
    private final Logger logger = Logger.getLogger(AdministratorController.class);
    private final ApkService apkService;
private final WatchService watchService;
    @Autowired
    public AdministratorController(WatchService watchService, ApkService apkService) {
        this.apkService = apkService;
        this.watchService = watchService;
    }

    @Value("${file.url}")
    private String PATH;

    @RequestMapping(method = RequestMethod.GET,
            value = "/info/{packageName}")
    public JsonApk getEventInfo(@PathVariable("packageName") String packageName, HttpServletRequest request, HttpResponse response) throws IOException {
        logger.info("Request: /info");
        if (packageName != null) {
            packageName = packageName.replace("-", ".");
        }
        try {
            JsonApk jsonVersion = new JsonApk(apkService.getApk(packageName));

            return jsonVersion;
        } catch (Exception e) {
            throw new InternalError();
        }
    }


    @RequestMapping(value = "/get-file/{name}", method = RequestMethod.GET, produces = "application/apk")
    public ResponseEntity<InputStreamResource> download(@PathVariable String name) throws IOException, com.edsson.expopromoter.api.exceptions.FileNotFoundException {
        name = name.replace("-", ".");
        logger.info("Request: /get-file");

        File[] files = new File(PATH).listFiles();
        for (File file : files) {
            ApkFile apkFile = new ApkFile(file);

            ApkMeta apkMeta = apkFile.getApkMeta();
            if (apkMeta.getPackageName().equals(name)) {
                InputStreamResource isResource = new InputStreamResource(new FileInputStream(file));
                FileSystemResource fileSystemResource = new FileSystemResource(file);
                String fileName = FilenameUtils.getName(file.getAbsolutePath());
                fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                headers.setContentLength(fileSystemResource.contentLength());
                headers.setContentDispositionFormData("attachment", fileName);

                logger.info("Request: /get-file  END");
                apkFile.close();
                return new ResponseEntity<InputStreamResource>(isResource, headers, HttpStatus.OK);
            }
            apkFile.close();
        }

        throw new com.edsson.expopromoter.api.exceptions.FileNotFoundException();


    }


    @RequestMapping("/upload")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile uploadfile) {

        logger.info("REQUEST: /upload");

        if (uploadfile.isEmpty()) {
            return new ResponseEntity("please select a file!", HttpStatus.OK);
        }

        try {

            apkService.uploadApk(Arrays.asList(uploadfile));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity("Successfully uploaded - " +
                uploadfile.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);

    }

    @RequestMapping("/get-updated")
    public JsonUpdatedApks getUpdatedApks() throws IOException {
        logger.info("REQUEST: /get-updated");
        List<String> apks = apkService.getUpdatedApks();
        return new JsonUpdatedApks(apks);
    }

    @RequestMapping("/signupWatch")
    public void signupWatch(@RequestBody JsonWatch jsonWatch )  {
        logger.info("REQUEST: /signupWatch");
        watchService.signupWatch(jsonWatch);
    }

    @RequestMapping("/signupPhone")
    public void signupPhone(@RequestBody JsonWatch jsonWatch )  {
        logger.info("REQUEST: /signupPhone");

        watchService.signupWatch(jsonWatch);
    }

    }