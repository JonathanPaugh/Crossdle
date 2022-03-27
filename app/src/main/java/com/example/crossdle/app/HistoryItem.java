package com.example.crossdle.app;

import androidx.fragment.app.FragmentActivity;

import com.example.crossdle.game.Board;

import java.io.Serializable;
import java.time.LocalTime;

public class HistoryItem implements Serializable {
    private FragmentActivity activity;
    private String gameId;
    private LocalTime time;
    private int words;
    private int attempts;
    private Board board;

    public HistoryItem(FragmentActivity activity, String gameId, LocalTime time, int words, int attempts, Board board) {
        this.activity = activity;
        this.gameId = gameId;
        this.time = time;
        this.words = words;
        this.attempts = attempts;
        this.board = board;
    }

    public FragmentActivity getActivity() { return activity; }
    public String getGameId() { return gameId; }
    public LocalTime getTime() {
        return time;
    }
    public int getWords() {
        return words;
    }
    public int getAttempts() { return attempts; }
    public Board getBoard() { return board; }
}
