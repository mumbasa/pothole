package com.csir.pothole.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.csir.pothole.models.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long>{
	Optional<Subscriber> findByTelephoneNumber(String number);
	Optional<Subscriber> findByTelegramId(long number);
	Optional<Subscriber> findByCodeAndTelephoneNumber(String code,String telephoneNumber);
	Optional<Subscriber> findByCode(String code);


}
