package com.group.econtainer.controller;

import com.group.econtainer.model.ContainerData;
import com.group.econtainer.service.ContainerDataService;
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
public class WebsocketContainerDataController {

	private static final Logger logger = LoggerFactory.getLogger(WebsocketContainerDataController.class);

	private ContainerDataService containerDataService;

	@Autowired
	public WebsocketContainerDataController(ContainerDataService containerDataService) {
		this.containerDataService = containerDataService;
	}

	@MessageMapping("/conditions")
	@SendTo("/topic/container")
	public ContainerData sendConditions(@Payload ContainerData containerData) {
		return containerData;
	}

	@SubscribeMapping("/containerData")
	public ContainerData getLatestContainerData() {
		logger.info("Subscribe single for latest containerData");
		return containerDataService.getLatest();
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
