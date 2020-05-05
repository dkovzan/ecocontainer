package com.econtainer.base.controller;

import com.econtainer.base.repository.ContainerDataRepository;
import com.econtainer.base.service.ContainerDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ContainerDataController {

    private static final Logger logger = LoggerFactory.getLogger(com.econtainer.base.controller.ContainerDataController.class);

    @Autowired
    private ContainerDataService containerDataService;
    @Autowired
    private ContainerDataRepository containerDataRepository;

//	@PostMapping(value = "/api/containerData")
//	public void addContainerData(@RequestBody ContainerData containerData){
//		try {
//            containerDataRepository.save(containerData);
//		} catch (Exception ex) {
//			logger.warn("Exception has been occured", ex);
//			ex.printStackTrace();
//			throw new ResponseStatusException(
//					HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
//		}
//	}
//
//	@GetMapping(value = "/api/containerData/{id}")
//	public ContainerData getContainerDataById(@PathVariable("id") Long id) {
//		ContainerData containerData = containerDataService.getById(id);
//		if(containerData == null) {
//			throw new ResponseStatusException(
//					HttpStatus.NOT_FOUND, String.format("ContainerData is not found by id %s", id)
//			);
//		}
//		return containerData;
//	}
//
//	@GetMapping(value = "/api/containerData/latest")
//	public ContainerData getLatestContainerData() {
//		ContainerData containerData = containerDataService.getLatest();
//		if(containerData == null) {
//			throw new ResponseStatusException(
//					HttpStatus.NOT_FOUND, "ContainerData is not found"
//			);
//		}
//		return containerData;
//	}

    @GetMapping(value = "/test")
    public String testConnection(HttpServletRequest request) {
        String clientDescription = request.getHeader("User-Agent");
        logger.info("Http connection is successful. Connected client is: " + clientDescription);
        return "Hello from server";
    }
}
