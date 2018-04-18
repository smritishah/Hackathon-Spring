package com.stackroute.hackathon.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.hackathon.domains.User;
import com.stackroute.hackathon.exceptions.UserAlreadyExistsException;
import com.stackroute.hackathon.exceptions.UserDoesNotExistException;
import com.stackroute.hackathon.repositorycontracts.UserRepository;
import com.stackroute.hackathon.servicecontracts.UserService;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User addUser(User user) throws Exception {
		User retrievedUser = userRepository.findOne(user.getId());
		if(retrievedUser == null)
			return userRepository.save(user);
		else
			throw new UserAlreadyExistsException("User already exists with id: " + user.getId());
	}

	@Override
	public User update(User user) throws Exception{
		User u = userRepository.findOne(user.getId());
		if(u==null)
			throw new UserAlreadyExistsException("User already exists with id: " + user.getId());
		else
			return userRepository.save(user);
	}

	@Override
	public User retrieveUserById(long id) throws Exception {
		User user = userRepository.findOne(id);
		if(user == null)
			throw new UserDoesNotExistException("User does not exist with id: " + id);
		else
			return user;
	}

	@Override
	public List<User> retrieveAllUsers() throws Exception{
		Iterable<User> iterable = userRepository.findAll();
		List<User> users = new ArrayList<User>();
		for(User user:  iterable) {
			users.add(user);
		}
		if (users.size() == 0)
			throw new UserDoesNotExistException("No users exist");
		else
			return users;
	}

	@Override
	public User deleteUserById(long id) throws Exception{
		User userToDelete = userRepository.findOne(id);
		if(userToDelete == null)
			throw new UserDoesNotExistException("User does not exist with id: " + id);
		else {
			userRepository.delete(id);
			return userToDelete;
		}
	}

	@Override
	public void setUserRepository(UserRepository userRepository) {
		// TODO Auto-generated method stub
		this.userRepository = userRepository;
		
	}

}
