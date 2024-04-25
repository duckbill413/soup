package io.ssafy.soupapi.domain.project.mongodb.dao;

import io.ssafy.soupapi.domain.project.mongodb.entity.Project;
import io.ssafy.soupapi.domain.project.mongodb.entity.Proposal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface MProjectRepository extends MongoRepository<Project, ObjectId> {
    @Query(value = "{ _id: ?0 }", fields = "{ project_proposal: 1 }")
    Optional<Project> findProposalById(ObjectId projectId);

    @Query(value = "{ _id:  ?0}", fields = "{ project_team_members:  1}")
    Optional<Project> findTeammateById(ObjectId projectId);

    @Query(value = "{ _id: ?0 }", fields = "{ project_proposal: 1 }")
    @Update("{ '$set': { 'project_proposal':  ?1} }")
    void updateProposal(ObjectId projectId, Proposal proposal);
}
