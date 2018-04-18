package com.stackroute.hackathon.validation;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.stackroute.hackathon.domains.User;
import com.stackroute.hackathon.exceptions.InvalidUserEmailException;
import com.stackroute.hackathon.exceptions.InvalidUserIdException;
import com.stackroute.hackathon.exceptions.InvalidUserNameException;

import java.util.regex.Matcher;

@Component
public class UserValidator {
	public void validateUserEmail(String email) throws InvalidUserEmailException{
		Pattern p = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(email);
		if(!matcher.find())
			throw new InvalidUserEmailException("Invalid user email: " + email);
	}
	
	public void validateUserName(String name) throws InvalidUserNameException {
		if(name == null) {
			throw new InvalidUserNameException("Invalid user name");
		}
		if(name.equals("")) {
			throw new InvalidUserNameException("Invalid user name");
		}
	}
	
	public void validateUserId(long id) throws InvalidUserIdException {
		if(id <= 0) {
			throw new InvalidUserIdException("Invalid user id: " + id);
		}
	}
	
	public void validate(User user) throws InvalidUserIdException, InvalidUserEmailException, InvalidUserNameException {
		validateUserId(user.getId());
		validateUserEmail(user.getEmail());
		validateUserName(user.getName());
	}
}
