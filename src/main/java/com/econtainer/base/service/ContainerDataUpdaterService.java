package com.econtainer.base.service;

import com.econtainer.base.model.ContainerData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.broker.BrokerAvailabilityEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ContainerDataUpdaterService implements ApplicationListener<BrokerAvailabilityEvent> {

	private AtomicBoolean brokerAvailable = new AtomicBoolean();

    @Autowired
	private ContainerDataService service;
    @Autowired
    private final SimpMessagingTemplate messagingTemplate;


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
			if (containerData != null) {
                this.messagingTemplate.convertAndSend("/topic/containerData", containerData);
            }
		}
	}
}
