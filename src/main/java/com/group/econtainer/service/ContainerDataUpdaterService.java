package com.group.econtainer.service;

import com.group.econtainer.model.ContainerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ContainerDataUpdaterService implements ApplicationListener<BrokerAvailabilityEvent> {

	private final SimpMessagingTemplate messagingTemplate;

	private AtomicBoolean brokerAvailable = new AtomicBoolean();

	private ContainerDataService service;

	@Autowired
	public ContainerDataUpdaterService(SimpMessagingTemplate messagingTemplate, ContainerDataService service) {
		this.messagingTemplate = messagingTemplate;
		this.service = service;
	}

	@Override
	public void onApplicationEvent(BrokerAvailabilityEvent event) {
		this.brokerAvailable.set(event.isBrokerAvailable());
	}

	@Scheduled(fixedDelay=60000)
	public void updateContainerData() {
		//TODO external update by API POST not with csv
		service.updateDataBaseByCsv();
		if (this.brokerAvailable.get() /*&& dataIsUpdated*/) {
			ContainerData containerData = service.getLatest();
				this.messagingTemplate.convertAndSend("/topic/containerData", containerData);
		}
	}
}
