package edu.uoc.dpoo;

public class CompetitionException extends Exception {
    // Predefined errors
    public static final String SENDER_NOT_FOUND = "Invalid sender";
    public static final String RECIPIENT_NOT_FOUND = "Invalid recipient";
    
    public static final String FORBIDEN_COMPETITION_OP = "Operation not allowd for user %s on competition %s";
    public static final String SUB_CLOSED_COMPETITION = "Cannot make submissions to a closed competition";
    /* 
    
    - How to throw with a custom message:    
    throw new CompetitionException("this is a custom message");
    
    - How to throw with a pre-defined message:    
    -- Without parameters
    throw new CompetitionException(CompetitionException.USER_NOT_FOUND);
    
    -- With parameters 
    throw new CompetitionException(String.format(CompetitionException.FORBIDEN_COMPETITION_OP, username, comp_title));
    
    */
    public CompetitionException (String message){
        super(message);
    }
}
