package edu.uoc.dpoo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Platform {
    private List<User> users;
    private List<Competition> competitions;
    
    private Integer numCompetitions  = 0;
       
    public Platform() {
        /**
         * PR1 Ex 2.1: We need to initialize the list of users
        */
        users = new ArrayList<User>();
        competitions = new ArrayList<Competition>();
    }
    
    private User findUser(String username) { 
        /**
         * PR1 Ex 2.1: Implementation of method findUser to find a user in the list users by username
        */
        User user = null;
        
        Iterator<User> itr = this.users.iterator();      
        while(itr.hasNext() && user == null) {
            User u = itr.next();
            if(u.getUserName().equals(username)) {
                user = u;
            }
        }
        
        return user;
    }
        
    
    
    public User registerUser(String username, String password, String fullname) {        
        /**
         * PR1 Ex 2.1: Register a new user, checking that it does not exist 
        */
        User newUser = null;
        
        // Check if the user is new or already exists
        User queryUser = findUser(username);
        
        if(queryUser==null) {
            newUser = new User(this, username, password, fullname);
            this.users.add(newUser);
        }
        
        return newUser;
    }

    public User login(String username, String password) {    
        /**
         * PR1 Ex 2.2: Login an already existing user
        */
        User user = null;
        
        // Find the user in the list of registered users
        User queryUser = findUser(username);        
        
        // If the user exists, check the password
        if(queryUser!=null && queryUser.checkPassword(password)) {
            user = queryUser;
        }
        
        return user;        
    }    
        
    public Integer getNumUsers() {
        /**
         * PR1 Ex 2.1: Required for test, to check if a new user is registered 
        */
        return this.users.size();
    }
    
    public Integer getNumCompetitions() {       
    	
        return this.numCompetitions;
    }
    
    public void incNumCompetitions(){
    	this.numCompetitions++;
    }
    
    /*
     * Send message from one user to another
     * */
    public Message sendMessage(User from, String to, String subject, String message) throws CompetitionException {  
    	
    	User user2= findUser(to);
    	Message msg = new Message(from, user2, subject, message);
    	from.getOutbox().add(msg);
    	user2.getInbox().add(msg);
        return msg;
    }
    
    /*
     * Register the copetition by adding it to the list
     * */
    public void registerCompetition(Competition competition) {
    	
    	competitions.add(competition);
    	
    }    
    
    /*
     * get open competitions by iterating the values in the list "competitions2" which
     * keeps the value of those competitions in "competitions" which are open
     * */
    public List<Competition> getOpenCompetitions() {        
    	
    	List<Competition> competitions2 = new ArrayList<Competition>();
    	
    	Iterator<Competition> itr = this.competitions.iterator();  
    	
    	while (itr.hasNext()) {
    		
    		Competition c = itr.next();
    		 		
    		if (c.isOpen()){
    			competitions2.add(c);
    		}
    		
    	}
    	
        return competitions2;
    }
    
    /*
     * evaluate all the competitions by calling the function with the same name in competitions
     * */
    private void evaluateAll() {
          
    	Iterator<Competition> it = competitions.iterator();
    	
    	while(it.hasNext()) {
    		it.next().evaluateAll();
    	}
    	
    }
    
    /*
     * run the evaluations
     * */
    public void run() {
        // Simulates system call for evaluation
        evaluateAll();
    }
  
    /*
     * equals method
     * */
public boolean equals(Object obj) {  
    	
    	if(obj==null) {
            return false;
        }       
    	User u = null;
    	u = this.findUser((String) obj);
    	
    	if (u == null){
    		return false;
    	}else{
    		return true;
    	}
    	
    	
    	
    }

}
