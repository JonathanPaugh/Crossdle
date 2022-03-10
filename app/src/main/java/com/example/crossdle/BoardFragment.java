package com.example.crossdle;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {

    private static final String ARG_LAYOUT = "ARG_LAYOUT";

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

    public static BoardFragment newInstance(Board board) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LAYOUT, board);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            board = (Board)getArguments().getSerializable(ARG_LAYOUT);
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

        for (int y = 0; y < LAYOUT.length; y++) {
            for (int x = 0; x < LAYOUT.length; x++) {
                char data = board.getCell(x, y);
                TextView textView = view.findViewById(LAYOUT[y][x]);
                if (data == Character.MIN_VALUE || Character.isSpaceChar(data)) {
                    textView.setBackgroundColor(Color.BLACK);
                } else {
                    textView.setText(String.valueOf(data));
                }
            }
        }
    }

    public static void displayFragment(AppCompatActivity activity, int id, Board board) {
        BoardFragment fragment = BoardFragment.newInstance(board);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
    }
}