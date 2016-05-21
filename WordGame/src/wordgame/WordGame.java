/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wordgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import twitter4j.FilterQuery;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
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
    
    static ArrayList<String> possibleWords = new ArrayList<String>();
    static ArrayList<Player> players = new ArrayList<Player>();
    static ArrayList<Long> playerIDs = new ArrayList<Long>();
    
    public static int gameNumber;
    /**
     * @param args $directory $oauth $wordlist
     */
    public static void main(String[] args) throws Exception {
        String path = "";
        if (args.length >= 1) {
            dir = args[0];
        }
        if (args.length >= 2) {
            path = dir + args[1];
        }
        initGame(path);
        beginGame(dir + args[2]);
    }
    
    public static void initGame (String file) throws FileNotFoundException {
        Scanner s = new Scanner(new File(file)); //open the file
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true) //populate the twitter details with the proper API informatin
                .setOAuthConsumerKey(s.nextLine())
                .setOAuthConsumerSecret(s.nextLine())
                .setOAuthAccessToken(s.nextLine())
                .setOAuthAccessTokenSecret(s.nextLine());
        TwitterFactory tf = new TwitterFactory(cb.build());
        t = tf.getInstance();
        TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(t.getConfiguration());
        TwitterStream twitterStream = twitterStreamFactory.getInstance();
        FilterQuery filterQuery = new FilterQuery();
        filterQuery.follow(new long[]{731852008030916608L}); //Track our tweets
        twitterStream.addListener(new MentionListener()); //Set the listener to our MentionListener class
        twitterStream.filter(filterQuery); //begin listening
    }
    
    public static void beginGame(String wordlist) throws InterruptedException, FileNotFoundException, TwitterException {
        
        while (true) { //eternal game loop
            gameNumber = getGameCount();
            try {
                setGameCount(gameNumber + 1);
            } catch (Exception e) {  }
            gameNumber = getGameCount();
            String letters = getString();
            possibleWords = findWords(letters, wordlist); //populate our word database
            try {
                announce("Game starting! Letters: " + letters); //announce the challenge to twitter
            } catch (Exception e) {
                
            }
            Thread.sleep(3600000); //one hour
            //announce the winner of the match
            try {
                if (players.size() == 0) {
                    announce("Nobody played this match! Play the next one for a chance to win!");
                } else {
                    Player winner = getWinner();
                    announce("The winner was @" + winner.getUserName() + " with a score of " + winner.getScore() + "! Congratulations! (Game " + gameNumber + ")");
                }
            } catch (Exception e) {
                
            }
            //reset game and prepare for next game
            resetGame();
        }
    }
    
    public static String getString() {
        String alphabet= "aaabcdeeefghiiijklmnooopqrsttuuvwxyz"; //alphabet with increased odds for vowles
        String s = "";
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = alphabet.charAt(random.nextInt(36));
            s+=c;
        }
        return s;
    }
    
    public static void announce(String str) throws TwitterException {
        System.out.println(str);
        StatusUpdate rt = new StatusUpdate(str);
        WordGame.t.updateStatus(rt);
    }
    
    public static ArrayList<String> findWords(String letters, String wordListPath) throws FileNotFoundException {
        ArrayList<String> list = new ArrayList<String>();
        Scanner s = new Scanner(new File(wordListPath));
        String line = null;
        try {
            while ((line = s.nextLine().toLowerCase()) != null) {
                if (containsWord(letters, line) && line.length() >= 3) {
                    list.add(line);
                    System.out.println(line);
                }
            }
        } catch (NoSuchElementException e) {
            
        }
        return list;
    }
    static boolean containsWord(String s, String w) {
        List<Character> list = new LinkedList<Character>();
        for (char c : s.toCharArray()) {
            list.add(c);
        }
        for (Character c : w.toCharArray()) {
            if (!list.remove(c)) {
                return false;
            }
        }
        return true;
    }
    public static void resetGame() {
        players = new ArrayList<Player>(); //remove all players from database
        playerIDs = new ArrayList<Long>();
        possibleWords = new ArrayList<String>(); //remove all remaining words
    }
    
    public static Player getWinner() {
        int maxScore = 0;
        long maxID = 0;
        for (Player player : players) {
            if (player.getScore() >= maxScore) {
                maxScore = player.getScore();
                maxID = player.getUserID();
            }
	}
        int index = playerIDs.indexOf(maxID);
        return players.get(index);
    }

    public static int getGameCount() throws FileNotFoundException {
        Scanner s = new Scanner(new File(dir + "games.txt")); //open the file
        return Integer.parseInt(s.nextLine());
    }
    public static void setGameCount(int games) throws FileNotFoundException, IOException {
        PrintWriter countLogger = new PrintWriter(new FileWriter(dir + "games.txt"));
        countLogger.println(games);
        countLogger.close();
    }
}
