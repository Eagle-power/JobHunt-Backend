package com.jobhunt.entity;

import java.time.LocalDateTime;
import java.util.Base64;

import com.jobhunt.dto.ApplicantDTO;
import com.jobhunt.dto.ApplicationStatus;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class Applicant {
	private Long applicantId;
	private String name ;
	private String email;
	private String phone;
	private String website;
	private byte[] resume;
	private String coverLetter;
	private LocalDateTime timestamp;
	private ApplicationStatus applicationStatus;
	private LocalDateTime interviewTime;
	
	
	public ApplicantDTO toDTO() {
		 return new ApplicantDTO(this.applicantId , this.name , this.email, this.phone , this.website , this.resume!=null?Base64.getEncoder().encodeToString(this.resume):null , this.coverLetter ,this.timestamp, this.applicationStatus, this.interviewTime);
	}
}
