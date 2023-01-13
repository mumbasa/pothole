package com.csir.pothole.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.csir.pothole.models.ResponseMessage;
import com.csir.pothole.models.Subscriber;
import com.csir.pothole.service.SubscriberService;

@RestController
public class SubcriberController {
	@Autowired
	SubscriberService subscriberService;
	
	@PostMapping("/register")
	public Subscriber saveSubscriber(@RequestBody Subscriber sub) {
		return subscriberService.saveSubscriber(sub);
				
	}
	@PostMapping("/activate")
	public ResponseMessage activateSubscriber(@RequestBody Subscriber sub) {
		return subscriberService.activate(sub);
	}
	
	@PostMapping("/activate/telegram")
	public ResponseMessage activateTelegram(@RequestBody Subscriber sub) {
		return subscriberService.activate(sub);
		
		
	}
	@PostMapping("/activate/telegras")
	public ResponseMessage activateTelegrams(@RequestBody Subscriber sub) {
		return subscriberService.activate(sub);
		
		
	}

}
