package com.edsson.expopromoter.api.repositories;

import com.edsson.expopromoter.api.model.Watch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchRepository  extends CrudRepository<Watch, String>{
}
