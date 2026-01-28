package com.jobhunt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDTO {
	 	private Long profileId;   // whose resume to use
	    private String message;   // user question
	    private Long jobId; 
}
