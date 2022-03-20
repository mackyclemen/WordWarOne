package edu.dogfood.wordwarone.database;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import edu.dogfood.wordwarone.database.entry.SavedGame;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// Singleton class only
public class SavedGameRepository implements AutoCloseable {
    private Connection connection = null;

    public static SavedGameRepository INSTANCE = null;
    
    // Initialize logger
    private static Logger logger = Logger.getLogger(SavedGameRepository.class.getName());

    public static SavedGameRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SavedGameRepository();
        }
        return INSTANCE;
    }

    // WARNING: This is created without upgradability in mind. If you are planning
    // to rewrite this, here's the database schema:
    // ID: Integer PK NOT NULL
    //     - This is the primary key
    // Name: String NOT NULL
    //     - This is the player name of the saved game
    // Date: Integer NOT NULL
    //     - This is the date the game was saved, by the number of milliseconds
    // Health: Integer NOT NULL
    //     - This is the health of the player
    // Difficulty: Integer NOT NULL
    //     - This is the difficulty of the current game
    // Round: Integer NOT NULL
    //     - This is the round of the game
    // Score: Integer NOT NULL
    //     - This is the score of the player
    // Seed: Long NOT NULL
    //     - This is the seed used to generate the game
    // BaseWord: String NOT NULL
    //     - This is the base word of the game. For verification purposes only.
    // SubmittedWords: String NOT NULL
    //     - This contains all submitted words. JSON format.

    private SavedGameRepository() {
        logger.log(Level.INFO, "Initializing Database...");
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:saved_games.db");

            // Check if the saved game table exist
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='saved_games'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet set = preparedStatement.executeQuery();

            if (!set.next()) {
                // Create the table
                logger.log(Level.INFO, "Creating saved_games table");
                sql = "CREATE TABLE saved_games (id INTEGER PRIMARY KEY, name TEXT NOT NULL, date INTEGER NOT NULL, difficulty INTEGER NOT NULL, health INTEGER NOT NULL, " +
                "round INTEGER NOT NULL, score INTEGER NOT NULL, seed INTEGER NOT NULL, base_word TEXT NOT NULL, submitted_words TEXT NOT NULL)";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        } finally {
            try {
                if (INSTANCE != null) {
                    connection.close();
                    INSTANCE = null;
                }
            } catch (SQLException ex) {
                logger.log(Level.SEVERE, "There was an error closing the database", ex);
            }
        }
    }

    public boolean addSaveGame(SavedGame sg) {
        boolean result = false;

        // Get all submitted words
        Gson gson = new Gson();
        String json = gson.toJson(sg.getSubmittedWords());
        logger.log(Level.INFO, "Attempting to save...");
        
        try {
            String sql = "INSERT INTO saved_games (name, date, difficulty, health, round, score, seed, base_word, submitted_words) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sg.getPlayerName());
            preparedStatement.setLong(2, sg.getDate().getTime());
            preparedStatement.setInt(3, sg.getDifficulty());
            preparedStatement.setInt(4, sg.getHealth());
            preparedStatement.setInt(5, sg.getRound());
            preparedStatement.setInt(6, sg.getScore());
            preparedStatement.setLong(7, sg.getSeed());
            preparedStatement.setString(8, sg.getBaseWord());
            preparedStatement.setString(9, json);
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    public boolean updateSavedGame(int id, SavedGame sg) {

        boolean result = false;
        
        // Get all submitted words
        Gson gson = new Gson();
        String json = gson.toJson(sg.getSubmittedWords());

        try {
            String sql = "UPDATE saved_games SET name = ?, date = ?, difficulty = ?, health = ?, round = ?, score = ?, seed = ?, base_word = ?, submitted_words = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, sg.getPlayerName());
            preparedStatement.setLong(2, sg.getDate().getTime());
            preparedStatement.setInt(3, sg.getDifficulty());
            preparedStatement.setInt(4, sg.getHealth());
            preparedStatement.setInt(5, sg.getRound());
            preparedStatement.setInt(6, sg.getScore());
            preparedStatement.setLong(7, sg.getSeed());
            preparedStatement.setString(8, sg.getBaseWord());
            preparedStatement.setString(9, json);
            preparedStatement.setInt(10, id);
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    public boolean deleteSavedGame(int id) {

        boolean result = false;

        try {
            String sql = "DELETE FROM saved_games WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    public List<SavedGame> getSavedGames() {
        List<SavedGame> savedGames = new ArrayList<>();
        try {
            String sql = "SELECT * FROM saved_games ORDER BY date DESC";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet set = preparedStatement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                long date = set.getLong("date");
                int difficulty = set.getInt("difficulty");
                int health = set.getInt("health");
                int round = set.getInt("round");
                int score = set.getInt("score");
                long seed = set.getLong("seed");
                String baseWord = set.getString("base_word");
                String submittedWords = set.getString("submitted_words");

                // Convert the JSON string to a list of words
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                List<String> words = gson.fromJson(submittedWords, type);

                SavedGame sg = new SavedGame(id, name, difficulty, health, round, score, seed, baseWord, words, new Date(date));
                savedGames.add(sg);
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }
        return savedGames;
    }

    public SavedGame getSavedGameFromId(int id) {
        SavedGame sg = null;
        try {
            String sql = "SELECT * FROM saved_games WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();

            if (set.next()) {
                String name = set.getString("name");
                long date = set.getLong("date");
                int difficulty = set.getInt("difficulty");
                int health = set.getInt("health");
                int round = set.getInt("round");
                int score = set.getInt("score");
                long seed = set.getLong("seed");
                String baseWord = set.getString("base_word");
                String submittedWords = set.getString("submitted_words");

                // Convert the JSON string to a list of words
                Gson gson = new Gson();
                Type type = new TypeToken<List<String>>(){}.getType();
                List<String> words = gson.fromJson(submittedWords, type);

                sg = new SavedGame(id, name, difficulty, health, round, score, seed, baseWord, words, new Date(date));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }
        return sg;
    }

    @Override
    public void close() throws Exception {
        if(INSTANCE != null) {
            connection.close();
            INSTANCE = null;
        } 
    }
}
