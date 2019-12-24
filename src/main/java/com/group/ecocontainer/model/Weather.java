package com.group.ecocontainer.model;

import com.group.ecocontainer.exception.CsvDaoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.group.ecocontainer.utils.StringUtils.matchRange;

@Entity
@Component
@Table(name = "weather")
public class Weather {

  public Weather() {
  }

  @Value("${csvSchemaValuesCount}")
  private static String csvSchemaValuesCount;
  @Value("${csvSchemaTemperature}")
  private static String csvSchemaTemperature;
  @Value("${csvSchemaHumidity}")
  private static String csvSchemaHumidity;
  @Value("${csvSchemaPressure}")
  private static String csvSchemaPressure;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull(message = "Container id cannot be null")
  private Long container_id;

  private Integer temperature;

  private Integer humidity;

  private Integer pressure;

  @NotNull
  private Timestamp created_on;

  @NotNull
  private Timestamp internal_time;

  @Override
  public String toString() {
    return "InternalWeather{" +
        "id=" + id +
        ", container_id=" + container_id +
        ", temperature=" + temperature +
        ", humidity=" + humidity +
        ", pressure=" + pressure +
        ", created_on=" + created_on +
        ", internal_time=" + internal_time +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Weather that = (Weather) o;
    return id.equals(that.id) &&
        container_id.equals(that.container_id) &&
        Objects.equals(temperature, that.temperature) &&
        Objects.equals(humidity, that.humidity) &&
        Objects.equals(pressure, that.pressure) &&
        created_on.equals(that.created_on) &&
        internal_time.equals(that.internal_time);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, container_id, temperature, humidity, pressure, created_on, internal_time);
  }

  public static List<Weather> CsvListToWeather(List<String> csvRows) throws CsvDaoException {
    List<Weather> weathers = new ArrayList<>();
    int size = csvRows.size();

    for (int i = 0; i < size; i++) {
      int index = size - 1 - i;
      Weather weather = new Weather();
      String csvRow = csvRows.get(index);
      String[] weatherValues = csvRow.split(",");
      int weatherValuesCount = weatherValues.length;

      List<String> csvValidationLog = new ArrayList<>();
			String errorMessage = "";

      try {

				if (Integer.valueOf(csvSchemaValuesCount) != weatherValuesCount) {
					csvValidationLog.add("invalid values count (" + weatherValuesCount
							+ ") expected by scheme (" + csvSchemaValuesCount + ")");
				}
				weather.setContainer_id(Long.valueOf(weatherValues[0]));

				int csvTemperature = Integer.valueOf(weatherValues[1]);
				int csvHumidity = Integer.valueOf(weatherValues[2]);
				int csvPressure = Integer.valueOf(weatherValues[3]);

				if (matchRange(csvSchemaTemperature, csvTemperature)) {
					csvValidationLog.add("invalid temperature (" + csvTemperature
							+ ") expected by scheme (" + csvSchemaTemperature + ")");
				}

				validateIntegerValue(csvSchemaHumidity, csvHumidity, csvValidationLog);
				validateIntegerValue(csvSchemaPressure, csvPressure, csvValidationLog);

				if (!csvValidationLog.isEmpty()) {
					errorMessage = "[CsvListToWeather] CSV Validation error:\n" +
							csvValidationLog.stream().collect(Collectors.joining("\n"))
							+ "\nin the following row from csv file: " + csvRow;
				}

				weather.setTemperature(csvTemperature);
				weather.setHumidity(csvHumidity);
				weather.setPressure(csvPressure);
				weather.setCreated_on(Timestamp.valueOf(weatherValues[4].replace("T", " ")));
				weather.setInternal_time(Timestamp.valueOf(weatherValues[5].replace("T", " ")));
				weathers.add(weather);
				if (!csvValidationLog.isEmpty()) {
					throw new ValidationException(errorMessage);
				}
			} catch (Exception ex) {
      	throw new CsvDaoException(ex.getMessage() + errorMessage,ex);
			}
    }

    return weathers;
  }

  private static void validateIntegerValue(String schemaRange, int value, List<String> log) {
    String name = schemaRange.getClass().getSimpleName();
    if (matchRange(schemaRange, value)) {
      log.add("invalid " + name + " (" + value
          + ") expected by scheme (" + schemaRange + ")");
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getContainer_id() {
    return container_id;
  }

  public void setContainer_id(Long container_id) {
    this.container_id = container_id;
  }

  public Integer getTemperature() {
    return temperature;
  }

  public void setTemperature(Integer temperature) {
    this.temperature = temperature;
  }

  public Integer getHumidity() {
    return humidity;
  }

  public void setHumidity(Integer humidity) {
    this.humidity = humidity;
  }

  public Integer getPressure() {
    return pressure;
  }

  public void setPressure(Integer pressure) {
    this.pressure = pressure;
  }

  public Timestamp getCreated_on() {
    return created_on;
  }

  public void setCreated_on(Timestamp created_on) {
    this.created_on = created_on;
  }

  public Timestamp getInternal_time() {
    return internal_time;
  }

  public void setInternal_time(Timestamp internal_time) {
    this.internal_time = internal_time;
  }
}
