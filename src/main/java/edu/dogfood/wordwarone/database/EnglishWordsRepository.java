package edu.dogfood.wordwarone.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
            File file = new File(getClass().getClassLoader().getResource("words_alpha.txt").getPath());
            // Split file into words

            // Initialize file reader
            reader = new BufferedReader(new FileReader(file)); 

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
            File file = new File(getClass().getClassLoader().getResource("final.txt").getPath());
            // Split file into words

            // Initialize file reader
            reader = new BufferedReader(new FileReader(file)); 

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
}
