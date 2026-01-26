package com.jobhunt.entity;
 
import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;
 
import com.jobhunt.dto.NotificationDTO;
import com.jobhunt.dto.NotificationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection="notification")
public class Notification {
	private Long id;
	private Long userId;
	private String message;
	private String action;
	private String route;
	private NotificationStatus status;
	private LocalDateTime timeStamp;
	
	public  NotificationDTO toDTO() {
		return new NotificationDTO(this.id , this.userId , this.message , this.action , this.route , this.status , this.timeStamp);
	}
}
