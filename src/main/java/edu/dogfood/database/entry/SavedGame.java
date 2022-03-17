package edu.dogfood.database.entry;

import java.util.Date;

public class SavedGame {

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

    private int id;
    private String name;
    private Date date;
    private String gameState;

    public SavedGame(int id, String name, Date date, String gameState) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.gameState = gameState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
}
