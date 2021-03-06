package com.edsson.expopromoter.api.controller;


import com.edsson.expopromoter.api.exceptions.EntityNotFoundException;
import com.edsson.expopromoter.api.exceptions.FileNotFoundException;
import com.edsson.expopromoter.api.exceptions.RegistrationException;
import com.edsson.expopromoter.api.model.json.Message;
import com.edsson.expopromoter.api.model.json.request.*;
import com.edsson.expopromoter.api.model.json.response.GenericResponse;
import com.edsson.expopromoter.api.model.json.response.JsonApk;
import com.edsson.expopromoter.api.model.json.response.JsonUpdatedApks;
import com.edsson.expopromoter.api.service.ApkService;
import com.edsson.expopromoter.api.service.DeviceService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping("/")
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Api(value = "Admin", description = "Administrator controller")
public class AdministratorController {
    private final Logger logger = Logger.getLogger(AdministratorController.class);
    private final ApkService apkService;

    private final DeviceService deviceService;

    @Autowired
    public AdministratorController(DeviceService deviceService, ApkService apkService) {
        this.apkService = apkService;

        this.deviceService = deviceService;
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

//    @RequestMapping(value = "/get-updated", method = RequestMethod.GET)
//    public JsonUpdatedApks getUpdatedApks() throws IOException {
//        logger.info("REQUEST: /get-updated");
//        List<String> apks = apkService.getUpdatedApks();
//        return new JsonUpdatedApks(apks);
//    }
//

    @RequestMapping(value = "/get-updated", method = RequestMethod.POST)
    public JsonUpdatedApks getUpdatedApk(@RequestBody JsonUpdateApkList list) throws IOException, FileNotFoundException {
        logger.info("REQUEST: /get-updated");
        if (list.getModelList() != null) {
            ArrayList<String> apks = apkService.getUpdatedApks(list);
            return new JsonUpdatedApks(apks);
        } else {
            throw new FileNotFoundException();
        }
    }

    @RequestMapping(value = "/signup-watch", method = RequestMethod.POST)
    public GenericResponse signupWatch(@RequestBody JsonWatch jsonWatch) throws RegistrationException {
        logger.info("REQUEST: /signupWatch");
        if (jsonWatch != null)
            deviceService.signupWatch(jsonWatch);
        return new GenericResponse(Message.MESSAGE_REQUEST_SUCCESS, new String[]{});
    }

    @RequestMapping(value = "/signup-phone", method = RequestMethod.POST)
    public GenericResponse signupPhone(@RequestBody JsonPhone jsonPhone) throws RegistrationException {
        logger.info("REQUEST: /signupPhone");
        deviceService.signupPhone(jsonPhone);
        return new GenericResponse(Message.MESSAGE_REQUEST_SUCCESS, new String[]{});
    }

    @RequestMapping(value = "/tie-device", method = RequestMethod.POST)
    public GenericResponse signupDevice(@RequestBody JsonTieDevices jsonTieDevices) {
        logger.info("REQUEST: /tie-device");
        deviceService.addWatchToPhone(jsonTieDevices);
        return new GenericResponse(Message.MESSAGE_REQUEST_SUCCESS, new String[]{});
    }


    @RequestMapping(value = "/send-measured-data", method = RequestMethod.POST)
    public boolean sendMeasuredData(@RequestBody JsonData jsonData) throws EntityNotFoundException, FileNotFoundException {
        logger.info("REQUEST: /send-measured-data");
        deviceService.addMeasureForWatch(jsonData);
//        return new GenericResponse(Message.MESSAGE_REQUEST_SUCCESS,new String[]{});
        return true;
    }

    @RequestMapping(value = "/get-measured-data/{uid}", method = RequestMethod.GET)
    public JsonMeasuredData getMeasuredData(@PathVariable String uid) throws EntityNotFoundException {
        logger.info("REQUEST: /get-measured-data/" + uid);
        return JsonMeasuredData.createFromDao(deviceService.getMeasuredData(uid));
    }

    @RequestMapping(value = "/send-location", method = RequestMethod.POST)
    public boolean getMeasuredData(@RequestBody JsonLocationMessage jsonLocation) throws EntityNotFoundException {
        logger.info("REQUEST: /send-location");
        deviceService.addLocationForWatch(jsonLocation);
//        return new GenericResponse(Message.MESSAGE_REQUEST_SUCCESS,new String[]{});
        return true;
    }


    @RequestMapping(value = "/get-location/{uid}", method = RequestMethod.GET)
    public JsonLocation getLocation(@PathVariable String uid) throws EntityNotFoundException {
        logger.info("REQUEST: /get-location/" + uid);
        return JsonLocation.createFromDao(deviceService.getLocation(uid));

    }
}