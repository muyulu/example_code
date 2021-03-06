package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class PR2_Ex2_2_Test {
    
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
    
    private final String comp_title2 = "Approximate number e";
    private final float comp_target2 = 2.718281828459045235f;
        
    private Platform platform = null;
    private User u1 = null;
    private User u2 = null;
    private User u3 = null;
        
    public PR2_Ex2_2_Test() {        
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
    public void closeCompetition() {   
        Organizer o1 = u1.asOrganizer();        
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);
        assertEquals(1, o1.getCompetitions().size());        
        assertEquals(1, platform.getOpenCompetitions().size());
        assertEquals(c1, platform.getOpenCompetitions().get(0));
    }
    
    @Test
    public void listOpenCompetitions() {   
        assertEquals(0, platform.getOpenCompetitions().size());
        
        Organizer o1 = u1.asOrganizer();        
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);        
        o1.closeCompetition(c1);
                
        assertEquals(0, platform.getOpenCompetitions().size());
        assertEquals(new Integer(1), platform.getNumCompetitions());                        
    }
    
    @Test
    public void testEvent() {  
        TestUser tu1 = new TestUser(u1);
        Organizer o1 = u1.asOrganizer();
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);
        c1.addListener(tu1);
                
        o1.closeCompetition(c1);
        
        assertEquals(1, tu1.numClosed);
    }
}
