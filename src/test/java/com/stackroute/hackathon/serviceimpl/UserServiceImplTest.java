package com.stackroute.hackathon.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.hackathon.HackathonApplication;
import com.stackroute.hackathon.dao.UserServiceImpl;
import com.stackroute.hackathon.domains.User;
import com.stackroute.hackathon.repositorycontracts.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class UserServiceImplTest {
	@Autowired
	private UserServiceImpl userService;
	
	@Before
	public void setup() {
		
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test
	public void testForOutOfBoundsId() {
		long id = 500;
		User retrievedUser = null;
		try {
			retrievedUser = userService.retrieveUserById(id);
		} catch (Exception e) {
			
		}
		assertNull("User should retrive a null object", retrievedUser);
	}
	
	@Test
	public void testForGetAllUsers() {
		List<User> retrievedUsers = null;
		try {
			retrievedUsers = userService.retrieveAllUsers();
		} catch (Exception e) {
			assertEquals(e.getMessage(), "No users exist");
		}
//		assertEquals("Should retrieve empty users list  when no user in the dayabase", retrievedUsers.size(), 0);
	
	}

}
