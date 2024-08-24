package com.zapcom.userRegistry.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.zapcom.common.model.User;


@Repository
public interface UserRepository extends MongoRepository<User, Integer> {

}
