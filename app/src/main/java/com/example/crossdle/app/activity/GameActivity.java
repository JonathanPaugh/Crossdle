package com.example.crossdle.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crossdle.app.fragment.BoardFragment;
import com.example.crossdle.R;
import com.example.crossdle.game.Board;

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