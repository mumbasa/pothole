package com.csir.pothole.models;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table
public class PotHoleMarker {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private float longitude;
	private float latitude;
	private LocalDateTime timeAdded;
	private int Size;
	@ManyToOne
	@JoinColumn(name = "subscriber",updatable = false,insertable = false)
	private Subscriber subscriber;
}
