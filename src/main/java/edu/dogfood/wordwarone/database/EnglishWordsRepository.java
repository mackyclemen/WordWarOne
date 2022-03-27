package edu.dogfood.wordwarone.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class EnglishWordsRepository {
    // Prepare logger
    private static Logger logger = Logger.getLogger(EnglishWordsRepository.class.getName());

    // Constants declaration
    final List<String> words = new ArrayList<>();
    final List<String> common = new ArrayList<>();

    // Variables declaration
    private static EnglishWordsRepository INSTANCE;

    // Singleton: Return Instance of EnglishWordsRepository if it exists, otherwise create new instance
    public static EnglishWordsRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnglishWordsRepository();
        }
        return INSTANCE;
    }

    // Constructor
    private EnglishWordsRepository() {
        // Log message
        logger.log(Level.INFO, "Loading EnglishWordsRepository");

        BufferedReader reader = null;

        // Attempt to read file words_alpha.txt from resources
        try {
            // Log message
            logger.log(Level.INFO, "Reading words_alpha.txt");

            // Read file
            try {
                // get resource as stream
                InputStream stream = getClass().getClassLoader().getResourceAsStream("words_alpha.txt");
                reader = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                // Log error
                logger.log(Level.SEVERE, "Error reading words_alpha.txt");
            }

            // Split file into words
            // Read each line
            String line;
            while((line = reader.readLine()) != null) {
                words.add(line);
            }
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        } finally {
            try {
                if(reader != null) reader.close();
            } catch(IOException ex) {
                logger.log(Level.WARNING, "IOException thrown", ex);
            }
        }

        // Attempt to read final.txt from resources
        try {
            // Log message
            logger.log(Level.INFO, "Reading final.txt");

            // Read file
            try {
                // get resource as stream
                InputStream stream = getClass().getClassLoader().getResourceAsStream("final.txt");
                reader = new BufferedReader(new InputStreamReader(stream));
            } catch (Exception e) {
                // Log error
                logger.log(Level.SEVERE, "Error reading final.txt");
            }

            // Read each line
            String line;
            while((line = reader.readLine()) != null) {
                common.add(line);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(reader != null) reader.close();
            } catch(IOException ex) {
                logger.log(Level.WARNING, "IOException thrown", ex);
            }
        }

        logger.log(Level.INFO, "EnglishWordsRepository loaded");
    }

    public String getCommonWord(int pos) {
        return common.get(pos);
    }

    public int getCommonWordSize() {
        return common.size();
    }

    public List<String> getWordsContainingLettersFrom(String string) {
        List<String> result = new ArrayList<>();

        // Iterate through all words
        for(String word : words) {
            // Check if word contains all letters from string using regex
            if(Pattern.matches("^[" + string.toLowerCase() + "]+$", word.toLowerCase())) {
                result.add(word);
            }
        }

        return result;
    }
    
    public List<String> getCommonWordsContainingLettersFrom(String string) {
        List<String> result = new ArrayList<>();

        // Iterate through all words
        for(String word : common) {
            // Check if word contains all letters from string using regex
            if(Pattern.matches("^[" + string.toLowerCase() + "]+$", word.toLowerCase())) {
                result.add(word);
            }
        }

        return result;
    }

    public String getWord(int pos) {
        return words.get(pos);
    }

    public int getWordsSize() {
        return words.size();
    }

    // This function calculates the score from the word.
    // This uses the scrabble scoring system.
    public static int getScore(String word) {
        int score = 0;

        for(String c : word.split("")) {
            switch(c) {
                case "a":
                case "e":
                case "i":
                case "o":
                case "u":
                case "l":
                case "n":
                case "r":
                case "s":
                case "t":
                    score += 1;
                    break;
                case "d":
                case "g":
                    score += 2;
                    break;
                case "b":
                case "c":
                case "m":
                case "p":
                    score += 3;
                    break;
                case "f":
                case "h":
                case "v":
                case "w":
                case "y":
                    score += 4;
                    break;
                case "k":
                    score += 5;
                    break;
                case "j":
                case "x":
                    score += 8;
                    break;
                case "q":
                case "z":
                    score += 10;
                    break;
            }
        }

        return score;
    }
}
