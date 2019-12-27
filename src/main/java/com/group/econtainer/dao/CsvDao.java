package com.group.econtainer.dao;

import com.group.econtainer.exception.CsvDaoException;
import com.group.econtainer.utils.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CsvDao {

  private static final Logger logger = LoggerFactory.getLogger(CsvDao.class);

  @Value("${csvFilePath}")
  private String csvFilePath;
  @Value("${csvSchemaValues}")
  private String csvSchemaValues;
  @Value("${csvSchemaTimeControl}")
  private String csvSchemaTimeControl;
  private static Integer indexTimeControl;

  public List<String> readNewRows(Timestamp current) throws CsvDaoException {

    List<String> list = new ArrayList<>();

    try {
      String[] rows = FileUtils.readAsString(csvFilePath).split("\r\n");

      if(indexTimeControl == null) {
      indexTimeControl = Arrays.asList(csvSchemaValues.split(",")).indexOf(csvSchemaTimeControl);
      }

      for (int i = 0; i < rows.length; i++) {
        int index = rows.length - 1 - i;
        Timestamp rowTs;
        try {
          rowTs = Timestamp.valueOf(rows[index].split(",")[indexTimeControl].replace("T", " "));
          if (rowTs.compareTo(current) != 1) {
            break;
          }
        } catch (IllegalArgumentException ex) {
          logger.error("[CsvDao] Can't determine timestamp of new row: " + rows[index], ex.getMessage());
        }
        list.add(rows[index]);
      }

      if (!list.isEmpty()) {
        logger.info("There are " + list.size() + " new rows in the {}.", csvFilePath);
      }
    } catch (Exception ex) {
      throw new CsvDaoException("[CsvDao] Can't read new rows: ", ex);
    }

    return list;
  }
}
