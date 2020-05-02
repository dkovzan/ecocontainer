package com.group.econtainer.controller;

import com.group.econtainer.model.ContainerData;
import com.group.econtainer.service.ContainerDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ContainerDataController {

	private static final Logger logger = LoggerFactory.getLogger(ContainerDataController.class);

	private ContainerDataService containerDataService;

	@Autowired
	public ContainerDataController(ContainerDataService containerDataService) {
		this.containerDataService = containerDataService;
	}

	@PostMapping(value = "/api/containerData")
	public void addContainerData(@RequestBody ContainerData containerData){
		try {
			containerDataService.add(containerData);
		} catch (Exception ex) {
			logger.warn("Exception has been occured", ex);
			ex.printStackTrace();
			throw new ResponseStatusException(
					HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
		}
	}

	@GetMapping(value = "/api/containerData/{id}")
	public ContainerData getContainerDataById(@PathVariable("id") Long id) {
		ContainerData containerData = containerDataService.getById(id);
		if(containerData == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, String.format("ContainerData is not found by id %s", id)
			);
		}
		return containerData;
	}

	@GetMapping(value = "/api/containerData/latest")
	public ContainerData getLatestContainerData() {
		ContainerData containerData = containerDataService.getLatest();
		if(containerData == null) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "ContainerData is not found"
			);
		}
		return containerData;
	}

	@GetMapping(value = "/test")
	public String testConnection(HttpServletRequest request) {
		String clientDescription = request.getHeader("User-Agent");
		logger.info("Http connection is successful. Connected client is: " + clientDescription);
		return "Hello from server";
	}
}
