package com.econtainer.base.repository;

import com.econtainer.base.model.ContainerData;
import org.springframework.data.repository.CrudRepository;

public interface ContainerDataRepository extends CrudRepository<ContainerData, Long> {

    ContainerData findById(long id);

    ContainerData findByContainerId(long containerId);

    ContainerData getTopByOrderByGlobalTimeDesc();
}
