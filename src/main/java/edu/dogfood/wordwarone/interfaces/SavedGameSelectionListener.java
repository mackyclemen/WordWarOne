package edu.dogfood.wordwarone.interfaces;

import edu.dogfood.wordwarone.database.entry.SavedGame;

public interface SavedGameSelectionListener {
    public void onSavedGameSelected(SavedGame game);
}
