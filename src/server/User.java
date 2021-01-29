package server;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String password;
    private int score;

    public User(String username, String password) {
        this.password = password;
        this.username = username;
        score = 0;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getScore() {
        return score;
    }
}
