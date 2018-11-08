package com.edsson.expopromoter.api.repositories;

import com.edsson.expopromoter.api.model.Measures;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasureRepository extends CrudRepository<Measures,String > {
    public Measures findByWatch_Id(Long id);
}
