package server;

import managers.GameManager;
import managers.GamePlayer;
import java.io.Serializable;
import java.util.*;
import java.text.SimpleDateFormat;

public class User implements Serializable {

    private String username;
    private String password;
    private int score;
    private int wins;
    private int losses;
    private final TreeMap<String, GamePlayer> savedGames;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
        score = 0;
        wins = 0;
        losses = 0;
        savedGames = new TreeMap<>();
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public void update(String newUsername, String newPassword, int wins, int losses, int score) {
        username = newUsername;
        if(!newPassword.equals(""))
            password = newPassword;
        this.wins = wins;
        this.losses = losses;
        this.score = score;
    }

    public boolean saveGame(GamePlayer gamePlayer) {
        String dateAndTime = new SimpleDateFormat("   dd.MM.yyyy   HH:mm:ss").format(new Date());
        savedGames.put(dateAndTime, gamePlayer);
        return true;
    }

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
