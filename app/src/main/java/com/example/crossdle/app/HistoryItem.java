package com.example.crossdle.app;

import androidx.fragment.app.FragmentActivity;

import com.example.crossdle.game.Board;

import java.io.Serializable;
import java.time.LocalTime;

public class HistoryItem implements Serializable {
    private String gameId;
    private LocalTime time;
    private int words;
    private int attempts;
    private char[] layout;

    public HistoryItem(String gameId, LocalTime time, int words, int attempts, char[] layout) {
        this.gameId = gameId;
        this.time = time;
        this.words = words;
        this.attempts = attempts;
        this.layout = layout;
    }

    public String getGameId() { return gameId; }
    public LocalTime getTime() {
        return time;
    }
    public int getWords() {
        return words;
    }
    public int getAttempts() { return attempts; }
    public char[] getLayout() { return layout; }
}
