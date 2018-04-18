package com.stackroute.hackathon.integration;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import com.stackroute.hackathon.HackathonApplication;
import com.stackroute.hackathon.domains.User;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HackathonApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IntegrationTest {
    @LocalServerPort
    private int port;
    
    TestRestTemplate restTemplate = new TestRestTemplate();
    HttpHeaders headers = new HttpHeaders();
    
    @Before
    public void setUp() throws Exception {  
        
    }
    
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
    
    @After
    public void tearDown() throws Exception {
    }
    
    @Test
    public void testAGetAllUsers() throws Exception {
        //Negative test when no users exist
        User u = new User();
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user"),
                HttpMethod.GET, entity, String.class);
        
        System.out.println("msg of response body:"+response.getBody());
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.CONFLICT,response.getStatusCode());
    }
    
    @Test
    public void testBGetUserByNonExistingId() throws Exception {
        User u = new User();
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user/5"),
                HttpMethod.GET, entity, String.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.CONFLICT,response.getStatusCode());
    }
    
    @Test
    public void testCAddNewUser() throws Exception {
        User u = new User(1,"User1","something@gmail.com");
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user"),
                HttpMethod.POST, entity, User.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.CREATED,response.getStatusCode());       
    }
    
    @Test
    public void testDAddExistingUser() throws Exception {
        User u = new User(1,"User1","something@gmail.com");
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user"),
                HttpMethod.POST, entity, String.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.CONFLICT,response.getStatusCode());       
    }
    
    @Test
    public void testEGetUserByExistingId() throws Exception {
        User u = new User();
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user/1"),
                HttpMethod.GET, entity, User.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.OK,response.getStatusCode());       
    }
    
    @Test
    public void testFUpdateExistingUser() throws Exception {
        User u = new User(1, "User1","updatedsomething@gmail.com");
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user"),
                HttpMethod.PUT, entity, User.class);
        System.out.println(response.getStatusCode());
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.OK,response.getStatusCode());       
    }
    
    @Test
    public void testGUpdateNonExistingUser() throws Exception {
        User u = new User(5, "User2","updatedsomething@gmail.com");
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<User> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user"),
                HttpMethod.PUT, entity, User.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected", HttpStatus.CONFLICT, response.getStatusCode());       
    }
    
    @Test
    public void testHDeleteExitingUser() throws Exception {
        User u = new User();
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user/1"),
                HttpMethod.DELETE, entity, String.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.OK,response.getStatusCode());       
    }
    
    @Test
    public void testIDeleteNonExitingUser() throws Exception {
        User u = new User();
        HttpEntity<User> entity = new HttpEntity<User>(u, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/v2.0/api/rest-user/user/1"),
                HttpMethod.DELETE, entity, String.class);
        
        assertNotNull("Expected some value but found null",response);
        assertEquals("Status code is not as expected",HttpStatus.CONFLICT,response.getStatusCode());              
    }
}