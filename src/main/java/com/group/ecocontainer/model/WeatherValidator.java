package com.group.ecocontainer.model;

import static com.group.ecocontainer.utils.StringUtils.matchRange;

import com.group.ecocontainer.exception.CsvDaoException;
import com.group.ecocontainer.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.validation.ValidationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WeatherValidator {
  @Value("${csvSchemaValuesCount}")
  private String csvSchemaValuesCount;
  @Value("${csvSchemaValues}")
  private String csvSchemaValues;
  @Value("${csvSchemaIdRange}")
  private String csvSchemaIdRange;
  @Value("${csvSchemaTemperatureRange}")
  private String csvSchemaTemperatureRange;
  @Value("${csvSchemaHumidityRange}")
  private String csvSchemaHumidityRange;
  @Value("${csvSchemaPressureRange}")
  private String csvSchemaPressureRange;

  private static List<String> skippedRows = new ArrayList<>();

  private static final Logger logger = LoggerFactory.getLogger(WeatherValidator.class);

  public List<Weather> CsvListToWeather(List<String> csvRows) throws CsvDaoException {

    List<Weather> weathers = new ArrayList<>();
    int size = csvRows.size();

    Map<Integer, Method> csvMap = mapSettersToCsvScheme(new Weather());

    List<String> csvValidationLog = new ArrayList<>();
    String errorMessage = "";

    if (!skippedRows.isEmpty()) {
      logger.error(skippedRows.size() + " invalid csv rows have been ignored: " + skippedRows.toString());
    }

    try {
      for (int i = 0; i < size; i++) {
        List<String> rowsLog = new ArrayList<>();
        int index = size - 1 - i;
        Weather weather = new Weather();
        String csvRow = csvRows.get(index);
        String[] weatherValues = csvRow.split(",");
        int weatherValuesCount = weatherValues.length;

        if (skippedRows.contains(csvRow)) {
          continue;
        }

        if (getCsvCounts() != weatherValuesCount) {
          rowsLog.add("invalid values count (" + weatherValuesCount
              + ") expected by scheme (" + getCsvCounts() + ")");
        } else {
          Map<String, Method> rangeGetters = getRangeGetters(this);
          csvMap.forEach((ind, m) -> {
            try {
              Class cl = m.getParameterTypes()[0];
              String stringValue = weatherValues[ind];
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

                m.invoke(weather, value);
              } else if ((cl).isInstance(1L)) {
                Long value = formatToLong(stringValue, parameterName, rowsLog);

                if (rangeGetters.containsKey(parameterName)) {
                  String schemaRange = (String) rangeGetters.get(parameterName).invoke(this);
                  validateRange(schemaRange, value, parameterName, rowsLog);
                }

                m.invoke(weather, value);
              } else if ((cl).isInstance(new Timestamp(0L))) {
                Timestamp value = formatToTimestamp(stringValue, parameterName, rowsLog);

                validateTimestamp(value, parameterName, rowsLog);

                m.invoke(weather, value);
              } else {
                m.invoke(weather, stringValue);
              }
            } catch (IllegalAccessException | InvocationTargetException e) {
              e.printStackTrace();
            } catch (ValidationException e) {
              rowsLog.add(e.getMessage());
            }
          });
        }
        if (rowsLog.isEmpty()) {
          weathers.add(weather);
        } else {
          csvValidationLog.add(rowsLog.toString());
          skippedRows.add(csvRow);
        }
      }

      if (!csvValidationLog.isEmpty()) {
        errorMessage = "[CsvListToWeather] CSV Validation error:\n"
            + skippedRows.size() + " invalid csv rows have been ignored\n"
            + csvValidationLog.stream().collect(Collectors.joining("\n"))
            + "\nin the following rows from csv file:\n" + skippedRows.stream().collect(Collectors.joining("\n"));

        throw new ValidationException(errorMessage);
      }

    } catch (Exception ex) {
      if (weathers.isEmpty()) {
        throw new CsvDaoException(skippedRows.size() + " invalid csv rows have been ignored !", ex);
      }
      logger.error(ex.getMessage());
      return weathers;
    }
    skippedRows.clear();
    return weathers;
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

  private Map mapSettersToCsvScheme(Object object) {
    Class aClass = object.getClass();

    List<Method> methods = Arrays.asList(aClass.getMethods());

    Map<Integer, Method> csvMap = new HashMap<>();

    List<String> weatherValues = Arrays.asList(csvSchemaValues.split(","));

    for (Method m :
        methods) {
      if (m.getName().contains("set") && !m.getName().equals("setId")) {
        String name = getMethodValueName(m, new String[]{"set"});
        if (weatherValues.contains(name)) {
          csvMap.put(weatherValues.indexOf(name), m);
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

  public Integer getCsvCounts() {
    return Integer.valueOf(csvSchemaValuesCount);
  }

  public String getCsvSchemaValuesCount() {
    return csvSchemaValuesCount;
  }

  public String getCsvSchemaValues() {
    return csvSchemaValues;
  }

  public String getCsvSchemaIdRange() {
    return csvSchemaIdRange;
  }

  public String getCsvSchemaTemperatureRange() {
    return csvSchemaTemperatureRange;
  }

  public String getCsvSchemaHumidityRange() {
    return csvSchemaHumidityRange;
  }

  public String getCsvSchemaPressureRange() {
    return csvSchemaPressureRange;
  }
}
