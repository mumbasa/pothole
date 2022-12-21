package com.csir.pothole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csir.pothole.models.PotHoleMarker;
import com.csir.pothole.models.ResponseMessage;
import com.csir.pothole.service.SubscriberService;

@RestController
public class SubscriberMarkerController {

	@Autowired
	SubscriberService subscriberService;
	
	
	@PostMapping("/add/marker")
	public ResponseMessage saveSubscriber(@RequestBody PotHoleMarker pot) {
		return subscriberService.addPothole(pot);
				
	}
	
	
}
