package com.group.ecocontainer.service;

import com.group.ecocontainer.model.Weather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class WeatherGeneratorService implements ApplicationListener<BrokerAvailabilityEvent> {

	private final SimpMessagingTemplate messagingTemplate;

	private AtomicBoolean brokerAvailable = new AtomicBoolean();

	private WeatherService service;

	@Autowired
	public WeatherGeneratorService(SimpMessagingTemplate messagingTemplate, WeatherService service) {
		this.messagingTemplate = messagingTemplate;
		this.service = service;
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		this.brokerAvailable.set(event.isBrokerAvailable());
	}

	@Scheduled(fixedDelay=600000)
	public void generateWeather() {
		boolean dataIsUpdated = service.updateDataBaseByCsv();
		if (this.brokerAvailable.get() && dataIsUpdated) {
			Weather weather = service.getLatest();
				this.messagingTemplate.convertAndSend("/topic/weather", weather);
		}
	}
}
