package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.dto.liveblock.LiveFlowChart;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Tool;
import org.bson.types.ObjectId;

import java.time.Instant;
import java.util.List;

public interface CustomMProjectRepository {

    List<ChatMessage> getNChatMessagesBefore(String projectId, Instant beforeTime, int size);

    LiveFlowChart updateFlowChart(ObjectId projectId, LiveFlowChart liveFlowChart);

    void updateInfoAndTools(ObjectId projectId, Info projectInfo, List<Tool> tools);

    GetProjectJiraKey updateJiraInfo(ObjectId projectId, UpdateProjectJiraKey updateProjectJiraKey);
}
