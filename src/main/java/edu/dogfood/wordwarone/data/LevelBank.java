package edu.dogfood.wordwarone.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.dogfood.wordwarone.Settings;
import edu.dogfood.wordwarone.Settings.DiffConfig;
import edu.dogfood.wordwarone.database.EnglishWordsRepository;

/**
 * LevelBank
 */
public class LevelBank {

    private final long seed;

    private String randomWord;
    private String randomWordShuffled;
    private List<String> commonWords = new ArrayList<>();
    private List<String> bonusWords = new ArrayList<>();

    private int diff = Settings.getInstance().getDifficulty();

    private int numberPassableWords;

    // Get instances of EnglishWordsRepository and Settings
    private EnglishWordsRepository repository = EnglishWordsRepository.getInstance();

    // Prepare logger
    Logger logger = Logger.getLogger(LevelBank.class.getName());

    // Constructor - accept a seed for the randomization, or generate a random seed

    public LevelBank(int diffOverride, long seed) {
        this.seed = seed;
        this.diff = diffOverride;
        generateWord();
    }

    public LevelBank(long seed) {
        this.seed = seed;
        generateWord();
    }

    public LevelBank(int diffOverride) {
        this.seed = System.nanoTime();
        this.diff = diffOverride;
        generateWord();
    }

    public LevelBank() {
        this.seed = System.nanoTime();
        generateWord();
    }

    private void generateWord() {
        // Create Random object
        Random random = new Random(seed);

        // Determine the number of passable words through a randomizer between 10% and
        // 50%
        // number of common words
        do {
            logger.log(Level.INFO, "Generating random number of passable words");
            
            // Get a random common word from the repo with min length of 7
            do {
                randomWord = repository.getCommonWord(random.nextInt(repository.getCommonWordSize()));
                logger.log(Level.INFO, "Random word: " + randomWord);
            } while (randomWord.length() < 8);

            // Locate all common words that contains all the letters of the randomly picked
            // randomWord
            commonWords = repository.getCommonWordsContainingLettersFrom(randomWord);

            numberPassableWords = (int) (random.nextDouble() * (commonWords.size() * 0.5) + commonWords.size() * 0.1);
            logger.log(Level.INFO, "Number rolled for passable words: " + numberPassableWords);
        } while (
            /* lower limit */
            numberPassableWords < (diff == DiffConfig.DIFFICULTY_EASY ? 20 : 40) ||

            /* upper limit */
            numberPassableWords > (diff == DiffConfig.DIFFICULTY_EASY ? 40 : 60)
        );

        // Shuffle the selected words' letters
        Set<String> word = new HashSet<>(Arrays.asList(randomWord.split("")));
        randomWordShuffled = String.join("", word);

        // Log
        logger.log(Level.INFO, "Seed: " + seed);
        logger.log(Level.INFO, "Chosen word: " + randomWord);
        logger.log(Level.INFO, "Shuffled word: " + randomWordShuffled);
        logger.log(Level.INFO, "Common words: " + commonWords.size());

        logger.log(Level.INFO, "List of common words: " + commonWords);

        // Shuffle common words and get the first numberPassableWords
        Collections.shuffle(commonWords, random);
        commonWords = commonWords.subList(0, numberPassableWords);

        // Get all words that contains all the letters of the randomly picked randomWord
        bonusWords = repository.getWordsContainingLettersFrom(randomWord);
        logger.log(Level.INFO, "Bonus words: " + bonusWords.size());

        // Subtract the common words from the bonusWords
        bonusWords.removeAll(commonWords);

        // Log
        logger.log(Level.INFO, "Number of words required to progress: " + numberPassableWords);
    }

    public long getSeed() {
        return seed;
    }

    public String getRandomWord() {
        return randomWord;
    }

    public String getRandomWordShuffled() {
        return randomWordShuffled;
    }

    public List<String> getCommonWords() {
        return commonWords;
    }

    public List<String> getBonusWords() {
        return bonusWords;
    }
}