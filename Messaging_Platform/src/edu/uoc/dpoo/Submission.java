package edu.uoc.dpoo;

import java.util.Comparator;
import java.util.Date;

public class Submission implements Comparable<Submission>{
    //private Integer id;
    private static Integer id = 0;
    private Participant participant;
    private Competition competition;    
    private SubmissionStatus status;
    private Date submittedAt;
    private float prediction;
    private float error;
      
    public Submission(Participant participant, Competition competition, float prediction) {
       this.submittedAt = new Date();
       id++;
       this.status = this.status.PENDING;
       this.participant = participant;
       this.competition = competition;
       this.prediction = prediction;
    }
    
    public SubmissionStatus getStatus() {       
        return status;
    }
    
    public float getError() {        
        return this.error;
    }
    
    public void setError(float error) {        
        this.error = error;        
    }
    
    public float getPrediction() {        
        return prediction;
    }
    
    public Participant getParticipant() {        
        return this.participant;
    }
    
    public void setSubmissionStatus (SubmissionStatus ss) {
    	this.status = ss;
    }

    /*
     * compareTo method of comparable to order the list by error value. 
     * Checks for the lesser error value, in ascending order
     * */
	public int compareTo(Submission s) {
		 
		float compareQuantity = ((Submission) s).error;

		//ascending order
		int ret = Float.compare(this.error, compareQuantity);
		
		return ret;
		
	}
	
	/*
     * Comparator to order the list by status
     * */
	public static Comparator<Submission> SubmissioneComparator
    = new Comparator<Submission>() {

		public int compare(Submission s1, Submission s2) {

			
			
			String state1 = s1.status.name();
			String state2 = s2.status.name();
			
			return state1.compareTo(state2);
		}

	};
	
	/*
     * equals method
     * */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Submission))
			return false;
		Submission other = (Submission) obj;
		if (competition == null) {
			if (other.competition != null)
				return false;
		} else if (!competition.equals(other.competition))
			return false;
		if (Float.floatToIntBits(error) != Float.floatToIntBits(other.error))
			return false;
		if (participant == null) {
			if (other.participant != null)
				return false;
		} else if (!participant.equals(other.participant))
			return false;
		if (Float.floatToIntBits(prediction) != Float.floatToIntBits(other.prediction))
			return false;
		if (status != other.status)
			return false;
		if (submittedAt == null) {
			if (other.submittedAt != null)
				return false;
		} else if (!submittedAt.equals(other.submittedAt))
			return false;
		return true;
	}

	/*
     * toString method
     * */
	public String toString() {
		return "Submission [status=" + status + ", error=" + error + "]";
	}
}
