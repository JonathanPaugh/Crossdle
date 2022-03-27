package com.example.crossdle.game;

import android.view.View;
import android.widget.TextView;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Board implements Serializable
{
    public static final char[] TEST_LAYOUT = new char[] {
        ' ', 'T', ' ', ' ', ' ', ' ',
        ' ', 'E', ' ', 'S', ' ', ' ',
        ' ', 'S', 'E', 'N', 'D', ' ',
        ' ', 'T', ' ', 'A', ' ', ' ',
        ' ', ' ', ' ', 'K', ' ', ' ',
        ' ', ' ', ' ', 'E', ' ', ' '
    };

    private int DEFAULT_SIZE = 6;

    private final BoardView view;
    private final Cell[][] data;
    private final int size;

    private Selection selection = new Selection();

    // For Reference //
    private static final String[] COORDINATE_SYSTEM =
    {
        "[0, 0]", "[1, 0]",
        "[0, 1]", "[1, 1]"
    };

    public Board(BoardView view, int size, char... data) {
        this.view = view;
        this.size = size;
        this.data = wrapData(size, data);
    }

    public Board(BoardView view, char... data) {
        this.view = view;
        this.size = DEFAULT_SIZE;
        this.data = wrapData(size, data);
    }

    public Board(BoardView view, char[][] data) {
        this.view = view;
        this.size = data.length;
        this.data = convertData(data);
    }

    public Cell getCell(int x, int y) {
        return data[y][x];
    }

    public Selection getSelection() { return selection; }

    public void clickKey(char character) {
        if (!getSelection().isSet()) { return; }
        getSelection().next(character);
        draw();
    }

    public void clickBack() {
        if (!getSelection().isSet()) { return; }
        getSelection().prev();
        draw();
    }

    public void select(int x, int y) {
        Cell cell = getCell(x, y);

        if (cell.isSet()) {
            Word horizontalWord = cell.getWord(Word.Orientation.Horizontal);
            Word verticalWord = cell.getWord(Word.Orientation.Vertical);
            selection.update(horizontalWord.getSize() > 1 ? horizontalWord : verticalWord);
        } else {
            selection.update(null);
        }
       draw();
    }

    private Cell[][] convertData(char[][] rawData) {
        Cell[][] data = new Cell[rawData.length][rawData.length];

        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                data[y][x] = new Cell(rawData[y][x], x, y);
            }
        }

        linkCells(data);

        return data;
    }

    private Cell[][] wrapData(int size, char[] rawData) {
        Queue<Character> queue = new LinkedList();

        for (char character : rawData) {
            queue.add(character);
        }

        Cell[][] data = new Cell[size][size];
        for (int y = 0; y < data.length; y++) {
            for (int x = 0; x < data[y].length; x++) {
                if (!queue.isEmpty()) {
                    data[y][x] = new Cell(queue.poll(), x, y);
                }
            }
        }

        linkCells(data);

        return data;
    }

    private void linkCells(Cell[][] data) {
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int nextIndex = 0;
                Cell up = null;
                Cell right = null;
                Cell down = null;
                Cell left = null;

                nextIndex = y - 1;
                if (nextIndex >= 0) {
                    up = data[nextIndex][x];
                }

                nextIndex = x + 1;
                if (nextIndex < size) {
                    right = data[y][nextIndex];
                }

                nextIndex = y + 1;
                if (nextIndex < size) {
                    down = data[nextIndex][x];
                }

                nextIndex = x - 1;
                if (nextIndex >= 0) {
                    left = data[y][nextIndex];
                }

                data[y][x].setNeighbours(up, right, down, left);
            }
        }
    }

    public void draw() {
        if (view == null) { return; }
        view.draw(data);
    }
}
