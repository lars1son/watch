package com.edsson.expopromoter.api.service;

import com.edsson.expopromoter.api.model.Phone;
import com.edsson.expopromoter.api.model.Watch;
import com.edsson.expopromoter.api.model.json.JsonPhone;
import com.edsson.expopromoter.api.model.json.JsonWatch;
import com.edsson.expopromoter.api.repositories.PhoneRepository;
import com.edsson.expopromoter.api.repositories.WatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneService {
    private final PhoneRepository repository;

    @Autowired
    public PhoneService(PhoneRepository repository) {
        this.repository = repository;
    }

    public void signupWatch(JsonPhone jsonPhone) {
        if (jsonPhone.getIp() != null) {
            Phone phone = repository.findByIp(jsonPhone.getIp());
            if (phone == null) {
                phone = new Phone(jsonPhone);
                repository.save(phone);
            }
        }
    }
}