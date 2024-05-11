package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface CustomMProjectRepository {

    List<ChatMessage> getNChatMessagesBefore(String projectId, LocalDateTime beforeTime, int size);

}
