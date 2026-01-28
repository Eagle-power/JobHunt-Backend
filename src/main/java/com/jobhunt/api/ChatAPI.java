package com.jobhunt.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jobhunt.dto.ChatRequestDTO;
import com.jobhunt.dto.ChatResponseDTO;
import com.jobhunt.exception.JobPortalException;
import com.jobhunt.service.ChatService;

@RestController
@RequestMapping("/chat")
public class ChatAPI {

    @Autowired
    private ChatService chatService;

    @PostMapping("/ask")
    public ResponseEntity<ChatResponseDTO> ask(
            @RequestBody ChatRequestDTO request,
            Authentication authentication
    ) throws JobPortalException {

        // 🔐 JWT required (Spring Security injects Authentication)
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // assuming username = userId or email mapped internally
        String email = authentication.getName();


        ChatResponseDTO response = chatService.ask(request, email);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
