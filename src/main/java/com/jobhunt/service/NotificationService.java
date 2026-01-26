package com.jobhunt.service;

import java.util.List;

import com.jobhunt.dto.NotificationDTO;
import com.jobhunt.entity.Notification;
import com.jobhunt.exception.JobPortalException;

public interface NotificationService {
	
	public void sendNotification(NotificationDTO notificationDTO) throws JobPortalException;
	public List<Notification> getUnreadNotifications(Long userId);
	public void readNotifications(Long id) throws JobPortalException;
	
}
