package com.jobhunt.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jobhunt.entity.ChatHistory;

public interface ChatHistoryRepository
        extends MongoRepository<ChatHistory, String> {

    List<ChatHistory> findTop5ByUserIdAndJobIdOrderByCreatedAtDesc(
        Long userId, Long jobId
    );
}
