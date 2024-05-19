package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.dto.liveblock.LiveFlowChart;
import io.ssafy.soupapi.domain.project.mongodb.dto.request.UpdateProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.dto.response.GetProjectJiraKey;
import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.domain.project.mongodb.entity.Info;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.Tool;
import io.ssafy.soupapi.global.common.code.ErrorCode;
import io.ssafy.soupapi.global.exception.BaseExceptionHandler;
import io.ssafy.soupapi.global.util.StringParserUtil;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
public class CustomMProjectRepositoryImpl implements CustomMProjectRepository {

    private final MongoTemplate mongoTemplate;

    /**
     * projectId 프로젝트의 채팅 메시지 중에서, beforeTime 이전에 만들어진 size개의 채팅 메시지를 반환한다.
     */
    @Override
    public List<ChatMessage> getNChatMessagesBefore(String projectId, Instant beforeTime, int size) {

        AggregationOperation opMatchProjectId = Aggregation.stage("""
                    { $match: { _id: ObjectId('#projectId#') } }
                """.replace("#projectId#", projectId));

        AggregationOperation opUnwind = Aggregation.stage("""
                    { $unwind: "$project_chats" }
                """);

        AggregationOperation opFilterByTimeAfter = Aggregation.stage("""
                    { $match: { "project_chats.chat_message_timestamp": { $lt: new ISODate("#beforeTime#") } } }
                """.replace("#beforeTime#", String.valueOf(beforeTime)));

        AggregationOperation opSortDesc = Aggregation.stage("""
                    { $sort: { "project_chats.chat_message_timestamp": -1 } }
                """);

        AggregationOperation opLimit = Aggregation.stage("""
                    { $limit: #size# }
                """.replace("#size#", String.valueOf(size)));

        AggregationOperation opRelation = Aggregation.stage("""
                    {
                         $project: {
                             _id: 0,
                             chat_message_id: "$project_chats.chat_message_id",
                             chat_message_sender_id: "$project_chats.chat_message_sender_id",
                             chat_message_content: "$project_chats.chat_message_content",
                             chat_message_timestamp: "$project_chats.chat_message_timestamp",
                             chat_message_mentionee_list: "$project_chats.chat_message_mentionee_list"
                         }
                     }
                """);

        Aggregation aggregation;
        if (beforeTime == null) {
            aggregation = Aggregation.newAggregation(
                    opMatchProjectId, opUnwind, opSortDesc, opLimit, opRelation
            );
        } else {
            aggregation = Aggregation.newAggregation(
                    opMatchProjectId, opUnwind, opFilterByTimeAfter, opSortDesc, opLimit, opRelation
            );
        }
        AggregationResults<ChatMessage> chatMessageList = mongoTemplate.aggregate(aggregation, "projects", ChatMessage.class);
        return chatMessageList.getMappedResults();
    }

    @Override
    public LiveFlowChart updateFlowChart(ObjectId projectId, LiveFlowChart liveFlowChart) {
        var pretty = StringParserUtil.formatMermaid(liveFlowChart.json());
        Query query = new Query(Criteria.where("_id").is(projectId));
        Update update = new Update().set("project_flow_chart", pretty);

        var result = mongoTemplate.updateFirst(query, update, Project.class);
        if (result.wasAcknowledged() && result.getMatchedCount() + result.getModifiedCount() > 0) {
            return new LiveFlowChart(pretty);
        }
        throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_FLOWCHART);
    }

    @Override
    public void updateInfoAndTools(ObjectId projectId, Info projectInfo, List<Tool> tools) {
        Query query = new Query(Criteria.where("_id").is(projectId));
        Update update = new Update()
                .set("project_info", projectInfo)
                .set("project_tools", tools);

        var result = mongoTemplate.updateFirst(query, update, Project.class);
        if (result.wasAcknowledged() && result.getModifiedCount() + result.getMatchedCount() > 0) {
            return;
        }
        throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT);
    }

    @Override
    public GetProjectJiraKey updateJiraInfo(ObjectId projectId, UpdateProjectJiraKey updateProjectJiraKey) {
        Query query = new Query(Criteria.where("_id").is(projectId));
        Update update = new Update()
                .set("project_info.project_jira_host", updateProjectJiraKey.host())
                .set("project_info.project_jira_project_key", updateProjectJiraKey.projectKey())
                .set("project_info.project_jira_username", updateProjectJiraKey.username())
                .set("project_info.project_jira_key", updateProjectJiraKey.key());

        var result = mongoTemplate.updateFirst(query, update, Project.class);
        if (result.wasAcknowledged() && result.getModifiedCount() + result.getMatchedCount() > 0) {
            return GetProjectJiraKey.builder()
                    .jiraHost(updateProjectJiraKey.host())
                    .jiraProjectKey(updateProjectJiraKey.projectKey())
                    .jiraUsername(updateProjectJiraKey.username())
                    .jiraKey(updateProjectJiraKey.key())
                    .build();
        }
        throw new BaseExceptionHandler(ErrorCode.FAILED_TO_UPDATE_PROJECT);
    }
}
