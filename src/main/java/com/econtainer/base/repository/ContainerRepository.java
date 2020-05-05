package com.econtainer.base.repository;

import com.econtainer.base.model.Container;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ContainerRepository extends CrudRepository<Container, Long> {

    Container findById(long id);

    Container findByUuid(long uuid);

    List<Container> findAllByName(String name);
}
