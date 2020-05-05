package com.econtainer.base.repository;

import com.econtainer.base.model.PlanTemplateStage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlanTemplateStageRepository extends CrudRepository<PlanTemplateStage, Long> {

    PlanTemplateStage findById(long id);

    List<PlanTemplateStage> findAllByPlanTemplateId(Long templateId);
}
