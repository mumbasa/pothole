package com.csir.pothole.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.csir.pothole.models.PotHoleMarker;
import com.csir.pothole.models.ResponseMessage;
import com.csir.pothole.models.Subscriber;
import com.csir.pothole.repository.PotHoleMarkerRepository;
import com.csir.pothole.repository.SubscriberRepository;
import com.pengrad.telegrambot.model.Chat;

@Service
public class SubscriberService {

	@Autowired
	SubscriberRepository subRepository;
	
	@Autowired
	PotHoleMarkerRepository potHoleMarkerRepository;
	
	@Value("${mnotify.key}")
	String key;
	public Subscriber saveSubscriber(Subscriber subscriber) {		
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		subscriber.setCode(generatedString);
		String message ="Your activation code is "+generatedString +". Use this code to start submission.";
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response =restTemplate.getForEntity("https://apps.mnotify.net/smsapi?key="+key+"&to="+subscriber.getTelephoneNumber()+"&msg="+message+"&sender_id=PotHoles", String.class);
		System.err.println(response.getBody() +" "+key);
		return subRepository.save(subscriber);
		
	}
	
	
	public ResponseMessage saveSubscriberTelegram(Chat chat,String telephone) {	
		ResponseMessage mess = new ResponseMessage();

		Optional<Subscriber> sub = subRepository.findByTelegramId(chat.id());
		if(sub.isPresent()) {
			mess.setMessage("You are already a registered user");
		}else {
		Subscriber subscriber = new Subscriber();
		subscriber.setTelegramId(chat.id());
		subscriber.setTelephoneNumber(telephone);
		subscriber.setDateAdded(LocalDateTime.now());
		int length = 10;
		boolean useLetters = true;
		boolean useNumbers = true;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
		subscriber.setCode(generatedString);
		String message ="Your activation code is "+generatedString +". Use this code to start submission.";
		subRepository.save(subscriber);
		mess.setMessage(message);
		}
		return mess;
		
	}
	
	public ResponseMessage activate(Subscriber subscriber) {	
		ResponseMessage message = new ResponseMessage();
		Optional<Subscriber> sub = subRepository.findByCodeAndTelephoneNumber(subscriber.getCode(), subscriber.getTelephoneNumber());
		if(sub.isPresent()) {
			sub.get().setActivated(true);
			subRepository.save(sub.get());
			message.setDateTime(LocalDateTime.now());
			message.setMessage("Activation Completed");
			message.setStatusCode(1);
		}else {
			message.setDateTime(LocalDateTime.now());
			message.setMessage("Activation Failed enter valid code");
			message.setStatusCode(0);
		}
		return message;
		
	}

	
	public ResponseMessage activateTelegram(String code,long telegramId) {	
		ResponseMessage message = new ResponseMessage();
		Optional<Subscriber> sub = subRepository.findByCode(code);
		if(sub.isPresent()) {
			sub.get().setTelegramId(telegramId);;
			subRepository.save(sub.get());
			message.setDateTime(LocalDateTime.now());
			message.setMessage("Telegram activation complete");
			message.setStatusCode(1);
		}else {
			message.setDateTime(LocalDateTime.now());
			message.setMessage("Telegram activating failed enter valid code");
			message.setStatusCode(0);
		}
		return message;
		
	}

	
	public ResponseMessage addPothole(PotHoleMarker potholeMarker) {	
		ResponseMessage message = new ResponseMessage();
		Optional<Subscriber> sub = subRepository.findByTelegramId(potholeMarker.getSubscriber().getTelegramId());
			
		if(sub.isPresent()) {
			potholeMarker.setSubscriber(sub.get());
			potHoleMarkerRepository.save(potholeMarker);
			message.setDateTime(LocalDateTime.now());
			message.setMessage("Submission completed. Thank you");
			message.setStatusCode(1);
		}else {
			message.setDateTime(LocalDateTime.now());
			message.setMessage("Submission failed. Subscriber does not exist.");
			message.setStatusCode(0);
		}
		return message;
		
	}

}
