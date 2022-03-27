package com.example.crossdle.game;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

public class Cell
{
    public enum State
    {
        HIDDEN,
        WRONG,
        HINT,
        CORRECT;

        public int getColor() {
            switch (this) {
                default: return Cell.COLOR_HIDDEN;
                case WRONG: return Cell.COLOR_WRONG;
                case HINT: return Cell.COLOR_HINT;
                case CORRECT: return Cell.COLOR_CORRECT;
            }
        }
    }

    private static final int COLOR_EMPTY = Color.BLACK;
    private static final int COLOR_HIDDEN = Color.WHITE;
    private static final int COLOR_WRONG = Color.rgb(120, 124, 126);
    private static final int COLOR_HINT = Color.rgb(201, 180, 88);
    private static final int COLOR_CORRECT = Color.rgb(106, 170, 100);
    private static final int COLOR_SELECTED = Color.BLUE;
    private static final int COLOR_ACTIVE = Color.MAGENTA;

    private static final int SELECTED_STROKE = 6;
    private static final int SELECTED_RADIUS = 3;

    private final int x;
    private final int y;
    private final char data;

    private char value;
    private boolean selected;
    private boolean active;

    private Neighbours neighbours;

    public Cell(char data, int x, int y) {
        this.data = data;
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public char getData() { return data; }

    public void setValue(char value) {
        this.value = value;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isCorrect() {
        return this.value == data;
    }

    public State getState() {
        if (this.value == data) {
            return State.CORRECT;
        } else if (wordsContainIncorrect(this.value)) {
            return State.HINT;
        } else if (this.value != Character.MIN_VALUE) {
            return State.WRONG;
        } else {
            return State.HIDDEN;
        }
    }

    public void setNeighbours(Cell up, Cell right, Cell down, Cell left) {
        this.neighbours = new Neighbours(up, right, down, left);
    }

    public Cell getNeighbour(Word.Orientation orientation, boolean next) {
        return neighbours.get(orientation, next);
    }

    public Word getWord(Word.Orientation orientation) {
        Cell cell = this;
        while (!cell.isHead(orientation)) {
            cell = cell.getNeighbour(orientation, false);
        }

        return new Word(cell, orientation);
    }

    private boolean wordsContainIncorrect(char character) {
        return getWord(Word.Orientation.Horizontal).containsIncorrect(character)
               || getWord(Word.Orientation.Vertical).containsIncorrect(character);
    }

    public boolean isSet() {
        return data != Character.MIN_VALUE && !Character.isSpaceChar(data);
    }

    private boolean isHead(Word.Orientation orientation) {
        Cell neighbour = neighbours.get(orientation, false);
        return neighbour == null || !neighbour.isSet();
    }

    public void draw(TextView view) {
        if (isSet()) {
            view.setText(String.valueOf(value));
            State state = getState();
            if (selected) {
                GradientDrawable background = new GradientDrawable();
                background.setCornerRadius(SELECTED_RADIUS);
                background.setColor(state.getColor());
                if (active) {
                    background.setStroke(SELECTED_STROKE, COLOR_ACTIVE);
                } else {
                    background.setStroke(SELECTED_STROKE, COLOR_SELECTED);
                }
                view.setBackground(background);
            } else {
                view.setBackgroundColor(state.getColor());
            }
        } else {
            view.setBackgroundColor(COLOR_EMPTY);
        }
    }

    private class Neighbours
    {
        public final Cell up;
        public final Cell right;
        public final Cell down;
        public final Cell left;

        public Neighbours(Cell up, Cell right, Cell down, Cell left) {
            this.up = up;
            this.right = right;
            this.down = down;
            this.left = left;
        }

        public Cell get(Word.Orientation orientation, boolean next) {
            switch (orientation) {
                default: return next ? right : left;
                case Vertical: return next ? down : up;
            }
        }
    }
}
