package com.group.ecocontainer.controller;

import com.group.ecocontainer.model.Weather;
import com.group.ecocontainer.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;

public class WebsocketWeatherController {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketWeatherController.class);

	private WeatherService weatherService;

	@Autowired
	public WebsocketWeatherController(WeatherService weatherService) {
		this.weatherService = weatherService;
	}

	@MessageMapping("/conditions")
	@SendTo("/topic/container")
	public Weather sendConditions(@Payload Weather weather) {
		// implement timer to add weather to DB e.g. each minute
		if (weather.getCreated_on().isAfter(weatherService.getLatest().getCreated_on()))
			weatherService.add(weather);
		return weather;
	}

	@MessageMapping("/test")
	@SendTo("/topic/test")
	public String testConnection(@Payload String word) {
		logger.info("Message is received from client through websocket: " + word);
		return "Ohh hello from server. Here is your word: " + word;
	}
}
