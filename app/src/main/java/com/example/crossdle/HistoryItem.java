package com.example.crossdle;

import java.time.LocalTime;

public class HistoryItem {
    private LocalTime time;
    private int words;
    private int attempts;

    public HistoryItem(LocalTime time, int words, int attempts) {
        this.time = time;
        this.words = words;
        this.attempts = attempts;
    }

    public LocalTime getTime() {
        return time;
    }

    public int getWords() {
        return words;
    }

    public int getAttempts() {
        return attempts;
    }
}
