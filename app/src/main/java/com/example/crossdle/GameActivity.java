package com.example.crossdle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class GameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        BoardFragment.displayFragment(this, R.id.game_fragmentView_board, new Board(
            ' ', 'T', ' ', ' ', ' ', ' ',
            ' ', 'E', ' ', 'S', ' ', ' ',
            ' ', 'S', 'E', 'N', 'D', ' ',
            ' ', 'T', ' ', 'A', ' ', ' ',
            ' ', ' ', ' ', 'K', ' ', ' ',
            ' ', ' ', ' ', 'E', ' ', ' '
        ));
    }
}