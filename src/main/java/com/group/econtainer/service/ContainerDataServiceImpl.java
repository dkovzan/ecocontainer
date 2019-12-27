package com.group.econtainer.service;

import com.group.econtainer.dao.CsvDao;
import com.group.econtainer.dao.ContainerDataDao;
import com.group.econtainer.exception.CsvDaoException;
import com.group.econtainer.model.ContainerData;
import com.group.econtainer.model.DataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ContainerDataServiceImpl implements ContainerDataService {

  private static final Logger logger = LoggerFactory.getLogger(ContainerDataServiceImpl.class);

  private ContainerDataDao containerDataDao;
  private CsvDao csv;
  private DataValidator validator;

  @Autowired
  public ContainerDataServiceImpl(ContainerDataDao containerDataDao, CsvDao csv, DataValidator validator) {
    this.containerDataDao = containerDataDao;
    this.csv = csv;
    this.validator = validator;
  }

  @Override
  @Transactional
  public void add(ContainerData containerData) throws CsvDaoException {
//		if (containerData.getCreated_on() != null) {
//			ApiException exception = new ApiException("ERROR Creaton_om != null");
//			throw exception;
//		}
    containerDataDao.add(containerData);
  }

  @Override
  @Transactional
  public void remove(ContainerData containerData) {
    containerDataDao.remove(containerData);
  }

  @Override
  @Transactional
  public List<ContainerData> getAll() {
    return containerDataDao.getAll();
  }

  @Override
  @Transactional
  public void update(ContainerData containerData) {
    containerDataDao.update(containerData);
  }

  @Override
  @Transactional
  public ContainerData getLatest() {
    return containerDataDao.getLatest();
  }

  @Override
  @Transactional
  public ContainerData getById(Long id) {
    return containerDataDao.getById(id);
  }

  @Override
  @Transactional
  public boolean updateDataBaseByCsv() {
    Timestamp currentTime = containerDataDao.getCurrentTimestamp();
    List<String> newRows = null;
		boolean isNewRow = false;
    try {
      newRows = csv.readNewRows(currentTime);
      List<ContainerData> containerDataUpdates = validator.CsvListToContainer(newRows);
      isNewRow = !containerDataUpdates.isEmpty();
      if (isNewRow) {
        for (ContainerData containerData :
            containerDataUpdates) {
          containerDataDao.add(containerData);
        }
      }

    } catch (CsvDaoException e) {
      String message = "Cannot update data by csv file! ";
      logger.error(message + e.getMessage() + e.getCause().getMessage());
      logger.debug(message, e);
    }
    return isNewRow;
  }
}
