package com.example.crossdle.app;

import androidx.fragment.app.FragmentActivity;

import com.example.crossdle.game.Board;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

public class HistoryItem implements Serializable {
    private String gameId;
    private int time;
    private int words;
    private int attempts;
    private char[][] layout;

    public HistoryItem(String gameId, int time, int words, int attempts, char[][] layout) {
        this.gameId = gameId;
        this.time = time;
        this.words = words;
        this.attempts = attempts;
        this.layout = layout;
    }

    public String getGameId() { return gameId; }
    public int getTime() {
        return time;
    }
    public String getTimeString() {
        return String.format("%d:%d",
            TimeUnit.MILLISECONDS.toMinutes(time),
            TimeUnit.MILLISECONDS.toSeconds(time) -
            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time))
        );
    }

    public int getWords() {
        return words;
    }
    public int getAttempts() { return attempts; }
    public char[][] getLayout() { return layout; }
}
