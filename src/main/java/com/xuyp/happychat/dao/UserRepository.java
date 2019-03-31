package com.xuyp.happychat.dao;

import com.xuyp.happychat.model.mongo.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    int countByUsernameAndPassword(String id, String password);

    int countByUsername(String userName);

    Optional<User> findByUsername(String userName);

    Optional<List<User>> findByUsernameLike(String userName);

}
