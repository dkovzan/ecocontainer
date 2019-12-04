package com.group.ecocontainer.service;

import com.group.ecocontainer.model.Weather;

import java.util.List;

public interface WeatherService {
	void add(Weather weather);
	void remove(Weather weather);
	List<Weather> getAll();
	void update(Weather weather);
	Weather getLatest();
	Weather getById(Long id);
}
