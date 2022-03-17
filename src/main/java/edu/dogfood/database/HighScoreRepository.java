package edu.dogfood.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import edu.dogfood.database.entry.Highscore;


// Singleton class only
public class HighScoreRepository implements AutoCloseable {
    private Connection connection = null;

    public static HighScoreRepository INSTANCE = null;
    
    // Initialize logger
    private static Logger logger = Logger.getLogger(HighScoreRepository.class.getName());

    public static HighScoreRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HighScoreRepository();
        }
        return INSTANCE;
    }

    // WARNING: This is created without upgradability in mind. If you are planning
    // to rewrite this, here's the database schema:
    // id: Integer, Not Null, Primary Key (Auto-incrementing)
    //     - This is the primary key of the table. Identifies a specific high score entry.
    // name: String, Not Null
    //     - This stores the player name of the high score.
    // date: Integer, Not Null
    //     - This stores the date of the high score, in a Long. This would be converted to
    //       a Date object in code.
    // score: Integer, Not Null
    //     - This stores the score of the entry.

    private HighScoreRepository() {
        logger.log(Level.INFO, "Initializing Database...");
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:highscores.db");

            // Check if the high score table exist
            String sql = "SELECT name FROM sqlite_master WHERE type='table' AND name='high_scores'";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet set = preparedStatement.executeQuery();

            if (!set.next()) {
                // Create the table
                logger.log(Level.INFO, "Creating high_scores table");
                sql = "CREATE TABLE high_scores (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, date INTEGER NOT NULL, score INTEGER NOT NULL)";
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

    public List<Highscore> getHighScores() {
        List<Highscore> highScores = new ArrayList<Highscore>();
        try {
            String sql = "SELECT * FROM high_scores";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet set = preparedStatement.executeQuery();

            // Iterate through the results
            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                Date date = new Date(set.getLong("date"));
                int score = set.getInt("score");
                Highscore highscore = new Highscore(id, name, date, score);
                highScores.add(highscore);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }
        
        return highScores;
    }

    public Highscore getSavedGameFromId(int id) {
        Highscore highscore = null;

        // Prepare a statement to get the high score from the id
        String sql = "SELECT * FROM high_scores WHERE id = ?";
        try {
            // Execute the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();

            // Iterate through the results - if there are none, return null
            if (set.next()) {
                highscore = new Highscore(set.getInt("id"), set.getString("name"), new Date(set.getLong("date")), set.getInt("score"));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return highscore;
    }

    public boolean addHighscore(String name, Date date, int score) {
        boolean result = false;

        // Prepare a statement to add the high score
        String sql = "INSERT INTO high_scores (name, date, score) VALUES (?, ?, ?)";
        try {
            // Execute the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, date.getTime());
            preparedStatement.setInt(3, score);
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    public boolean removeHighscore(int id) {
        boolean result = false;

        // Prepare a statement to remove the high score
        String sql = "DELETE FROM high_scores WHERE id = ?";
        try {
            // Execute the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    @Override
    public void close() throws Exception {
        if(INSTANCE != null) {
            connection.close();
            INSTANCE = null;
        } 
    }
}
