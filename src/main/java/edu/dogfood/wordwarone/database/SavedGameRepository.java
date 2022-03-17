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

import edu.dogfood.database.entry.SavedGame;


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
    // id: Integer, Not Null, Primary Key (Auto-incrementing)
    //     - This is the primary key of the table. Identifies a specific saved game.
    // name: String, Not Null
    //     - This stores the player name of the saved game.
    // date: Integer, Not Null
    //     - This stores the date of the saved game, in a Long. This would be converted to
    //       a Date object in code.
    // gameState: String, Not Null
    //     - This stores the game state of the saved game. JSON format.

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
                sql = "CREATE TABLE saved_games (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, date INTEGER NOT NULL, game_state TEXT NOT NULL)";
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

    public List<SavedGame> getSavedGames() {
        List<SavedGame> savedGames = new ArrayList<SavedGame>();
        try {
            String sql = "SELECT * FROM saved_games";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet set = preparedStatement.executeQuery();

            while (set.next()) {
                int id = set.getInt("id");
                String name = set.getString("name");
                Date date = new Date(set.getLong("date"));
                String gameState = set.getString("game_state");
                SavedGame savedGame = new SavedGame(id, name, date, gameState);
                savedGames.add(savedGame);
            }

        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }
        
        return savedGames;
    }

    public SavedGame getSavedGameFromId(int id) {
        SavedGame save = null;

        // Prepare a statement to get the saved game from the id
        String sql = "SELECT * FROM saved_games WHERE id = ?";
        try {
            // Execute the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet set = preparedStatement.executeQuery();

            // Iterate through the results - if there are none, return null
            if (set.next()) {
                save = new SavedGame(set.getInt("id"), set.getString("name"), new Date(set.getLong("date")), set.getString("game_state"));
            }
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return save;
    }

    public boolean addSaveGame(String name, Date date, String gameState) {
        boolean result = false;

        // Prepare a statement to add the saved game
        String sql = "INSERT INTO saved_games (name, date, game_state) VALUES (?, ?, ?)";
        try {
            // Execute the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setLong(2, date.getTime());
            preparedStatement.setString(3, gameState);
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    public boolean updateSavedGame(int id, String gameState) {
        boolean result = false;

        // Prepare a statement to update the saved game
        String sql = "UPDATE saved_games SET game_state = ?, date = ? WHERE id = ?";
        try {
            // Execute the statement
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, gameState);
            preparedStatement.setLong(2, new Date().getTime()); // gets current time
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

            result = true;
        } catch (SQLException ex) {
            logger.log(Level.SEVERE, "An SQL exception has been thrown", ex);
        }

        return result;
    }

    public boolean removeSave(int id) {
        boolean result = false;

        // Prepare a statement to remove the saved game
        String sql = "DELETE FROM saved_games WHERE id = ?";
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
