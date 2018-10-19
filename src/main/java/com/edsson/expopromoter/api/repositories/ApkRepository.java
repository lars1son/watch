package com.edsson.expopromoter.api.repositories;

import com.edsson.expopromoter.api.model.Apk;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApkRepository  extends CrudRepository<Apk, String> {
    Apk findApkByName(String name);
}
