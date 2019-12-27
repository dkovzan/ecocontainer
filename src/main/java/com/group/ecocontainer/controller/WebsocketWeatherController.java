package com.group.ecocontainer.controller;

import com.group.ecocontainer.model.Weather;
import com.group.ecocontainer.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
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
		return weather;
	}

	@SubscribeMapping("/weather")
	public Weather getLatestWeather() {
		logger.info("Subscribe single for latest weather");
		return weatherService.getLatest();
	}

	@MessageMapping("/test")
	@SendTo("/topic/test")
	public String testConnection(@Payload String word) {
		logger.info("Message is received from client through websocket: " + word);
		return "Ohh hello from server. Here is your word: " + word;
	}

	@MessageExceptionHandler
	@SendTo("/queue/errors")
	public String handleException(Throwable exception) {
		return exception.getMessage();
	}

}
