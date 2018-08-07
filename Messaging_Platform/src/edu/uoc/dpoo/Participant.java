package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.List;

public class Participant extends User implements CompetitionListener{

    private List<Submission> submissions;
    
    public Participant(User user) {
    	super(user); 
    	submissions = new ArrayList<Submission>();
    }
    
    /*
     * submit the prediction 
     * */
    public Submission submitPrediction(Competition competition, float prediction) {
    	Submission submission = null;
    	if (competition.isOpen()) {
    		submission = new Submission(this, competition, prediction);
        	competition.getSubmissions().add(submission);
        	competition.evaluate(submission);//*****************
        	
        	submissions.add(submission);
    	}
    	
    	
    	
        return submission;
    }
    
    public List<Submission> getSubmissions() {        
        return submissions;
    }
    
    public void onCompetitionClosed(){
    	
    	
    	
    }

}
