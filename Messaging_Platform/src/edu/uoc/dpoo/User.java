package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class User implements CompetitionListener {
    private Platform platform;
    private String username;
    private String password;
    private String fullName;
    private List<Message> inbox;
    private List<Message> outbox;
    private List<Submission> submissions;
  
    public User (Platform platform, String username, String password, String fullName) {
        /**
         * PR1 Ex 2.1: User constructor needed for user registration
        */
        this.platform = platform;
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.inbox = new ArrayList<Message>();
        this.outbox = new ArrayList<Message>();
        this.submissions = new ArrayList<Submission>();
    }
    
    public User (User obj) {
        /**
         * PR1 Ex 2.3: Implementation of the copy constructor
        */
        this.platform = obj.platform;
        this.username = obj.username;
        this.password = obj.password;
        this.fullName = obj.fullName;
        this.inbox = obj.inbox;
        this.outbox = obj.outbox;
        this.submissions = obj.submissions;
    }
                
    public Boolean checkPassword(String password) {   
        /**
         * PR1 Ex 2.2: Implementation of checkPassword, required by login
        */
        return this.password.equals(password);        
    }
    
    /*
     * update competitions in organizer class
     * */
    public Organizer asOrganizer() {   
        /**
         * PR1 Ex 2.3: Create a new object for the Organizer Role
        */
    	Organizer o = new Organizer(this);
    	
    	o.updateCompetitions();
    	
        return o;
    }

    public Participant asParticipant() {
        /**
         * PR1 Ex 2.3: Create a new object for the Participant Role
        */
        return new Participant(this);
    }

    public String getUserName() {
        /**
         * PR1 Ex 2.1: Required by method findUser
        */
        return this.username;
    }
    
    public String getFullName() {
        /**
         * PR1 Ex 2.1: Required by test
        */
        return this.fullName;
    }
    
    /*
     * toString method
     * */
    public String toString() {        
        return this.fullName+" <"+this.username+">";
    }
    
    /*
     * equals method
     * */
    public boolean equals(Object obj) {
        /**
         * PR1 Ex 2.2: Required by test
        */
        if(obj==null) {
            return false;
        }        
        if (obj instanceof User) {
            User user = (User) obj;
            if (!this.username.equals(user.username) || !this.password.equals(user.password) || !this.fullName.equals(user.fullName)) {
                return false;
            }        
            // Additional checks can be added
        } else {
            return false;
        }
        
        return true;
    }
    
    /*
     * get pending messages
     * */
    public List<Message> getMessages() {  
    	List<Message> pending = new ArrayList();
    	for (Iterator<Message> it = this.getInbox().iterator();it.hasNext();) {
    		Message m = (Message) it.next();
    		if (m.getStatus().name() == "PENDING") {
    			pending.add(m);
    		}
    	}

        return pending;
    }
    
    /*
     * send message and use exceptions whether either the recipient or the sender is not found
     * */
    public Message sendMessage(String to, String subject, String message) throws CompetitionException {  
    	
    	if (!this.platform.equals(to) || (to == null)) {
    		throw new CompetitionException(CompetitionException.RECIPIENT_NOT_FOUND);
    	}
    	
    		
    	if (((User.this).platform.login((User.this).username, (User.this).password)==null) ||
    			((User.this)==null)){
    		throw new CompetitionException(CompetitionException.SENDER_NOT_FOUND);
    	}
    	
        Message m = this.platform.sendMessage((User.this), to, subject, message);
    	return m;
    }
        
    public List<Competition> myCompetitions() {
        return null;
    }

    public List<Message> getInbox() {        
        return this.inbox;
    }

    public List<Message> getOutbox() { 
        return this.outbox;
    }    
    
    public Platform getPlatform() {        
        return this.platform;
    }
    
    public void onNewEvaluation() {
        
    }
    public void onCompetitionClosed() {
        
    }
}
