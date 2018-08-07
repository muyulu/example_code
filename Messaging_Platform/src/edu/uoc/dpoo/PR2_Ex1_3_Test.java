package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex1_3_Test {
    
    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String fullName1 = "Test User 1";
    
    private final String username2 = "username2";
    private final String password2 = "password2";
    private final String fullName2 = "Test User 2";
        
    private final String msg_subject1 = "Subject msg 1";
    private final String msg_body1 = "Text of the message 1";
    
    private Platform platform = null;
    private User u1 = null;
    private User u2 = null;
    
    public PR2_Ex1_3_Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        // Create the test scenario
        platform = new Platform();
        
        // Register new users
        u1 = platform.registerUser(username1, password1, fullName1);        
        u2 = platform.registerUser(username2, password2, fullName2);        
    }
    
    @After
    public void tearDown() {
        
    }  
    
    // Read messages
    @Test
    public void getMessage() {                
        // Check that initially, the inbox and outbox are empty
        assertEquals(0, u1.getInbox().size());
        assertEquals(0, u1.getOutbox().size());
        assertEquals(0, u2.getInbox().size());
        assertEquals(0, u2.getOutbox().size());        
        
        // Send message from u1 to u2
        try {
            u1.sendMessage(username2, msg_subject1, msg_body1);
            
            // Check that u1 has no messages to read
            assertNotNull(u1.getMessages());
            assertEquals(0, u1.getMessages().size());
            
            // Check that u2 has one message to read
            assertNotNull(u2.getMessages());
            assertEquals(1, u2.getMessages().size());
            
            // Check the status of the messages
            Message m1 = u1.getOutbox().get(0);
            Message m2 = u2.getMessages().get(0);
            
            assertEquals(MessageStatus.PENDING, m1.getStatus());
            assertEquals(MessageStatus.PENDING, m2.getStatus());            
        } catch (CompetitionException ex) {
            fail();
        }
    }
    
    // Read messages
    @Test
    public void readMessage() {                
        // Check that initially, the inbox and outbox are empty
        assertEquals(0, u1.getInbox().size());
        assertEquals(0, u1.getOutbox().size());
        assertEquals(0, u2.getInbox().size());
        assertEquals(0, u2.getOutbox().size());        
        
        // Send message from u1 to u2
        try {
            u1.sendMessage(username2, msg_subject1, msg_body1);
            
            // Check the status of the messages
            Message m1 = u1.getOutbox().get(0);
            Message m2 = u2.getMessages().get(0);
            
            assertEquals(MessageStatus.PENDING, m1.getStatus());
            assertEquals(MessageStatus.PENDING, m2.getStatus());
            
            // Check that the read action works on both sides
            m2.read();            
            assertEquals(MessageStatus.READ, m1.getStatus());
            assertEquals(MessageStatus.READ, m2.getStatus());
            
            // Check that no message is returned as non read
            assertEquals(0, u1.getMessages().size());
            assertEquals(0, u2.getMessages().size());
            
        } catch (CompetitionException ex) {
            fail();
        }
    }
}
