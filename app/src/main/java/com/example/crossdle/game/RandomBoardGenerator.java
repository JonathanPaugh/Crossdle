package com.example.crossdle.game;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

public class RandomBoardGenerator{

    public static final char EMPTY = ' ';
    public static final int FOUR_LETTER_WORD_BASE_SIZE = 448;
    public static final int FIVE_LETTER_WORD_BASE_SIZE = 2073;
    public static char[][] Board;

    static char[][] generateBoard(){
        final int ROWS = 6;
        final int COLS = 6;

        char[][] board = new char [ROWS][COLS];

        for (int i = 0; i < ROWS; i++)
        {
            for (int j = 0; j < COLS; j++)
            {
                board[i][j] = EMPTY;
            }
        }
        return board;
    }

    static void printBoard(char[][] board){

        for (char[] chars : board) {
            System.out.println();
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(chars[j] + " ");
            }
        }

    }

    public static void placeWordHorizontal(char[][] board, String word, int row, int column){
        for (int i = 0; i < word.length(); i++)
        {
            board[row][column] = word.charAt(i);
            column++;
        }
    }
    public static void placeWordVertical(char[][] board, String word, int row, int column){

        for (int i = 0; i < word.length(); i++)
        {
            board[row][column] = word.charAt(i);
            row++;
        }
    }

    public static void placeFirstWord(char[][] board, String word){
        Random rand = new Random();
        int upperbound = 2;
        int orientation = rand.nextInt(upperbound);
        int position = rand.nextInt(upperbound);
        if(orientation == 1){
            placeWordHorizontal(board, word, 0, position);
        }else{
            placeWordVertical(board, word, position, 0);
        }

    }

    public static boolean placeSecondWord(char[][] board, String word){
        if(board[0][2]!=EMPTY){
            for(int i = 0; i<board.length; i++){
                if(board[0][i]==word.charAt(0)){
                    placeWordVertical(board, word, 0, i);
                    return true;
                }
            }
        }else{
            for(int i = 0; i<board.length; i++){
                if(board[i][0]==word.charAt(0)){
                    placeWordHorizontal(board, word, i, 0);
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkHorizontalNeighbours(char[][] board,int row,int column, int length){
        for (int i = 1; i <=length; i++){
            if(board[row][column+i]!=EMPTY){
                return false;
            }
            if(row+1<6){
                if(board[row+1][column+i]!=EMPTY){
                    return false;
                }
            }
            if(row-1>-1){
                if(board[row-1][column+i]!=EMPTY){
                    return false;
                }
            }

        }
        if(column+length+1<6){
            if(board[row][column+length+1]!=EMPTY){
                return false;
            }
        }
        if(column-1>-1){
            return board[row][column - 1] == EMPTY;
        }
        return true;
    }

    public static boolean checkVerticalNeighbours(char[][] board,int row,int column, int length){
        for (int i = 1; i <=length; i++){
            if(board[row+i][column]!=EMPTY){
                return false;
            }
            if(column+1<6){
                if(board[row+i][column+1]!=EMPTY){
                    return false;
                }
            }
            if(column-1>-1){
                if(board[row+i][column-1]!=EMPTY){
                    return false;
                }
            }
        }
        if(row+length+1<6){
            if(board[row+length+1][column]!=EMPTY){
                return false;
            }
        }
        if(row-1>-1){
            return board[row - 1][column] == EMPTY;
        }
        return true;
    }

    public static boolean placeRandomWord(char[][] board, String word){
        for (int row = 0; row < board.length; row++)
        {
            for (int column = 0; column < board[0].length; column++)
            {
                if(word.indexOf(board[row][column])!=-1){

                    if(canWordBePlaced(board, word, row, column, word.indexOf(board[row][column]))==("horizontal")){
                        placeWordHorizontal(board, word, row, column- word.indexOf(board[row][column]));
                        return true;
                    }
                    else if(canWordBePlaced(board, word, row, column, word.indexOf(board[row][column]))==("vertical")){
                        placeWordVertical(board, word, row- word.indexOf(board[row][column]), column);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String canWordBePlaced(char[][] board, String word, int row, int column, int intersectionIndex){
        int roomSecondHalf = word.length() -  intersectionIndex-1;
        int roomFirstHalf = word.length() - roomSecondHalf-1;


        if(column+roomSecondHalf<6 && column-roomFirstHalf>-1){
            if(checkHorizontalNeighbours(board, row, column, roomSecondHalf)){
                if(checkHorizontalNeighbours(board, row, column-roomFirstHalf, roomFirstHalf)){
                    return("horizontal");
                }
            }

        }
        if(row+roomSecondHalf<6 && row-roomFirstHalf>-1){
            if(checkVerticalNeighbours(board, row, column, roomSecondHalf)){
                if(checkVerticalNeighbours(board, row-roomFirstHalf, column, roomFirstHalf)){
                    return("vertical");
                }
            }


        }
        return null;
    }


    public static boolean generateCrossword(){
        char[][] board = generateBoard();
        String firstWord = WordBase.getFiveLetterRandom();
        placeFirstWord(board, firstWord);
        System.out.println(firstWord);
        int count = 0;
        while(true){
            String secondWord = WordBase.getFiveLetterRandom();
            if(placeSecondWord(board,secondWord)){
                System.out.println(secondWord);
                break;
            }
        }

        while(true){
            String thirdWord = WordBase.getFourLetterRandom();
            if(placeRandomWord(board, thirdWord)){
                System.out.println(thirdWord);
                break;
            }
        }

        while(count<100){
            count++;
            String fourthWord = WordBase.getFourLetterRandom();
            if(placeRandomWord(board,fourthWord)){
                System.out.println(fourthWord);
                printBoard(board);
                Board = board;
                return true;
            }
        }

        return false;
    }

    public static char[][] returnBoard() throws IOException {
        while(true){
            if(generateCrossword()){
                break;
            }
        }
        printBoard(Board);
        return Board;
    }

}
