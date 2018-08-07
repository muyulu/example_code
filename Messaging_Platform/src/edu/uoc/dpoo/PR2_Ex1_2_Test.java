package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex1_2_Test {
    
    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String fullName1 = "Test User 1";
    
    private final String username2 = "username2";
    private final String password2 = "password2";
    private final String fullName2 = "Test User 2";
    
    private final String username3 = "username3";
    private final String password3 = "password3";
    private final String fullName3 = "Test User 3";
    
    private final String msg_subject1 = "Subject msg 1";
    private final String msg_body1 = "Text of the message 1";
    
    private final String msg_subject2 = "Subject msg 2";
    private final String msg_body2 = "Text of the message 2";
    
    private final String msg_subject3 = "Subject msg 3";
    private final String msg_body3 = "Text of the message 3";
    
    private Platform platform = null;
    private User u1 = null;
    private User u2 = null;
    private User u3 = null;
    
    public PR2_Ex1_2_Test() {
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
        u3 = platform.registerUser(username3, password3, fullName3);
    }
    
    @After
    public void tearDown() {
        
    }  
    
    // Send a message
    @Test
    public void sendMessage() {        
        
        /*
        JUnit provides from version 3 different annotations to deal with exception tests. 
        For simplicity and compatibility, standard classic try-catch strategy is used
        in this test.
        */
        
        // Check exceptions
        
        // Send a message with a null destination username
        try {
            u1.sendMessage(null, msg_subject1, msg_body1);            
            fail();
        } catch (CompetitionException ce) {
            assertEquals(ce.getMessage(), CompetitionException.RECIPIENT_NOT_FOUND);
        } catch (Exception e) {            
            fail();
        }
        
        // Send a message with a non existing destination username
        try {
            u1.sendMessage("unexisting_user", msg_subject1, msg_body1);            
            fail();
        } catch (CompetitionException ce) {
            assertEquals(ce.getMessage(), CompetitionException.RECIPIENT_NOT_FOUND);
        } catch (Exception e) {            
            fail();
        }
        
        // Send a message from a not registered user
        User u_test=new User(platform, "not_registered", "a password", "Unregistered User");
        try {
            u_test.sendMessage(username2, msg_subject1, msg_body1);            
            fail();
        } catch (CompetitionException ce) {
            assertEquals(ce.getMessage(), CompetitionException.SENDER_NOT_FOUND);
        } catch (Exception e) {
            fail();
        }
        
        // Check that initially, the inbox and outbox are empty
        assertEquals(0, u1.getInbox().size());
        assertEquals(0, u1.getOutbox().size());
        assertEquals(0, u2.getInbox().size());
        assertEquals(0, u2.getOutbox().size());        
        assertEquals(0, u3.getInbox().size());
        assertEquals(0, u3.getOutbox().size());        
        
        // Send message from u1 to u2
        try {
            Message m=u1.sendMessage(username2, msg_subject1, msg_body1);
            
            // Check that this message is in the u1 outbox
            assertEquals(1, u1.getOutbox().size());
            Message m_o = u1.getOutbox().get(0);
            assertEquals(m, m_o);
            
            // Check that this message is in the u2 inbox
            assertEquals(1, u2.getInbox().size());
            Message m_i = u2.getInbox().get(0);
            assertEquals(m, m_i);            
            
            // Check the status of the rest of folders
            assertEquals(0, u1.getInbox().size());            
            assertEquals(0, u2.getOutbox().size());            
            assertEquals(0, u3.getInbox().size());
            assertEquals(0, u3.getOutbox().size());        
        } catch (CompetitionException ex) {
            fail();
        }
    }
}
