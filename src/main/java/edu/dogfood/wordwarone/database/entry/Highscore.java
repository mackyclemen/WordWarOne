package edu.dogfood.wordwarone.database.entry;

import java.util.Date;

public class Highscore {

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
    // difficulty: Integer, Not Null
    //     - This stores the difficulty of the entry.

    private String name;
    private Date date;
    private int score;
    private int difficulty;

    public Highscore(String name, Date date, int score, int difficulty) {
        this.name = name;
        this.date = date;
        this.score = score;
        this.difficulty = difficulty;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
