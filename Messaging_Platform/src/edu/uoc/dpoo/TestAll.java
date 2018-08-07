/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uoc.dpoo;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author xavie
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
    // Tests for PR1
    edu.uoc.dpoo.PR1_Ex2_1_Test.class,
    edu.uoc.dpoo.PR1_Ex2_3_Test.class, 
    edu.uoc.dpoo.PR1_Ex2_2_Test.class, 
    // Tests for PR2
    edu.uoc.dpoo.PR2_Ex1_1_Test.class,
    edu.uoc.dpoo.PR2_Ex1_2_Test.class,
    edu.uoc.dpoo.PR2_Ex1_3_Test.class,
    edu.uoc.dpoo.PR2_Ex2_1_Test.class,
    edu.uoc.dpoo.PR2_Ex2_2_Test.class,
    edu.uoc.dpoo.PR2_Ex2_3_Test.class,
    edu.uoc.dpoo.PR2_Ex3_1_Test.class,
    edu.uoc.dpoo.PR2_Ex3_2_Test.class,
    edu.uoc.dpoo.PR2_Ex3_3_Test.class,
    edu.uoc.dpoo.PR2_Ex3_4_Test.class
    })

public class TestAll {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
