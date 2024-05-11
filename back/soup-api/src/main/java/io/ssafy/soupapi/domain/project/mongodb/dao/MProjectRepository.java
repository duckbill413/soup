package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.ChatMessage;
import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.Proposal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.List;
import java.util.Optional;

public interface MProjectRepository extends MongoRepository<Project, ObjectId>, CustomMProjectRepository {
    @Query(value = "{ _id: ?0 }", fields = "{ project_proposal: 1 }")
    Optional<Project> findProposalById(ObjectId projectId);

    @Query(value = "{_id: ?0}", fields = "{project_info: 1 }")
    Optional<Project> findInfoById(ObjectId projectId);

    @Query(value = "{ _id: ?0 }", fields = "{ project_proposal: 1 }")
    @Update("{ '$set': { 'project_proposal':  ?1} }")
    void updateProposal(ObjectId projectId, Proposal proposal);

    @Query(value = "{ _id: ?0}", fields = "{ project_vuerd:  1}")
    Optional<Project> findVuerdById(ObjectId projectId);

    @Query(value = "{ _id: ?0 }", fields = "{ project_vuerd: 1 }")
    @Update("{ '$set': { project_vuerd:  ?1} }")
    void changeVuerdById(ObjectId projectId, Object vuerdDoc);

    @Query(value = "{ _id:  ?0 }", fields = "{project_info:  1, project_tools:  1}")
    Optional<Project> findInfoAndToolsById(ObjectId projectId);

    @Query(value = "{ _id: ?0 }", fields = "{project_info: 1}")
    Optional<Project> findProjectJiraInfo(ObjectId projectId);

    @Query(value = "{ _id: ?0}", fields = "{project_api_doc:  1}")
    Optional<Project> findProjectApiDocs(ObjectId projectId);

    @Query(value = "{ _id:  ?0}", fields = "{project_issues:  1}")
    Optional<Project> findAllProjectIssues(ObjectId projectId);

    @Query(value = "{ _id: ?0 }", fields = "{project_issues: {$slice: [?1, ?2]}}")
    Optional<Project> findProjectIssues(ObjectId projectId, int offset, int limit);

    @Query(value = "{ _id: ?0 }", fields = "{ project_issues: 0 }")
    Optional<Project> findProjectIssuesCount(ObjectId projectId);

    @Query(value = "{ '_id': ?0 }", fields = "{ 'project_chats': 1 }")
    @Update("{ '$push': { 'project_chats': ?1 } }")
    void addChatMessage(ObjectId projectId, ChatMessage chatMessage);

    @Query(value = "{ '_id': ?0 }", fields = "{ 'project_chats': 1 }")
    @Update("{ '$push': { 'project_chats': { '$each': ?1 } } }")
    void addChatMessageList(ObjectId projectId, List<ChatMessage> chatMessageList);

}
