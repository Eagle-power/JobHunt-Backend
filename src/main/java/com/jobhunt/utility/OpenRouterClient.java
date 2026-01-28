package com.jobhunt.utility;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OpenRouterClient {

    private static final String OPENROUTER_URL =
            "https://openrouter.ai/api/v1/chat/completions";

    @Value("${openrouter.api.key}")
    private String apiKey;

    @Value("${openrouter.model}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    public String ask(String prompt) {
    	
    	

        // 1️⃣ Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        headers.add("HTTP-Referer", "https://jobhunt.app"); // required by OpenRouter
        headers.add("X-Title", "JobHunt AI Assistant");

        // 2️⃣ Request body (OpenAI-compatible)
        Map<String, Object> body = Map.of(
            "model", model,
            "messages", List.of(
                Map.of("role", "user", "content", prompt)
            ),
            "temperature", 0.2
        );

        HttpEntity<Map<String, Object>> request =
                new HttpEntity<>(body, headers);

        // 3️⃣ Call API
        Map response = restTemplate.postForObject(
                OPENROUTER_URL,
                request,
                Map.class
        );
        

        // 4️⃣ Extract reply safely
        try {
            Map firstChoice = (Map) ((List) response.get("choices")).get(0);
            Map message = (Map) firstChoice.get("message");
            return message.get("content").toString();
        } catch (Exception e) {
            throw new RuntimeException("Invalid response from OpenRouter");
        }
    }
}
