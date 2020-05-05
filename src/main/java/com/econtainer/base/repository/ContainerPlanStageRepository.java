package com.econtainer.base.repository;

import com.econtainer.base.model.ContainerPlanStage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContainerPlanStageRepository extends CrudRepository<ContainerPlanStage, Long> {

    ContainerPlanStage findById(long id);

    List<ContainerPlanStage> findByContainerPlanId(Long containerPlanId);
}
