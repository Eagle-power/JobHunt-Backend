package com.jobhunt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessageDTO {
    private String role; // system | user | assistant
    private String content;
}
