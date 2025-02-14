package com.econtainer.base.model;

import com.econtainer.base.exception.CsvDaoException;
import com.econtainer.base.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.econtainer.base.utils.StringUtils.matchRange;

@Component
public class DataValidator {

  @Value("${csvSchemaValues}")
  private String csvSchemaValues;
  @Value("${csvSchemaContainerIdRange}")
  private String csvSchemaContainerIdRange;
  @Value("${csvSchemaAirTempRange}")
  private String csvSchemaAirTempRange;
  @Value("${csvSchemaAirHumidityRange}")
  private String csvSchemaAirHumidityRange;
  @Value("${csvSchemaAirCo2Range}")
  private String csvSchemaAirCo2Range;
  @Value("${csvSchemaWaterPhRange}")
  private String csvSchemaWaterPhRange;
  @Value("${csvSchemaWaterEcRange}")
  private String csvSchemaWaterEcRange;

  private int csvValuesCount = 0;

  private static List<String> skippedRows = new ArrayList<>();

  private static final Logger logger = LoggerFactory.getLogger(DataValidator.class);

  public List<ContainerData> CsvListToContainer(List<String> csvRows) throws CsvDaoException {

    List<ContainerData> dataRow = new ArrayList<>();
    int size = csvRows.size();

    Map<Integer, Method> csvMap = mapSettersToCsvScheme(new ContainerData());

    List<String> csvValidationLog = new ArrayList<>();
    String errorMessage = "";

    if (!skippedRows.isEmpty()) {
      logger.error(skippedRows.size() + " invalid csv rows have been ignored: " + skippedRows.toString());
    }

    try {
      for (int i = 0; i < size; i++) {
        List<String> rowsLog = new ArrayList<>();
        int index = size - 1 - i;
        ContainerData containerData = new ContainerData();
        String csvRow = csvRows.get(index);
        String[] containerDataValues = csvRow.split(",");
        int containerDataValuesCount = containerDataValues.length;

        if (skippedRows.contains(csvRow)) {
          continue;
        }

        if (csvValuesCount == 0) {
          csvValuesCount = Arrays.asList(csvSchemaValues.split(",")).size();
        }

        if (csvValuesCount != containerDataValuesCount) {
          rowsLog.add("invalid values count (" + containerDataValuesCount
              + ") expected by scheme (" + csvValuesCount + ")");
        } else {
          Map<String, Method> rangeGetters = getRangeGetters(this);
          csvMap.forEach((ind, m) -> {
            try {
              Class cl = m.getParameterTypes()[0];
              String stringValue = containerDataValues[ind];
              String parameterName = getMethodValueName(m, new String[]{"set"});

              if (stringValue.equals("")) {
                throw new ValidationException(parameterName + " is empty!");
              } else if (stringValue.equals(null) || stringValue.equals("null")) {
                throw new ValidationException(parameterName + " is null!");
              }

              if ((cl).isInstance(1)) {
                Integer value = formatToInt(stringValue, parameterName, rowsLog);

                if (rangeGetters.containsKey(parameterName)) {
                  String schemaRange = (String) rangeGetters.get(parameterName).invoke(this);
                  validateRange(schemaRange, Long.valueOf(value), parameterName, rowsLog);
                }

                m.invoke(containerData, value);
              } else if ((cl).isInstance(1L)) {
                Long value = formatToLong(stringValue, parameterName, rowsLog);

                if (rangeGetters.containsKey(parameterName)) {
                  String schemaRange = (String) rangeGetters.get(parameterName).invoke(this);
                  validateRange(schemaRange, value, parameterName, rowsLog);
                }

                m.invoke(containerData, value);
              } else if ((cl).isInstance(new Timestamp(0L))) {
                Timestamp value = formatToTimestamp(stringValue, parameterName, rowsLog);

                validateTimestamp(value, parameterName, rowsLog);

                m.invoke(containerData, value);
              } else if ((cl).isInstance(0.1)) {
                Double value = formatToDouble(stringValue, parameterName, rowsLog);

                if (rangeGetters.containsKey(parameterName)) {
                  String schemaRange = (String) rangeGetters.get(parameterName).invoke(this);
                  validateDouble(schemaRange, value, parameterName, rowsLog);
                }

                m.invoke(containerData, value);
              } else if ((cl).isInstance(false)) {
                Boolean value = formatToBoolean(stringValue, parameterName, rowsLog);

                m.invoke(containerData, value);
              }else {
                m.invoke(containerData, stringValue);
              }
            } catch (IllegalAccessException | InvocationTargetException e) {
              e.printStackTrace();
            } catch (ValidationException e) {
              rowsLog.add(e.getMessage());
            }
          });
        }
        if (rowsLog.isEmpty()) {
          dataRow.add(containerData);
        } else {
          csvValidationLog.add(rowsLog.toString());
          skippedRows.add(csvRow);
        }
      }

      if (!csvValidationLog.isEmpty()) {
        errorMessage = "[CsvListToContainerData] CSV Validation error:\n"
            + skippedRows.size() + " invalid csv rows have been ignored\n"
            + csvValidationLog.stream().collect(Collectors.joining("\n"))
            + "\nin the following rows from csv file:\n" + skippedRows.stream().collect(Collectors.joining("\n"));

        throw new ValidationException(errorMessage);
      }

    } catch (Exception ex) {
      if (dataRow.isEmpty()) {
        throw new CsvDaoException(skippedRows.size() + " invalid csv rows have been ignored !", ex);
      }
      logger.error(ex.getMessage());
      skippedRows.clear();
      return dataRow;
    }
    skippedRows.clear();
    return dataRow;
  }

