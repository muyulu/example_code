package edu.uoc.dpoo;

public class TestUser extends User {    
    public int numEvals = 0;
    public int numClosed = 0;
    
    public TestUser (User obj) {
        super(obj);
    }
    public void onNewEvaluation() {
        numEvals++;
    }
    public void onCompetitionClosed() {
        numClosed++;
    }
}