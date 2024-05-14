package io.ssafy.soupapi.domain.noti.dao;

import io.ssafy.soupapi.domain.noti.entity.MNoti;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface NotiRepository extends MongoRepository<MNoti, ObjectId> {

    @Query(value = "{ noti_receiver_id: ?0 }")
    List<MNoti> findByReceiverId(String receiverId);

}
