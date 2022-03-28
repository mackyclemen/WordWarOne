package edu.dogfood.wordwarone.database.entry;

import java.util.Date;
import java.util.List;

public class SavedGame {

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

    private final int id;
    private String playerName;

    private boolean next = false;

    private int difficulty;
    private int health;

    private int round = 1;
    private int score = 0;
    private long seed;

    private String baseWord; // for save verification
    private List<String> submittedWords;

    private Date date;

    public SavedGame(String playerName) {
        this.id = -1;
        this.playerName = playerName;
    }

    public SavedGame(String playerName, int round, int score) {
        this.id = -2;
        this.playerName = playerName;
        this.round = round;
        this.score = score;
    }

    public SavedGame(int id, String playerName, int difficulty, int health, int round, int score, long seed, String baseWord, List<String> submittedWords, Date date) {
        this.id = id;
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.health = health;
        this.round = round;
        this.score = score;
        this.seed = seed;
        this.baseWord = baseWord;
        this.submittedWords = submittedWords;
        this.date = date;
        this.next = false;
    }
    
    public SavedGame(int id, String playerName, int difficulty, int health, int round, int score, long seed, String baseWord, List<String> submittedWords, Date date, boolean next) {
        this.id = id;
        this.playerName = playerName;
        this.difficulty = difficulty;
        this.health = health;
        this.round = round;
        this.score = score;
        this.seed = seed;
        this.baseWord = baseWord;
        this.submittedWords = submittedWords;
        this.date = date;
        this.next = next;
    }
    
    public int getId() {
        return id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getHealth() {
        return health;
    }

    public int getRound() {
        return round;
    }

    public int getScore() {
        return score;
    }

    public long getSeed() {
        return seed;
    }

    public String getBaseWord() {
        return baseWord;
    }

    public List<String> getSubmittedWords() {
        return submittedWords;
    }

    public Date getDate() {
        return date;
    }

    public boolean isNext() {
        return next;
    }

    @Override
    public String toString() {
        return "SavedGame{" + "id=" + id + ", playerName=" + playerName + ", difficulty=" + difficulty + ", health=" + health + ", round=" + round + ", score=" + score + ", seed=" + seed + ", baseWord=" + baseWord + ", submittedWords=" + submittedWords + ", date=" + date + ", next=" + next + '}';
    }
}
