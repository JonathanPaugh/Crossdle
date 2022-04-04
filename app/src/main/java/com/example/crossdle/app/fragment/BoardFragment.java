package com.example.crossdle.app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.crossdle.R;
import com.example.crossdle.game.Board;
import com.example.crossdle.game.Cell;
import com.example.crossdle.game.Selection;
import com.example.crossdle.game.Word;

/***
 * Represents the controller in the MVC architecture for a board. Takes the user input and sends it
 * to the model. ...more here
 */
public class BoardFragment extends Fragment {
    private static final String ARG_BOARD = "ARG_BOARD";

    private static final int[][] LAYOUT = new int[][]
    {
        { R.id.board_cell_0_0, R.id.board_cell_1_0, R.id.board_cell_2_0, R.id.board_cell_3_0, R.id.board_cell_4_0, R.id.board_cell_5_0, },
        { R.id.board_cell_0_1, R.id.board_cell_1_1, R.id.board_cell_2_1, R.id.board_cell_3_1, R.id.board_cell_4_1, R.id.board_cell_5_1, },
        { R.id.board_cell_0_2, R.id.board_cell_1_2, R.id.board_cell_2_2, R.id.board_cell_3_2, R.id.board_cell_4_2, R.id.board_cell_5_2, },
        { R.id.board_cell_0_3, R.id.board_cell_1_3, R.id.board_cell_2_3, R.id.board_cell_3_3, R.id.board_cell_4_3, R.id.board_cell_5_3, },
        { R.id.board_cell_0_4, R.id.board_cell_1_4, R.id.board_cell_2_4, R.id.board_cell_3_4, R.id.board_cell_4_4, R.id.board_cell_5_4, },
        { R.id.board_cell_0_5, R.id.board_cell_1_5, R.id.board_cell_2_5, R.id.board_cell_3_5, R.id.board_cell_4_5, R.id.board_cell_5_5, }
    };

    private Board board;

    public BoardFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            board = (Board)getArguments().getSerializable(ARG_BOARD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
        board.draw();
    }

    public void onClickCell(View view) {
        Index index = findViewIdIndex(view.getId());
        board.clickCell(index.x, index.y);
    }

    public void setup(View view) {
        for (int y = 0; y < LAYOUT.length; y++) {
            for (int x = 0; x < LAYOUT[y].length; x++) {
                TextView textView = view.findViewById(LAYOUT[y][x]);
                textView.setOnClickListener(this::onClickCell);
            }
        }
    }


    private Index findViewIdIndex(int viewId) {
        int[][] viewIds = LAYOUT;
        for (int y = 0; y < viewIds.length; y++) {
            for (int x = 0; x < viewIds[y].length; x++) {
                if (viewId == viewIds[y][x]) {
                    return new Index(x, y);
                }
            }
        }

        return null;
    }

    public static BoardFragment newInstance(Board board) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOARD, board);
        fragment.setArguments(args);
        return fragment;
    }

    public static BoardFragment frame(FragmentManager manager, int id, Board board) {
        BoardFragment fragment = BoardFragment.newInstance(board);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
        return fragment;
    }

    private class Index {
        public final int x;
        public final int y;

        public Index(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}