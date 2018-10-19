package com.edsson.expopromoter.api.service;

import com.edsson.expopromoter.api.model.Watch;
import com.edsson.expopromoter.api.model.json.JsonWatch;
import com.edsson.expopromoter.api.repositories.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchService {
    private final WatchRepository repository;

    @Autowired
    public WatchService(WatchRepository repository) {
        this.repository = repository;
    }

    public void signupWatch(JsonWatch jsonWatch) {
        if (jsonWatch.getIp() != null) {
            Watch watch = repository.findByIp(jsonWatch.getIp());
            if (watch == null) {
                watch = new Watch(jsonWatch);
                repository.save(watch);
            }
        }
    }
}
