package com.group.econtainer.service;

import com.group.econtainer.exception.CsvDaoException;
import com.group.econtainer.model.ContainerData;

import java.util.List;

public interface ContainerDataService {
	void add(ContainerData containerData) throws CsvDaoException;
	void remove(ContainerData containerData);
	List<ContainerData> getAll();
	void update(ContainerData containerData);
	ContainerData getLatest();
	ContainerData getById(Long id);
	boolean updateDataBaseByCsv();
}
