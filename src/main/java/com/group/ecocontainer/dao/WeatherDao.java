package com.group.ecocontainer.dao;

import com.group.ecocontainer.model.Weather;

import java.util.List;

public interface WeatherDao {
	void add(Weather weather);
	void update(Weather weather);
	void remove(Weather weather);
	Weather getById(Long id);
	Weather getLatest();
	List<Weather> getAll();
}
