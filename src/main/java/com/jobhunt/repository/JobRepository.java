package com.jobhunt.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobhunt.entity.Job;

public interface JobRepository extends MongoRepository<Job, Long>{
	public List<Job> findByPostedBy(Long postedBy);
} 
