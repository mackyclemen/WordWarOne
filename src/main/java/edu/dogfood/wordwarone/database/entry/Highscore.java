package edu.dogfood.database.entry;

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

    private int id;
    private String name;
    private Date date;
    private int score;

    public Highscore(int id, String name, Date date, int score) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.score = score;
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
