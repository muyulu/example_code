package edu.uoc.dpoo;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Message {

    private String subject;
    private String message;
    private MessageStatus status;
    private Date createdAt;
    private User to;
    private User from;
    
    public Message (User from, User to, String subject, String message) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.createdAt = new Date();
        this.status = MessageStatus.valueOf(MessageStatus.PENDING.name());
    }
    
    /*
     * change status of message to read
     * */
    public void read() {
        this.status = MessageStatus.valueOf(MessageStatus.READ.name());
    }
    
    /*
     * get the status of the message
     * */
    public MessageStatus getStatus() {        
        return this.status;
    }
    
    /*
     * toString method. Format date to the specified manner
     * */
    public String toString() {          
    	
    	SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	String DateToStr = format.format(this.createdAt);
    	
        return "{date: "+DateToStr+", from: "+this.from.toString()+", to: "+this.to.toString()+ ", subject: "+this.subject+", status: "+this.status.name()+"}";
    }
    
    /*
     * equals method
     * */
    public boolean equals(Object obj) {  
    	
    	if(obj==null) {
            return false;
        }        
        if (obj instanceof Message) {
            Message message = (Message) obj;
            if (!this.from.equals(message.from) || !this.to.equals(message.to) || !this.subject.equals(message.subject) || !this.status.equals(message.status) || !this.createdAt.equals(message.createdAt)) {
                return false;
            }        
            return true;
        } else {
            return false;
        } 
    }
}
