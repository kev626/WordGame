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

/**
 *
 * @author Kevin
 */
class MentionListener implements StatusListener {

    @Override
    public void onStatus(Status status) {
        
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
