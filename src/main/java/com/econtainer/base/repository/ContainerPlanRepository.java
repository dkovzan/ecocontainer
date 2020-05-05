package com.econtainer.base.repository;

import com.econtainer.base.model.ContainerPlan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContainerPlanRepository extends CrudRepository<ContainerPlan, Long> {

    ContainerPlan findById(long id);

    List<ContainerPlan> findAllByContainerId(long containerId);
}
