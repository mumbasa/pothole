package com.csir.pothole.models;

import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class Mnotify {
	
	private String key;
	private Object[] recipient;
	private String sender;
	private String message;

}
