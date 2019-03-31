package com.xuyp.happychat.dao;

import com.xuyp.happychat.model.mongo.FriendsRequest;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRequestRepository extends MongoRepository<FriendsRequest, ObjectId> {

    Optional<FriendsRequest> findBySendUserIdAndAcceptUserId(String sendUserId, String acceptUserId);

    Optional<List<FriendsRequest>> findByAcceptUserId(String acceptUserId);

    int deleteBySendUserIdAndAcceptUserId(String sendUserId, String acceptUserId);

}
