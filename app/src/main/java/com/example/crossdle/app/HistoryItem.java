package com.example.crossdle.app;

import androidx.appcompat.app.AppCompatActivity;

import com.example.crossdle.game.Board;

import java.time.LocalTime;

public class HistoryItem {
    private AppCompatActivity context;
    private LocalTime time;
    private int words;
    private int attempts;
    private Board board;

    public HistoryItem(AppCompatActivity context, LocalTime time, int words, int attempts, Board board) {
        this.context = context;
        this.time = time;
        this.words = words;
        this.attempts = attempts;
        this.board = board;
    }

    public AppCompatActivity getContext() { return context; }
    public LocalTime getTime() {
        return time;
    }
    public int getWords() {
        return words;
    }
    public int getAttempts() { return attempts; }
    public Board getBoard() { return board; }
}
