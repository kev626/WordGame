/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package wordgame;

import java.io.FileNotFoundException;
import java.io.IOException;
import twitter4j.Twitter;

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
        
    }
    
}
