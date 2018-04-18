package com.stackroute.hackathon.controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.hackathon.domains.User;
import com.stackroute.hackathon.servicecontracts.UserService;
import com.stackroute.hackathon.validation.UserValidator;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v2.0/api/rest-user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	private UserValidator userValidator;
	
	private static Logger logger = LogManager.getRootLogger();
	
	@ApiOperation(value = "Add a new User")
	@PostMapping(value = "/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUser(@RequestBody User user) {
		try {
			  userValidator.validate(user);
	          userService.addUser(user);
	          return new ResponseEntity<User>(user,HttpStatus.CREATED);
	      }
	      catch(Exception e) { 	   
	    	  logger.error(e.getMessage());
	          return new ResponseEntity<String>("{\"msg\": \""+ e.getMessage() +  "\"}",HttpStatus.CONFLICT);
	      }
	}
	
	@ApiOperation(value = "Update an existing User")
	@PutMapping(value="/user", consumes = MediaType.APPLICATION_JSON_VALUE, produces =  MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		 try {
			 userValidator.validate(user);
	       		userService.update(user);
	            return new ResponseEntity<User>(user,HttpStatus.OK);
	       }
	        catch(Exception e) {
	            return new ResponseEntity<String>("{\"msg\": \""+ e.getMessage() +  "\"}",HttpStatus.CONFLICT);
	        }
	}
	
	@ApiOperation(value = "Retrieve a User by ID")
	@GetMapping("/user/{id}")
	public ResponseEntity<?> getUser(@PathVariable Long id) {
		User retrievedUser = null;
    	try {
    		retrievedUser = userService.retrieveUserById(id);
    		return new ResponseEntity<User>(retrievedUser, HttpStatus.OK);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<String>("{\"msg\": \""+ e.getMessage() +  "\"}",HttpStatus.CONFLICT);
    	}
    }
	
	@ApiOperation(value = "Retrive all Users", response = Iterable.class)
	@GetMapping("/user")
	public ResponseEntity<?> getAllUsers() {
		try {
    		return new ResponseEntity<List>(userService.retrieveAllUsers(),HttpStatus.OK);
    	}
    	catch(Exception e) {
    		return new ResponseEntity<String>("{\"msg\": \""+ e.getMessage() +  "\"}",HttpStatus.CONFLICT);
    	}
	}
	
	@ApiOperation(value = "Delete a User by ID")
	@DeleteMapping("/user/{id}")
	public ResponseEntity deleteUser(@PathVariable Long id) {
		if(id <= 0) {
			return new ResponseEntity<String>("{\"msg\": \"Enter a valid id\"}",HttpStatus.CONFLICT);
		}
		try {
			User deletedUser = userService.deleteUserById(id);
            return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
        }
        catch(Exception e) {
            return new ResponseEntity<String>("{\"msg\": \""+ e.getMessage() +  "\"}", HttpStatus.CONFLICT);
        }
	}
}

