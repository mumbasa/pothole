package com.csir.pothole.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.csir.pothole.models.PotHoleMarker;
import com.csir.pothole.models.Subscriber;
@Repository
public interface PotHoleMarkerRepository extends JpaRepository<PotHoleMarker, Long> {
	
	List<PotHoleMarker> findBySubscriber(Subscriber subscriber);

}
