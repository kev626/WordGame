package wordgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Kevin
 */
public class Player {
    private long userID = 0L;
    private String userName = "";
    private int score = 0;
    
    public void setUserID(long _userID) {
        userID = _userID;
    }
    public void setUserName(String _userName) {
        userName = _userName;
    }
    public void setScore(int _score) {
        score = _score;
    }
    
    public long getUserID() {
        return userID;
    }
    public String getUserName() {
        return userName;
    }
    public int getScore() {
        return score;
    }
}
