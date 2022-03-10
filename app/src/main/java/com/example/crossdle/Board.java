package com.example.crossdle;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class Board implements Serializable {
    private int DEFAULT_SIZE = 6;

    private int size;
    private char[][] data;

    public Board(int size, char... data) {
        this.size = size;
        this.data = wrapData(size, data);
    }

    public Board(char... data) {
        this.size = DEFAULT_SIZE;
        this.data = wrapData(size, data);
    }

    public Board(char[][] data) {
        this.size = data.length;
        this.data = data;
    }

    public char getCell(int x, int y) {
        return data[y][x];
    }

    private char[][] wrapData(int size, char[] rawData) {
        Queue<Character> queue = new LinkedList();
        for (char character : rawData) {
            queue.add(character);
        }

        char[][] data = new char[size][size];
        for (int y = 0; y < DEFAULT_SIZE; y++) {
            for (int x = 0; x < DEFAULT_SIZE; x++) {
                if (!queue.isEmpty()){
                    data[y][x] = queue.poll();
                }
            }
        }
        return data;
    }
}
