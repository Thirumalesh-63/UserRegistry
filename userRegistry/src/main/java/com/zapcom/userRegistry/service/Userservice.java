package com.zapcom.userRegistry.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zapcom.common.model.DatabaseSequence;
import com.zapcom.common.model.User;
import com.zapcom.userRegistry.Repository.DatabaseSequenceRepository;
import com.zapcom.userRegistry.Repository.UserRepository;
import com.zapcom.userRegistry.exceptions.usernotfoundexception;

@Service
public class Userservice {


	Logger log=LoggerFactory.getLogger(Userservice.class);

	@Autowired
	private UserRepository userrepository;
	@Autowired
	private MongoTemplate mongoTemplate;

	public User createUser(User user) {
		int id=generateSequence("user-sequence");
		user.setId(id);
		return userrepository.save(user);

	}
	@Transactional
	public int generateSequence(String seqName) {
		Query query = Query.query(Criteria.where("_id").is(seqName));
		Update update = new Update().inc("seq", 1);

		DatabaseSequence counter = mongoTemplate.findAndModify(
				query,
				update,
				FindAndModifyOptions.options().returnNew(true).upsert(true), // Ensure upsert and return the new document after the update
				DatabaseSequence.class
				);
		if (counter == null) {
			System.err.println(counter.getSeq());
			counter = new DatabaseSequence();
			counter.setId(seqName);
			counter.setSeq(1);
			mongoTemplate.save(counter);
			return 1;
		}

		return counter != null ? (int) counter.getSeq() : 1;
	}


	public Optional<User> getUser(int id) {
		// TODO Auto-generated method stub
		Optional<User> user=userrepository.findById(id);

		if(user.isPresent()) {
			return userrepository.findById(id);
		}
		else throw new usernotfoundexception("usernot found with that id " + id);
	}


	public Page<User> getAllUsers(int page,int size) {

		Pageable pageable = PageRequest.of(page, size);
		// TODO Auto-generated method stub
		return userrepository.findAll(pageable);

	}


	public User updateUser(int id, User userDetails) {
		Optional<User> user=userrepository.findById(id);
		if(user.isPresent()) {
			if(userDetails.getName()!=null)
				user.get().setName(userDetails.getName());
			if(userDetails.getAddress()!=null)
				user.get().setAddress(userDetails.getAddress());
			if(userDetails.getEmail()!=null)
				user.get().setEmail(userDetails.getEmail());
			if(userDetails.getPassword()!=null)
				user.get().setPassword(userDetails.getPassword());
			if(userDetails.getPhonenumber()!=null)
				user.get().setPhonenumber(userDetails.getPhonenumber());
			if(userDetails.getRole()!=null)
				user.get().setRole(userDetails.getRole());
			return userrepository.save(user.get());
		}
		else {
			throw new usernotfoundexception("usernot found with that id " +id);
		}

	}


	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		Optional<User> user=userrepository.findById(id);
		if(user.isPresent())
		{
			userrepository.deleteById(id);
			return true;
		}
		else {
			throw new usernotfoundexception("usernot found with that id " +id);
		}
	}

}
