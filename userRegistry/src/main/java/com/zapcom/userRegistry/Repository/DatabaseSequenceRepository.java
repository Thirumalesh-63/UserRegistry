package com.zapcom.userRegistry.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.zapcom.common.model.DatabaseSequence;

public interface DatabaseSequenceRepository  extends MongoRepository<DatabaseSequence,String>{

}
