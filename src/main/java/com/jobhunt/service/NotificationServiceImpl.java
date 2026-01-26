package com.jobhunt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jobhunt.dto.NotificationDTO;
import com.jobhunt.dto.NotificationStatus;
import com.jobhunt.entity.Notification;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.repository.NotificationRepository;
import com.jobhunt.utility.Utilities;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService{
	@Autowired
	private NotificationRepository notificationRepository;

	@Override
	public void sendNotification(NotificationDTO notificationDTO) throws JobPortalException {
		notificationDTO.setId(Utilities.getNextSequence("notification"));
		notificationDTO.setStatus(NotificationStatus.UNREAD);
		notificationDTO.setTimeStamp(LocalDateTime.now());
		notificationRepository.save(notificationDTO.toEntity());
		
	}

	@Override
	public List<Notification> getUnreadNotifications(Long userId) {
		return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
	}

	@Override
	public void readNotifications(Long id) throws JobPortalException {
		Notification noti = notificationRepository.findById(id).orElseThrow(()->new JobPortalException("No Notification found"));
		noti.setStatus(NotificationStatus.READ);
		notificationRepository.save(noti);
	}
	
}
