package com.example.crossdle.game;

public class Selection
{
    private Word word;
    private Cell current;

    public Word getWord() {
        return word;
    }

    private void setCurrent(Cell cell) {
        if (current != null) {
            current.setActive(false);
        }
        current = cell;
        current.setActive(true);
    }

    public boolean isSet() {
        return word != null;
    }

    public void next(char value) {
        Cell destination = current.getNeighbour(word.getOrientation(), true);
        current.setValue(value);
        if (destination == null || !destination.isSet()) {
            return;
        }
        current = destination;
    }

    public void prev() {
        Cell destination = current.getNeighbour(word.getOrientation(), false);
        current.setValue(Character.MIN_VALUE);
        if (destination == null || !destination.isSet()) {
            return;
        }
        current = destination;
    }

    public void update(Word word) {
        selectCells(false);
        this.word = word;
        if (isSet()) {
            this.current = this.word.getStart();
        } else {
            this.current = null;
        }
        selectCells(true);
    }

    private void selectCells(boolean selected) {
        if (!isSet()) { return; }
        for (Cell cell : word.getCells()) {
            cell.setSelected(selected);
        }
    }
}
