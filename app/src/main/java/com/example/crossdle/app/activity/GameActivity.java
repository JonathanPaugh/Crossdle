package com.example.crossdle.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;

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

        board = new Board(view, Board.TEST_LAYOUT);

        keyboardFragment = KeyboardFragment.frame(getSupportFragmentManager(), R.id.game_fragmentView_keyboard, board);
        boardFragment = BoardFragment.frame(getSupportFragmentManager(), R.id.game_fragmentView_board, board);

        view.setViewHandler(boardFragment::getView);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
         if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                board.clickBack();
            } else {
                char character = (char)event.getUnicodeChar(event.getMetaState());
                if (Character.isLetter(character)) {
                    board.clickKey((Character.toUpperCase(character)));
                }
            }
        }

        return super.dispatchKeyEvent(event);
    }
}