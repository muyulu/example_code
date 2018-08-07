package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR1_Ex2_1_Test {
    
    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String fullName1 = "Test User 1";
    
    private final String username2 = "username2";
    private final String password2 = "password2";
    private final String fullName2 = "Test User 2";
    
    public PR1_Ex2_1_Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    // Test create new user    
    @Test
    public void createUser() {        
        Platform platform = new Platform();
        
        // Check that no user is in the platform
        assertEquals((int)platform.getNumUsers(), 0);
        
        // Register new user
        User u1=platform.registerUser(username1, password1, fullName1);
        
        // User is not NULL
        assertNotNull(u1);
        
        // User full name is correct
        assertEquals(u1.getFullName(), fullName1);        
        
        // Check that only 1 user is in the platform (login does not created new user)
        assertEquals((int)platform.getNumUsers(), 1);
    }
    
     // Test create new user controls  
    @Test
    public void createUserControls() {        
        Platform platform = new Platform();
        
        // Check that no user is in the platform
        assertEquals((int)platform.getNumUsers(), 0);
        
        // Register new user
        User u1=platform.registerUser(username1, password1, fullName1);
        assertNotNull(u1);
        assertEquals((int)platform.getNumUsers(), 1);
        
        // Try to register the same user again
        User u2=platform.registerUser(username1, password1, fullName1);
        assertNull(u2);
        assertEquals((int)platform.getNumUsers(), 1);
        
        // Register new user
        User u3=platform.registerUser(username2, password2, fullName2);
        assertNotNull(u3);
        assertEquals((int)platform.getNumUsers(), 2);        
    }
    
}
