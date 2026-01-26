package com.jobhunt.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobhunt.dto.NotificationStatus;
import com.jobhunt.entity.Notification;

public interface NotificationRepository extends MongoRepository<Notification, Long> {
	
	public List<Notification> findByUserIdAndStatus(Long userId , NotificationStatus status);
	
}
