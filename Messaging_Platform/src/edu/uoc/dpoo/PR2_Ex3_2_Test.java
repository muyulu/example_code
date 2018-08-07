package edu.uoc.dpoo;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex3_2_Test {
    
    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String fullName1 = "Test User 1";
    
    private final String username2 = "username2";
    private final String password2 = "password2";
    private final String fullName2 = "Test User 2";
    
    private final String username3 = "username3";
    private final String password3 = "password3";
    private final String fullName3 = "Test User 3";
    
    private final String comp_title1 = "Approximate number PI";
    private final float comp_target1 = 3.141592653589793238f;
    private final float comp1_pred1 = 3.141592f;
    private final float comp1_pred2 = 3.14f;
    
    private final String comp_title2 = "Approximate number e";
    private final float comp_target2 = 2.718281828459045235f;
    private final float comp2_pred1 = 2.718281f;
    private final float comp2_pred2 = 2.71f;
        
    private Platform platform = null;
    private User u1 = null;
    private User u2 = null;
    private User u3 = null;
    
    public PR2_Ex3_2_Test() {        
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
    
    @Test
    public void evalSubmission() {   
        Organizer o1 = u1.asOrganizer();
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);
        
        Participant p1 = u2.asParticipant();
        Submission s1 = p1.submitPrediction(c1, comp1_pred1);
        assertNotNull(s1);
        
        // Start evaluations
        assertEquals(SubmissionStatus.PENDING, s1.getStatus());
        platform.run();        
        assertEquals(SubmissionStatus.DONE, s1.getStatus());        
        assertEquals(0.0, Math.abs(comp_target1-comp1_pred1), 0.0001);        
    }
    
    @Test
    public void testEvents() {   
        TestUser tu1 = new TestUser(u1);
        Organizer o1 = u1.asOrganizer();        
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);
        
        Participant p1 = u2.asParticipant();
        Submission s1 = p1.submitPrediction(c1, comp1_pred1);
        
        c1.addListener(tu1);
        
        // Start evaluations        
        platform.run();        
        assertEquals(1, tu1.numEvals);
        
        tu1.numEvals=0;
        platform.run();        
        assertEquals(0, tu1.numEvals);
    }
}
