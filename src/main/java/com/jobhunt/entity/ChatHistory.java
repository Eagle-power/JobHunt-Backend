package com.jobhunt.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "chat_history")
@Data
public class ChatHistory {

    @Id
    private String id;   // Mongo uses String ObjectId

    private Long userId;
    private Long jobId;

    private String userMessage;
    private String botReply;

    private LocalDateTime createdAt = LocalDateTime.now();
}
