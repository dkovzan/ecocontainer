package com.group.ecocontainer.dataReader;

import com.group.ecocontainer.utils.FileUtils;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j
public class CsvReader {

  private static final Logger log = Logger.getLogger(CsvReader.class);

  public void readCsv() {
    readCsvAsStream();
    log.info(FileUtils.readAsString("src/main/resources/containerData/data.csv"));
  }

  public Stream readCsvAsStream() {

    Timestamp current = new Timestamp(System.currentTimeMillis());

    String dataString = FileUtils.readAsString("src/main/resources/containerData/data.csv").split("\r\n")[4].split(",")[5];

    Arrays.asList(FileUtils.readAsString("src/main/resources/containerData/data.csv").split("\r\n")).stream()
        .filter(row -> Timestamp.valueOf(row.split(",")[5].replace("T"," ")).compareTo(current) == 1)
        .sorted()
        .collect(Collectors.toList());

    Timestamp curTs = Timestamp.valueOf(dataString.replace("T"," "));



    return Arrays.asList(FileUtils.readAsString("src/main/resources/containerData/data.csv").split("\r\n")).stream()
        .filter(row -> Timestamp.valueOf(row.split(",")[5].replace("T"," ")).compareTo(current) == 1);
  }

  public List<String> readCsvToArray() {

    Timestamp current = new Timestamp(System.currentTimeMillis());

    String[] rows =  FileUtils.readAsString("src/main/resources/containerData/data.csv").split("\r\n");

    List<String> list = new ArrayList<>();

    for (int i = 0; i < rows.length; i++) {
      int index = rows.length - 1 - i;
      Timestamp rowTs = Timestamp.valueOf(rows[index].split(",")[5].replace("T"," "));
      if (rowTs.compareTo(current) != 1) {
        break;
      }
      list.add(rows[index]);
    }
    return list;
  }
}
