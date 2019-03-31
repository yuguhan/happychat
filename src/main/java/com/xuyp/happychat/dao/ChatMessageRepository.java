package com.xuyp.happychat.dao;

import com.xuyp.happychat.model.mongo.ChatMessage;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, ObjectId> {
    List<ChatMessage> findAllByAcceptUserIdAndSignFlag(String userId, int signFlag);

}
