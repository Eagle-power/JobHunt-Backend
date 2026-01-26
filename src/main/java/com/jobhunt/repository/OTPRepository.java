package com.jobhunt.repository;

import java.time.Instant; 
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobhunt.entity.OTP;

public interface OTPRepository extends MongoRepository<OTP, String> {
	List<OTP>findByCreationTimeBefore(Instant expiry);
	
}
