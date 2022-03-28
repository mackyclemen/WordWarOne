package edu.dogfood.wordwarone.interfaces;

import edu.dogfood.wordwarone.database.entry.Highscore;
import edu.dogfood.wordwarone.database.entry.SavedGame;

public interface GameHandoffListener {
    public void onSave(SavedGame game);
    public void onForfeit(Highscore highscore);
}
