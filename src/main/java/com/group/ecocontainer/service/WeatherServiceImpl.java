package com.group.ecocontainer.service;

import com.group.ecocontainer.dao.CsvDao;
import com.group.ecocontainer.dao.WeatherDao;
import com.group.ecocontainer.exception.CsvDaoException;
import com.group.ecocontainer.model.Weather;
import com.group.ecocontainer.model.WeatherValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

  private static final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

  private WeatherDao weatherDao;
  private CsvDao csv;
  private static Timestamp currentTime;
  private WeatherValidator validator;

  @Autowired
  public WeatherServiceImpl(WeatherDao weatherDao, CsvDao csv,WeatherValidator validator) {
    this.weatherDao = weatherDao;
    this.csv = csv;
    this.validator = validator;
  }

  @Override
  @Transactional
  public void add(Weather weather) throws CsvDaoException {
//		if (weather.getCreated_on() != null) {
//			ApiException exception = new ApiException("ERROR Creaton_om != null");
//			throw exception;
//		}
    weatherDao.add(weather);
  }

  @Override
  @Transactional
  public void remove(Weather weather) {
    weatherDao.remove(weather);
  }

  @Override
  @Transactional
  public List<Weather> getAll() {
    return weatherDao.getAll();
  }

  @Override
  @Transactional
  public void update(Weather weather) {
    weatherDao.update(weather);
  }

  @Override
  @Transactional
  public Weather getLatest() {
    return weatherDao.getLatest();
  }

  @Override
  @Transactional
  public Weather getById(Long id) {
    return weatherDao.getById(id);
  }

  @Override
  @Transactional
  public boolean updateDataBaseByCsv() {
    currentTime = currentTime == null ? weatherDao.getCurrentTimestamp() : currentTime;
    List<String> newRows = null;
		boolean isNewRow = false;
    try {
      newRows = csv.readNewRows(currentTime);
      List<Weather> weatherUpdates = validator.CsvListToWeather(newRows);
      isNewRow = !weatherUpdates.isEmpty();
      if (isNewRow) {
        currentTime = weatherUpdates.get(weatherUpdates.size() - 1).getCreated_on();
        for (Weather weather :
            weatherUpdates) {
          weatherDao.add(weather);
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
