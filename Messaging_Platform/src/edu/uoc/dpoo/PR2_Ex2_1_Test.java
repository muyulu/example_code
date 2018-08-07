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

public class PR2_Ex2_1_Test {
    
    private final String username1 = "username1";
    private final String password1 = "password1";
    private final String fullName1 = "Test User 1";
    
    private final String username2 = "username2";
    private final String password2 = "password2";
    private final String fullName2 = "Test User 2";
    
    private final String comp_title1 = "Approximate number PI";
    private final float comp_target1 = 3.141592653589793238f;
        
    private Platform platform = null;
    private User u1 = null;    
    private User u2 = null;    
    
    public PR2_Ex2_1_Test() {        
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
    
    @Test
    public void createCompetitionObject() {   
        Organizer o1 = u1.asOrganizer();
        
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);
        
        assertNotNull(c1);
        assertEquals(o1, c1.getOwner());        
        assertTrue(c1.isOpen()); 
        assertNotNull(c1.getSubmissions());
        assertEquals(0, c1.getSubmissions().size());
    }
    
    @Test
    public void duplicatedCompetitionObjects() {   
        Organizer o1 = u1.asOrganizer();        
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);
        Competition c2 = o1.newCompetition(comp_title1, comp_target1);
        
        // If id is correct, they cannot be equals
        assertThat(c1, is(not(equalTo(c2))));
        
        // Id cannot depend on the owner
        Organizer o2 = u2.asOrganizer();   
        Competition c3 = o2.newCompetition(comp_title1, comp_target1);               
        assertThat(c1, is(not(equalTo(c3))));        
    }
    
    @Test
    public void AssociatedObjects() {   
        Organizer o1 = u1.asOrganizer();        
        Competition c1 = o1.newCompetition(comp_title1, comp_target1);       
        
        assertEquals(new Integer(1), platform.getNumCompetitions());
        assertEquals(1, o1.getCompetitions().size());
    }
}
