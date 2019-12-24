package com.group.ecocontainer.dao;

import com.group.ecocontainer.exception.CsvDaoException;
import com.group.ecocontainer.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CsvDao {

  private static final Logger logger = LoggerFactory.getLogger(CsvDao.class);

  @Value("${csvFilePath}")
  private String csvFilePath;

  public List<String> readNewRows(Timestamp current) throws CsvDaoException {

    List<String> list = new ArrayList<>();

    try {
      String[] rows = FileUtils.readAsString(csvFilePath).split("\r\n");

      for (int i = 0; i < rows.length; i++) {
        int index = rows.length - 1 - i;
        Timestamp rowTs = Timestamp.valueOf(rows[index].split(",")[4].replace("T", " "));
        if (rowTs.compareTo(current) != 1) {
          break;
        }
        list.add(rows[index]);
      }

      if (!list.isEmpty()) {
        logger.info("There are new rows in the {}.", csvFilePath);
      }
    } catch (IllegalArgumentException ex) {
      throw new CsvDaoException("[CsvDao] Can't read new rows: ", ex);
    }

    return list;
  }
}
