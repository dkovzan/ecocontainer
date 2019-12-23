package com.group.ecocontainer.controller;

import com.group.ecocontainer.exception.ApiException;
import com.group.ecocontainer.model.Weather;
import com.group.ecocontainer.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class WeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

	private WeatherService weatherService;

	@Autowired
	public WeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@PostMapping(value = "/api/weather")
	public void addWeather(@RequestBody Weather weather){
		try {
			weatherService.add(weather);
		} catch (Exception ex) {
			logger.warn("Exception has been occured", ex);
			ex.printStackTrace();
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@GetMapping(value = "/api/weather/{id}")
	public Weather getWeatherById(@PathVariable("id") Long id) {
		Weather weather = weatherService.getById(id);
		if(weather == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, String.format("Weather is not found by id %s", id)
			);
		}
		return weather;
	}

	@GetMapping(value = "/api/weather/latest")
	public Weather getLatestWeather() {
		Weather weather = weatherService.getLatest();
		if(weather == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Weather is not found"
			);
		}
		return weather;
	}

	@GetMapping(value = "/test")
	public String testConnection(HttpServletRequest request) {
		String clientDescription = request.getHeader("User-Agent");
		logger.info("Http connection is successful. Connected client is: " + clientDescription);
		return "Hello from server";
	}
}
