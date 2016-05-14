/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wordgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import twitter4j.FilterQuery;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Kevin
 */
public class WordGame {
    
    public static Twitter t;
    
    public static String dir = "C:/WordGame/";    
    /**
     * @param args $directory $oauth
     */
    public static void main(String[] args) throws IOException {
        String path = "";
        if (args.length >= 1) {
            dir = args[0];
        }
        if (args.length >= 2) {
            path = dir + args[1];
        }
        initGame(path);
    }
    
    public static void initGame (String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file));
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(s.nextLine())
                .setOAuthConsumerSecret(s.nextLine())
                .setOAuthAccessToken(s.nextLine())
                .setOAuthAccessTokenSecret(s.nextLine());
        TwitterFactory tf = new TwitterFactory(cb.build());
        t = tf.getInstance();
        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(t.getConfiguration());
        TwitterStream twitterStream = twitterStreamFactory.getInstance();
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.follow(new long[]{3939785352L});
        twitterStream.addListener(new MentionListener());
        twitterStream.filter(filterQuery);
    }
    
}
