package com.csir.pothole.models;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class ResponseMessage {
	
	private String message;
	private int statusCode;
	private LocalDateTime dateTime;

}
