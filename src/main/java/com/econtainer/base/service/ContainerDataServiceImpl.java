package com.econtainer.base.service;

import com.econtainer.base.dao.CsvDao;
import com.econtainer.base.model.ContainerData;
import com.econtainer.base.model.DataValidator;
import com.econtainer.base.repository.ContainerDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class ContainerDataServiceImpl implements com.econtainer.base.service.ContainerDataService {

    private static final Logger logger = LoggerFactory.getLogger(com.econtainer.base.service.ContainerDataServiceImpl.class);

    @Autowired
    private ContainerDataRepository containerDataRepository;
    @Autowired
    private CsvDao csv;
    @Autowired
    private DataValidator validator;

    @Override
    @Transactional
    public ContainerData getLatest() {
        //return containerDataRepository.find
                return null;
    }

    @Override
    @Transactional
    public boolean updateDataBaseByCsv() {
        Date currentTime = new Date();
        List<String> newRows = null;
        boolean isNewRow = false;
//    try {
//      newRows = csv.readNewRows(currentTime);
//      List<ContainerData> containerDataUpdates = validator.CsvListToContainer(newRows);
//      isNewRow = !containerDataUpdates.isEmpty();
//      if (isNewRow) {
//        for (ContainerData containerData :
//            containerDataUpdates) {
//          containerDataDao.add(containerData);
//        }
//      }
//
//    } catch (CsvDaoException e) {
//      String message = "Cannot update data by csv file! ";
//      logger.error(message + e.getMessage() + e.getCause().getMessage());
//      logger.debug(message, e);
//    }
        return isNewRow;
    }
}
