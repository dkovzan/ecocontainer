package com.group.ecocontainer.service;

import com.group.ecocontainer.dao.WeatherDao;
import com.group.ecocontainer.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WeatherServiceImpl implements WeatherService {

	private WeatherDao weatherDao;

	@Autowired
	public WeatherServiceImpl(WeatherDao weatherDao) {
		this.weatherDao = weatherDao;
	}

	@Override
	@Transactional
	public void add(Weather weather) {
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
}
