package com.econtainer.base.repository;

import com.econtainer.base.model.PlanTemplate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlanTemplateRepository extends CrudRepository<PlanTemplate, Long> {

    PlanTemplate findById(long id);

    List<PlanTemplate> findAllByName(String name);
    List<PlanTemplate> findAllByPlantName(String plantName);

//    PlanTemplate findFirstByCreatedOnIsNearOrderByCreatedOnDesc(Date );
}
