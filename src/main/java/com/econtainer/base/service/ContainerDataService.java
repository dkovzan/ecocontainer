package com.econtainer.base.service;


import com.econtainer.base.model.ContainerData;

public interface ContainerDataService {
    ContainerData getLatest();
	boolean updateDataBaseByCsv();
}
