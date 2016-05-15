/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wordgame;

import java.util.Arrays;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

/**
 *
 * @author Kevin
 */
class MentionListener implements StatusListener {

    @Override
    public void onStatus(Status status) {
        String userName = status.getUser().getScreenName();
        long id = status.getUser().getId();
        Player player = new Player(id, userName, 0);
        if (!WordGame.playerIDs.contains(player.getUserID())){ // check if we already have records of the player in our database
            WordGame.players.add(player); //if not, add our player and add their index to the database
            WordGame.playerIDs.add(player.getUserID());
        }
        System.out.println(userName + ": " + status.getText()); //print what the user tweeted us
        String[] inp = status.getText().split(" "); //split the text into the user's tag and their word
        try {
            if (status.getUser().getId() == 731852008030916608L) { //Is it our tweet?
                    return; //exit, as we do not want to be parsing our own tweets!
            }
            if (inp[1].equals("score")) {
                int index = WordGame.playerIDs.indexOf(player.getUserID()); //find index of user's ID
                String tweet = "@" + userName + "'s score is: " + WordGame.players.get(index).getScore() + "!";
                return;
            }
            String word = inp[1].toLowerCase(); //normalize the word
            if (!WordGame.possibleWords.contains(word)) { //word was not found
                String tweet = "@" + userName + " your word was invalid or already found! Please try again!";
                WordGame.announce(tweet);
                return;
            }
            
            //their word was valid and wasn't already found
            int index = WordGame.playerIDs.indexOf(player.getUserID()); //find index of user's ID
            WordGame.players.get(index).setScore(WordGame.players.get(index).getScore()+word.length()); //increase the user's score
            String tweet = "@" + userName + " found " + word.toUpperCase() + "! They have earned " + word.length() + " points, bringing them to a total of " + WordGame.players.get(index).getScore() + " points!";
            WordGame.possibleWords.remove(word);
            WordGame.announce(tweet);
        } catch (TwitterException e) {
            
        }
    }
    
    
    @Override
    public void onDeletionNotice(StatusDeletionNotice sdn) {
        System.out.println(sdn.getUserId() + "'s status deleted!");
    }

    @Override
    public void onTrackLimitationNotice(int i) {

    }

    @Override
    public void onScrubGeo(long l, long l1) {

    }

    @Override
    public void onStallWarning(StallWarning sw) {
        System.out.println("Stall warning encountered!");
    }

    @Override
    public void onException(Exception excptn) {
        System.out.println(Arrays.toString(excptn.getStackTrace()));
    }
}
