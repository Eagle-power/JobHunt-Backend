package com.jobhunt.service;

import com.jobhunt.dto.ChatRequestDTO;
import com.jobhunt.dto.ChatResponseDTO;
import com.jobhunt.exception.JobPortalException;

public interface ChatService {

	ChatResponseDTO ask(ChatRequestDTO request, String email)
	        throws JobPortalException;

    
    
}
