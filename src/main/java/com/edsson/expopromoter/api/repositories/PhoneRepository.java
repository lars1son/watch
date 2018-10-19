package com.edsson.expopromoter.api.repositories;

import com.edsson.expopromoter.api.model.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository  extends CrudRepository<Phone,String>{
     Phone findByIp(String ip);
}
