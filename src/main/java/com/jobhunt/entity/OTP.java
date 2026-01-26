package com.jobhunt.entity;

import java.time.Instant;
 

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection="otp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
	
	@Id
	private String email;
	private String otpCode;
	private Instant creationTime;
	
}
