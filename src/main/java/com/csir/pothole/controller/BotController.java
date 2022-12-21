package com.csir.pothole.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.csir.pothole.models.PotHoleMarker;
import com.csir.pothole.models.ResponseMessage;
import com.csir.pothole.models.Subscriber;
import com.csir.pothole.service.SubscriberService;
import com.github.kshashov.telegram.api.MessageType;
import com.github.kshashov.telegram.api.TelegramMvcController;
import com.github.kshashov.telegram.api.TelegramRequest;
import com.github.kshashov.telegram.api.bind.annotation.BotPathVariable;
import com.github.kshashov.telegram.api.bind.annotation.BotRequest;
import com.github.kshashov.telegram.api.bind.annotation.request.MessageRequest;
import com.pengrad.telegrambot.Callback;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;

@com.github.kshashov.telegram.api.bind.annotation.BotController
public class BotController implements TelegramMvcController {

	@Value("${bot.token}")
	String token;

	@Autowired
	SubscriberService subscriberService;

	@BotRequest(value = "", type = { MessageType.CALLBACK_QUERY, MessageType.MESSAGE })
	public BaseRequest<?, ?> hello(User user, Chat chat, Message message) {
		if (message.location() != null) {
			Subscriber subscriber = new Subscriber();
			subscriber.setTelegramId(chat.id());
			PotHoleMarker marker = new PotHoleMarker();
			marker.setSubscriber(subscriber);
			marker.setLatitude(message.location().latitude());
			marker.setLongitude(message.location().longitude());
			marker.setTimeAdded(LocalDateTime.now());
			ResponseMessage serverResponse = subscriberService.addPothole(marker);
			return new SendMessage(chat.id(), "Hello, " + user.firstName() + "!\n" + serverResponse.getMessage());
		} else {
			return new SendMessage(chat.id(), "Hello, enter location " + user.firstName());
		}
	}

	@MessageRequest("/activate {name:[\\S]+}")
	public String helloWithName(@BotPathVariable("name") String userName, Chat chat) {
		// Return a string if you need to reply with a simple message
		ResponseMessage message = subscriberService.activateTelegram(userName, chat.id());
		return "Hello, " + message.getMessage();
	}
	
	@MessageRequest("/register {name:[\\S]+}")
	public String register(@BotPathVariable("name") String userName, Chat chat) {
		// Return a string if you need to reply with a simple message
		ResponseMessage message = subscriberService.saveSubscriberTelegram(chat, userName);
		return "Hello, " + message.getMessage();
	}

	@MessageRequest("/helloCallback")
	public String helloWithCustomCallback(TelegramRequest request, User user) {
		request.setCallback(new Callback() {
			@Override
			public void onResponse(BaseRequest request, BaseResponse response) {
				// TODO
			}

			@Override
			public void onFailure(BaseRequest request, IOException e) {
				// TODO
			}
		});
		return "Hello, " + user.firstName() + "!";
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return token;
	}

}
