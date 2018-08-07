package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Competition {
    private Platform platform;
    private Integer id;
    private String title;
    private float target;
    private Boolean isActive;
    private Organizer owner;
    private List<Submission> submissions;
      
    //static, so that the value be shared between classes
    private static Integer numCompetitionsGlobal = 0;
    
    private List<CompetitionListener> listeners = new ArrayList<CompetitionListener>();

	public Competition(Platform platform, Organizer owner, String title, float target) {
        
    	numCompetitionsGlobal++;
    	
    	this.title = title;
    	this.target = target;
    	
    	this.id = numCompetitionsGlobal;
    	
    	this.platform = platform;
    	this.owner = owner;
    	
    	this.isActive = true;
    	
    	submissions = new ArrayList<Submission>();
    }
	
	/*
     * evaluate the submission. The final value must be absolute
     * */
    public float evaluate(Submission submission) {  
    	
    	Float f = submission.getPrediction();
    	
    	Float evaluation = target - f;
    	if (evaluation < 0) {
    		evaluation *= ((float) -1);
    	}
    	submission.setError(evaluation);
    	Iterator<Submission> it = this.submissions.iterator();
    	
        return evaluation;
        
    }
    
    
    
    public Organizer getOwner() {        
        return owner;
    }
    
    public Boolean isOpen() {        
        return isActive;
    }
        
    public void close() {
        this.isActive = false;
    }
    
    /*
     * get the winner. The submission must have the status DONE and the list not be null
     * */
    public Participant getWinner() {       
    	
    	Participant p= null;
    	
    	if (!submissions.isEmpty()) {
    		int min = submissions.indexOf(Collections.min(submissions));
    		p = submissions.get(min).getParticipant();
    		if (submissions.get(min).getStatus().name() == "DONE") {
    			return p;
    		}
    	}
    	
        return null;
    }


    /*
     * equals method
     * */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Competition))
			return false;
		Competition other = (Competition) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isActive == null) {
			if (other.isActive != null)
				return false;
		} else if (!isActive.equals(other.isActive))
			return false;
		if (listeners == null) {
			if (other.listeners != null)
				return false;
		} else if (!listeners.equals(other.listeners))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (platform == null) {
			if (other.platform != null)
				return false;
		} else if (!platform.equals(other.platform))
			return false;
		if (submissions == null) {
			if (other.submissions != null)
				return false;
		} else if (!submissions.equals(other.submissions))
			return false;
		if (Float.floatToIntBits(target) != Float.floatToIntBits(other.target))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	public void sendMessage(String subject, String message) {

    }
    
	/*
     * sort the submissions using Comparable. The sorting must be ascendent and first the
     * one with DONE status
     * */
	public List<Submission> getSubmissions() {     
    	
		
		Collections.sort(submissions);
		Collections.sort(submissions, Submission.SubmissioneComparator);

    	
    	Iterator<Submission> it = submissions.iterator();
    	
    	Submission s = null;
    	
        return submissions;        
    }
    
    public List<CompetitionListener> getListeners() {
  		return listeners;
  	}
    
    
    /*
     * Get the participants and let the observers know using the listener
     * */
    public List<Participant> getParticipants() {
    	List<Participant> lp = new ArrayList<Participant>();
    	
    	Iterator<CompetitionListener> it = this.listeners.iterator();
    	
    	CompetitionListener cp = null;
    	
    	while(it.hasNext()){
    		cp = it.next();
    		
    		if(cp instanceof Participant){
    			lp.add((Participant)cp);
    		}
    	}
    	
    	return lp;
    }
    
    /*
     * evaluate all the submissions, if the status is PENDING, evaluate it and change it to DONE
     * */
    public void evaluateAll() {
       
    	Iterator<Submission> it = submissions.iterator();
    	Submission s = null;
    	while (it.hasNext()) {
    		s = it.next();
    		if (s.getStatus().equals(SubmissionStatus.PENDING)) {
    			this.evaluate(s);
    			s.setSubmissionStatus(SubmissionStatus.DONE);
    			
    			Iterator<CompetitionListener> itls = this.listeners.iterator();
    			
    			while (itls.hasNext()) {
    				
    				itls.next().onNewEvaluation();
    				
    			}
    			
    			
    		}    		
    	}
    	
    }
    
    /*
     * add listeners to the list
     * */
    
    public void addListener(CompetitionListener listener) {
        
    	listeners.add(listener);
    	
    }
    
    
    
}
