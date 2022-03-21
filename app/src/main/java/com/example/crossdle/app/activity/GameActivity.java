package com.example.crossdle.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.example.crossdle.app.fragment.BoardFragment;
import com.example.crossdle.R;
import com.example.crossdle.app.fragment.KeyboardFragment;
import com.example.crossdle.game.Board;
import com.example.crossdle.game.BoardView;

public class GameActivity extends AppCompatActivity {
    private Board board;
    private BoardFragment boardFragment;
    private KeyboardFragment keyboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        BoardView view = new BoardView();

        board = new Board(view,
            ' ', 'T', ' ', ' ', ' ', ' ',
            ' ', 'E', ' ', 'S', ' ', ' ',
            ' ', 'S', 'E', 'N', 'D', ' ',
            ' ', 'T', ' ', 'A', ' ', ' ',
            ' ', ' ', ' ', 'K', ' ', ' ',
            ' ', ' ', ' ', 'E', ' ', ' '
        );

        keyboardFragment = KeyboardFragment.frame(this, R.id.game_fragmentView_keyboard, board);
        boardFragment = BoardFragment.frame(this, R.id.game_fragmentView_board, board);

        view.setViewHandler(boardFragment::getView);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.isCanceled()) {
            board.clickBack();
        } else if (event.getAction() == KeyEvent.ACTION_DOWN) {
            char character = (char)event.getUnicodeChar(event.getMetaState());
            if (Character.isLetter(character)) {
                board.clickKey((Character.toUpperCase(character)));
            }
        }

        return super.dispatchKeyEvent(event);
    }
}