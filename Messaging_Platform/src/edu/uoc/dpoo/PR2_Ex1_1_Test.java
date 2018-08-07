package edu.uoc.dpoo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PR2_Ex1_1_Test {
    
    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String fullName1 = "Test User 1";
    
    private final String username2 = "username2";
    private final String password2 = "password2";
    private final String fullName2 = "Test User 2";
      
    private final String msg_subject1 = "Subject msg 1";
    private final String msg_body1 = "Text of the message 1";
    
    private final String msg_subject2 = "Subject msg 2";
    private final String msg_body2 = "Text of the message 2";
    
    private Platform platform = null;
    private User u1 = null;
    private User u2 = null;
    
    public PR2_Ex1_1_Test() {
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
    // Test create new message
    @Test
    public void createMessage() throws InterruptedException {        
        // Create two messages
        Message m1 = new Message(u1, u2, msg_subject1, msg_body1);
        Message m2 = new Message(u1, u2, msg_subject2, msg_body2);
        
        // Check the status
        assertEquals(m1.getStatus(), MessageStatus.PENDING);
        assertEquals(m2.getStatus(), MessageStatus.PENDING);
        
        // Check the equals method
        assertEquals(m1, m1);
        assertEquals(m2, m2);
        //assertNotEquals(m1, m2);
        assertThat(m1, is(not(equalTo(m2))));
        
        // Wait 1 second and create another message
        Thread.sleep(1000);        
        Message m3 = new Message(u1, u2, msg_subject1, msg_body1);
        
        // Since the dates are different (minimum 1 second), the messages cannot be the same
        assertThat(m1, is(not(equalTo(m3))));
        //assertNotEquals(m1,m3);            
    }
    
    // Test user toString
    @Test
    public void toStringUser() throws InterruptedException {        
        // Check User class toString, used by message toString
        assertTrue(u1.toString().startsWith(u1.getFullName()));
        assertTrue(u1.toString().endsWith("<" + u1.getUserName() + ">"));
    }
    
    // Test message toString
    @Test
    public void toStringMessage() throws InterruptedException {        
        // Create a message
        Message m1 = new Message(u1, u2, msg_subject1, msg_body1);
        
        // Check the strign representation
        String msg = m1.toString();
        
        // Check initial and final values
        assertTrue(msg.startsWith("{"));
        assertTrue(msg.endsWith("}"));
        
        // Get the values from the string representation of the object   
        Pattern pattern = Pattern.compile("(\\w*:[^,\\}]*)*");
        Matcher m = pattern.matcher(msg);
        Date m_date = null;
        String m_from = null;
        String m_to = null;
        String m_subject = null;
        MessageStatus m_status = null;
        while(m.find()) {
            if(m.groupCount()>0 && m.group(0).length()>0) {    
                String[] pair = m.group(0).split(":",2);      
                // Remove spaces in the key
                pair[0]=pair[0].trim();
                // Remove spaces at the beging and end of the value
                while(pair[1].charAt(0)==' ') {
                    pair[1]=pair[1].substring(1);
                }
                while(pair[1].charAt(pair[1].length()-1)==' ') {
                    pair[1]=pair[1].substring(0, pair[1].length()-1);
                }
                if("date".equalsIgnoreCase(pair[0])) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    try {
                        m_date = format.parse(pair[1]);
                    } catch (ParseException ex) {
                        fail("Invalid data format: " + ex.getMessage());
                    }
                } else if("from".equalsIgnoreCase(pair[0])) {
                    m_from = pair[1];
                } else if("to".equalsIgnoreCase(pair[0])) {
                    m_to = pair[1];
                } else if("subject".equalsIgnoreCase(pair[0])) {
                    m_subject = pair[1];
                } else if("status".equalsIgnoreCase(pair[0])) {
                    if("PENDING".equals(pair[1])) {
                        m_status = MessageStatus.PENDING;
                    }
                    if("READ".equals(pair[1])) {
                        m_status = MessageStatus.READ;
                    }
                } 
            }
        }
        
        // First check that all information is provided
        assertNotNull(m_date);
        assertNotNull(m_from);
        assertNotNull(m_to);
        assertNotNull(m_subject);
        assertNotNull(m_status);
                
        // Check the values
        assertTrue((new Date().getTime() - m_date.getTime())< 15000); // Difference between mail date and current date less than 15 seconds
        assertTrue(m_from.startsWith(u1.getFullName()));
        assertTrue(m_from.endsWith("<" + u1.getUserName() + ">"));
        assertTrue(m_to.startsWith(u2.getFullName()));
        assertTrue(m_to.endsWith("<" + u2.getUserName() + ">"));
        assertEquals(m_subject, msg_subject1);
        assertEquals(m_status, MessageStatus.PENDING);
    }    
}
