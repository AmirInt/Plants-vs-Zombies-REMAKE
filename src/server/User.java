package server;

import managers.GamePlayer;
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;

/**
 * Class User represents a user account and keeps its ratings and
 * saved games
 */
public class User implements Serializable {

    private String username;
    private String password;
    private int score;
    private int wins;
    private int losses;
    private final TreeMap<String, GamePlayer> savedGames;

    /**
     * Instantiates this class
     * @param username The account's username
     * @param password The account's password
     */
    public User(String username, String password) {
        this.password = password;
        this.username = username;
        score = 0;
        wins = 0;
        losses = 0;
        savedGames = new TreeMap<>();
    }

    /**
     * @return This user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return This user's score
     */
    public int getScore() {
        return score;
    }

    /**
     * @return This user's total wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * @return This user's total losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Updates this user's status
     * @param newUsername The new username
     * @param newPassword The new password
     * @param wins The updated wins
     * @param losses The updated losses
     * @param score The updated score
     */
    public void update(String newUsername, String newPassword, int wins, int losses, int score) {
        username = newUsername;
        if(!newPassword.equals(""))
            password = newPassword;
        this.wins = wins;
        this.losses = losses;
        this.score = score;
    }

    /**
     * Saves a game onto this account
     * @param gamePlayer The game to be saved
     * @return True if the operation is done properly
     */
    public boolean saveGame(GamePlayer gamePlayer) {
        String dateAndTime = new SimpleDateFormat("   dd.MM.yyyy   HH:mm:ss").format(new Date());
        savedGames.put(dateAndTime, gamePlayer);
        return true;
    }

    /**
     * @return The Array of the dates of the saved games
     */
    public String[] getSavedGames() {
        String[] savedGamesArray = new String[savedGames.size()];
        int i = 0;
        for (String date:
             savedGames.keySet()) {
            savedGamesArray[i] = date;
            ++i;
        }
        return savedGamesArray;
    }

    /**
     * @param date The date of the saved game
     * @return The saved game
     */
    public GamePlayer getSavedGame(String date) {
        System.out.println(date);
        GamePlayer game = savedGames.get(date);
        savedGames.remove(date, game);
        return game;
    }

    @Override
    public String toString() {
        return "        " + username + "    wins: " + wins + "    losses: " + losses + "    score:" + score;
    }
}
