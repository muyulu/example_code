package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Organizer extends User {
	//only organizer's competitions
    private List<Competition> competitions;
    
    public Organizer(User user) {
        super(user);
        // Initialize the list of competitions
        competitions = new ArrayList<Competition>();                
    }

    public void removeSubmission(Submission submission) {
        /* NOT IMPLEMENTED */
    }

    public boolean sendMessage(Competition competition, String subject, String message) {
        /* NOT IMPLEMENTED */
        return false;
    }

    public Competition newCompetition(String title, float target) {   
    	
    	Competition competition = new Competition(this.getPlatform(), this, title, target);
    	this.getPlatform().incNumCompetitions();
    	this.getPlatform().registerCompetition(competition);
    	competitions.add(competition);    	
    	
        return competition;
    }
    
    /*
     * close the competition and remove it from the list
     * */
    public void closeCompetition(Competition competition) {
        
    	competition.close();
    	
    	Iterator<Competition> it2 = this.competitions.listIterator();
    	Competition c = null;
    	while (it2.hasNext()) {
    		
    		
    		if (it2.next().equals(competition)) {
    			it2.remove();
    		}
    		
    	}
    	
    	//let the observers know
    	Iterator<CompetitionListener> it = competition.getListeners().iterator();
    	
    	while (it.hasNext()) {
    		it.next().onCompetitionClosed();
    	}
    }
    
    public List<Competition> getCompetitions() {   
    	
        return this.competitions;
    }
    
    /*
     * update the competitions by adding the competition in the platform to this list
     * */
    public void updateCompetitions() { 
        Iterator<Competition> it = this.getPlatform().getOpenCompetitions().iterator();
        Competition c = null;
        while (it.hasNext()) {
        	
        	c = it.next();
        	
        	competitions.add(c);
        		
        }
        
    }
}
