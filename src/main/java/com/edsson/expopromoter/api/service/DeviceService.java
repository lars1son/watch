package com.edsson.expopromoter.api.service;

import com.edsson.expopromoter.api.controller.AdministratorController;
import com.edsson.expopromoter.api.exceptions.EntityNotFoundException;
import com.edsson.expopromoter.api.exceptions.FileNotFoundException;
import com.edsson.expopromoter.api.exceptions.RegistrationException;
import com.edsson.expopromoter.api.model.Location;
import com.edsson.expopromoter.api.model.Measures;
import com.edsson.expopromoter.api.model.Phone;
import com.edsson.expopromoter.api.model.Watch;
import com.edsson.expopromoter.api.model.json.request.*;
import com.edsson.expopromoter.api.repositories.LocationRepository;
import com.edsson.expopromoter.api.repositories.MeasureRepository;
import com.edsson.expopromoter.api.repositories.PhoneRepository;
import com.edsson.expopromoter.api.repositories.WatchRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
    private final Logger logger = Logger.getLogger(AdministratorController.class);
    private final PhoneRepository phoneRepository;
    private final WatchRepository watchRepository;
    private final MeasureRepository measureRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public DeviceService(LocationRepository locationRepository, MeasureRepository measureRepository, PhoneRepository phoneRepository, WatchRepository watchRepository) {
        this.phoneRepository = phoneRepository;
        this.watchRepository = watchRepository;
        this.measureRepository = measureRepository;
        this.locationRepository = locationRepository;

    }

    public void signupPhone(JsonPhone jsonPhone) throws RegistrationException {

        if (jsonPhone.getIp() != null) {
            Phone phone = phoneRepository.findByIp(jsonPhone.getIp());
            if (phone == null) {
                phone = new Phone(jsonPhone);
                phoneRepository.save(phone);
                logger.info("Phone: " + phone.getIp() + " registered");
            } else throw new RegistrationException();
        }
    }

    public void signupWatch(JsonWatch jsonWatch) throws RegistrationException {
        if (jsonWatch.getIp() != null) {
            Watch watch = watchRepository.findByIp(jsonWatch.getIp());
            if (watch == null) {
                watch = new Watch(jsonWatch);
                watchRepository.save(watch);
                logger.info("Watch: " + watch.getIp() + " registered");
            } else throw new RegistrationException();
        }
    }

    public void addWatchToPhone(JsonTieDevices jsonTieDevices) {

        Phone phone = phoneRepository.findByIp(jsonTieDevices.getPhoneIp());
        if (phone != null) {
            Watch watch = watchRepository.findByIp(jsonTieDevices.getWatchIp());
            if (watch != null) {
                if (phone.addWatch(watch)) {
                    watch.setPhone(phone);
                    phoneRepository.save(phone);
                }
            }
        }
    }


    public Measures getMeasuredData(String uid) throws EntityNotFoundException {
        Watch watch = watchRepository.findByIp(uid);
        if (watch != null) {
            Measures measures = watch.getMeasure();
            if (measures != null) {
                return measures;
            }
            throw new EntityNotFoundException();
        }
        throw new EntityNotFoundException();

    }


    public void addMeasureForWatch(JsonData jsonData) throws EntityNotFoundException, FileNotFoundException {
        if (jsonData.getWatchId() != null) {
            Watch watch = watchRepository.findByIp(jsonData.getWatchId());
            if (watch != null) {
                Measures measures = measureRepository.findByWatch_Id(watch.getId());
                if (measures != null) {
                    measures.setMeasures(jsonData.getMeasuredData());
                    measureRepository.save(measures);
                } else {
                    measures = new Measures(jsonData);

                    watch.setMeasure(measures);
                    measures.setWatch(watch);
                    watchRepository.save(watch);

                }
            } else throw new EntityNotFoundException();
        } else
            throw new FileNotFoundException();
    }

    public void addLocationForWatch(JsonLocationMessage jsonLocation) throws EntityNotFoundException {
        Watch watch = watchRepository.findByIp(jsonLocation.getWatchId());
        if (watch != null) {
            Location location = new Location(jsonLocation.getLocation());

//            if (locationRepository.findByWatch_Id(watch.getId()) != null) {
//                location.setWatch(watch);
//                locationRepository.save(location);
//            } else {
//                location.setWatchId(watch.getId());
            if (locationRepository.findByWatch_Id(watch.getId())!=null){
                location = locationRepository.findByWatch_Id(watch.getId());
                location.setBearing(jsonLocation.getLocation().getBearing());
                location.setLatitude(jsonLocation.getLocation().getLatitude());
                location.setLongitude(jsonLocation.getLocation().getLongitude());
                location.setProvider(jsonLocation.getLocation().getProvider());
                location.setTime(jsonLocation.getLocation().getTime());
                locationRepository.save(location);
            }
            watch.setLocation(location);
            location.setWatch(watch);
            watchRepository.save(watch);


        } else throw new

                EntityNotFoundException();

    }

    public Location getLocation(String uid) throws EntityNotFoundException {
        Watch watch = watchRepository.findByIp(uid);
        if (watch != null) {
            return watch.getLocation();
        }
        throw new EntityNotFoundException();
    }

}