  private void validateRange(String schemaRange, Long value, String name, List<String> log) {
    if (!matchRange(schemaRange, value)) {
      log.add("invalid " + name + " (" + value
          + ") expected between (" + schemaRange + ") by csv scheme ");
    }
  }

  private void validateTimestamp(Timestamp timestamp, String name, List<String> log) {
    LocalDateTime date = timestamp.toLocalDateTime();
    LocalDateTime nowPlus12 = LocalDateTime.now().plusHours(12);
    if (nowPlus12.compareTo(date) < 1) {
      log.add("invalid timestamp " + name + " (" + timestamp
          + "). Container data cannot be from future");
    }
  }

  private void validateDouble(String schemaRange, Double value, String name, List<String> log) {
    if (!matchRange(schemaRange, value)) {
      log.add("invalid " + name + " (" + value
          + ") expected between (" + schemaRange + ") by csv scheme ");
    }
  }

  private Integer formatToInt(String value, String name, List<String> log) {
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException ex) {
      log.add("csv value  [" + name + "] = " + value + " cannot be converted to integer!");
    }
    return null;
  }

  private Long formatToLong(String value, String name, List<String> log) {
    try {
      return Long.valueOf(value);
    } catch (NumberFormatException ex) {
      log.add("csv value  [" + name + "] = " + value + " cannot be converted to long!");
    }
    return null;
  }

  private Timestamp formatToTimestamp(String value, String name, List<String> log) {
    Timestamp ts = null;
    try {
      ts =  StringUtils.stringToTimestamp(value);
    } catch (Exception ex) {
      log.add("csv value  [" + name + "] = " + value + " cannot be converted to Timestamp!");
    }
    return ts;
  }

  private Double formatToDouble(String value, String name, List<String> log) {
    try {
      return Double.valueOf(value);
    } catch (NumberFormatException ex) {
      log.add("csv value  [" + name + "] = " + value + " cannot be converted to double!");
    }
    return null;
  }


  private Boolean formatToBoolean(String value, String name, List<String> log) {
    try {
      if(value.equals("0")) {
        value = "false";
      } if(value.equals("1")) {
        value = "true";
      }
      return Boolean.valueOf(value);
    } catch (NumberFormatException ex) {
      log.add("csv value  [" + name + "] = " + value + " cannot be converted to boolean!");
    }
    return null;
  }

  private Map mapSettersToCsvScheme(Object object) {
    Class aClass = object.getClass();

    List<Method> methods = Arrays.asList(aClass.getMethods());

    Map<Integer, Method> csvMap = new HashMap<>();

    List<String> containerDataValues = Arrays.asList(csvSchemaValues.split(","));

    for (Method m :
        methods) {
      if (m.getName().contains("set")) {
        String name = getMethodValueName(m, new String[]{"set"});
        if (containerDataValues.contains(name)) {
          csvMap.put(containerDataValues.indexOf(name), m);
        } else {
          logger.warn("Csv scheme doesn't match entity fields. There is no " + name + "value in scheme");
        }
      }
    }

    return csvMap;
  }

  private Map getRangeGetters(Object object) {
    Class aClass = object.getClass();
    List<Method> methods = Arrays.asList(aClass.getMethods());

    Map<String, Method> map = new HashMap<>();


    for (Method m :
        methods) {
      if (m.getName().startsWith("get") && m.getName().endsWith("Range")) {
        String name = getMethodValueName(m, new String[]{"get", "CsvSchema", "Range"});
        map.put(name, m);
      }
    }

    return map;
  }

  private String getMethodValueName(Method method, String[] replaces) {
    String name = method.getName();
    for (String replace :
        replaces) {
      name = name.replace(replace, "");
    }

    return name.substring(0, 1).toLowerCase() + name.substring(1);
  }

  public String getCsvSchemaValues() {
    return csvSchemaValues;
  }

  public String csvSchemaContainerIdRange() {
    return csvSchemaContainerIdRange;
  }

  public String getCsvSchemaAirTempRange() {
    return csvSchemaAirTempRange;
  }

  public String getCsvSchemaAirHumidityRange() {
    return csvSchemaAirHumidityRange;
  }

  public String getCsvSchemaAirCo2Range() {
    return csvSchemaAirCo2Range;
  }

  public String getCsvSchemaWaterPhRange() {
    return csvSchemaWaterPhRange;
  }

  public String getCsvSchemaWaterEcRange() {
    return csvSchemaWaterEcRange;
  }
}
