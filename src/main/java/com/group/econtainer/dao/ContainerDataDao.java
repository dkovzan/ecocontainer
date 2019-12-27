package com.group.econtainer.dao;

import com.group.econtainer.model.ContainerData;

import java.sql.Timestamp;
import java.util.List;

public interface ContainerDataDao {
	void add(ContainerData containerData);
	void update(ContainerData containerData);
	void remove(ContainerData containerData);
	ContainerData getById(Long id);
	ContainerData getLatest();
	List<ContainerData> getAll();
	Timestamp getCurrentTimestamp();
}
