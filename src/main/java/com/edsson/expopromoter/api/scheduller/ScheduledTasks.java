package com.edsson.expopromoter.api.scheduller;

import com.edsson.expopromoter.api.service.ApkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ScheduledTasks {
    private static final Logger log = Logger.getLogger(ScheduledTasks.class);
    private static final long DAY = 1000*60*60*24;
    private final ApkService apkService;
    @Autowired()
    public ScheduledTasks(ApkService apkService){
        this.apkService=apkService;
    }
    @Scheduled(fixedRate = DAY)
    public void checkFilesForUpdates() {
        log.info("Start Scheduler");
        try {
            apkService.updateFilesInfo();
        } catch (IOException e) {
            log.error(e);
        }
    }
}
