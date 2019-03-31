package com.xuyp.happychat.dao;

import com.xuyp.happychat.model.mongo.MyFriend;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MyFriendRepository extends MongoRepository<MyFriend, ObjectId> {
    Optional<MyFriend> findByMyUserIdAndMyFriendUserId(String myUserId, String myFriendUserId);

    Optional<List<MyFriend>> findByMyUserId(String myUserId);



}
