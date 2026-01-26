package com.jobhunt.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobhunt.entity.Profile;

public interface ProfileRepository extends MongoRepository<Profile, Long>{
	
}
