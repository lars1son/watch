package com.edsson.expopromoter.api.repositories;

import com.edsson.expopromoter.api.model.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location,String> {
   public Location findByWatch_Id(Long watchId);
}
