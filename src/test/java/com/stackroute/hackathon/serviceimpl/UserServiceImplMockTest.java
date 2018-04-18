package com.stackroute.hackathon.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.hackathon.HackathonApplication;
import com.stackroute.hackathon.dao.UserServiceImpl;
import com.stackroute.hackathon.domains.User;
import com.stackroute.hackathon.exceptions.UserAlreadyExistsException;
import com.stackroute.hackathon.repositorycontracts.UserRepository;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceImplMockTest {
	private UserServiceImpl userServiceImpl;
	private long id = 0;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private User user;
	
	@Before
	public void setupMockito() {
		MockitoAnnotations.initMocks(this);
        
		userServiceImpl = new UserServiceImpl();
        userServiceImpl.setUserRepository(userRepository);
	}
	
	@After
	public void teardownMockito() {
		this.id = 0;
		this.userServiceImpl = null;
	}
	
	@Test
	public void testaddUser() {
		user = new User();
		user.setId(1);
		user.setName("Abhishek");
		user.setEmail("abhi@gmail.com");
		//Arrange
//		when(userRepository.findOne((long) 22201)).thenReturn(this.generateUser("Abhishek", "abhi@gmail.com"));
		when(userRepository.save(user)).thenReturn(this.generateUser("Abhishek", "abhi@gmail.com"));
		//Act
		try {
			User retrievedUser = userServiceImpl.retrieveUserById((long) 22201);
			//Assert
			assertEquals("Expected Name did not match", retrievedUser.getName(), "Abhishek");
			assertEquals("Expected Id did not match", retrievedUser.getId(), 1);
		} catch(UserAlreadyExistsException e) {
			
		} catch (Exception e) {
			
		}
		
	}
	
//	@Test
//	public void testNegativeAddUser() {
//		user = new User();
//		user.setId(1);
//		user.setName("Abhishek");
//		user.setEmail("abhi@gmail.com");
//		//Arrange
////		when(userRepository.findOne((long) 22201)).thenReturn(this.generateUser("Abhishek", "abhi@gmail.com"));
//		
//		
//		//Act
//		try {
//			when(userRepository.save(user)).thenReturn(this.addUser(user));
////			User retrievedUser = userServiceImpl.retrieveUserById((long) 22201);
//			userServiceImpl.addUser(user);
//			userServiceImpl.addUser(user);
//			//Assert
////			assertEquals("Expected Name did not match", retrievedUser.getName(), "Abhishek");
////			assertEquals("Expected Id did not match", retrievedUser.getId(), 1);
//			
////			when(user.getId()==1).thenThrow(UserAlreadyExistsException.class);
//		} catch(UserAlreadyExistsException e) {
//			System.out.println("Caught in user already exists exception");
//		} 
//		catch (Exception e) {
//			System.out.println("Caught in exception");
//		}
//		
//	}
	
	public User generateUser(String name, String email) {
		this.id++;
		User user = new User();
		user.setId(this.id);
		user.setName(name);
		user.setEmail(email);
		return user;
	}
	
	public User addUser(User user) throws UserAlreadyExistsException {
		if(user.getId() <= this.id) {
			throw new UserAlreadyExistsException("user already present");
		}
		this.id++;
		user.setId(id);
		return user;
	}
}
