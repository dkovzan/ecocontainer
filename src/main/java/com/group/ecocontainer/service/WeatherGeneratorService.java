package com.group.ecocontainer.service;

import com.group.ecocontainer.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class WeatherGeneratorService implements ApplicationListener<BrokerAvailabilityEvent> {

	private final SimpMessagingTemplate messagingTemplate;

	private AtomicBoolean brokerAvailable = new AtomicBoolean();

	@Autowired
	public WeatherGeneratorService(SimpMessagingTemplate messagingTemplate) {
		this.messagingTemplate = messagingTemplate;
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		this.brokerAvailable.set(event.isBrokerAvailable());
	}

	@Scheduled(fixedDelay=30000)
	public void generateWeather() {
		if (this.brokerAvailable.get()) {
			Weather weather = new Weather();
			weather.setContainer_id((long) 2);
			weather.setHumidity(new Random().nextInt(100));
			weather.setPressure(new Random().nextInt(500));
			weather.setTemperature(new Random().nextInt(100));
			this.messagingTemplate.convertAndSend("/topic/weather", weather);
		}
	}

}
