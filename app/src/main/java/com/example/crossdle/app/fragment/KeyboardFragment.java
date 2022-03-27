package com.example.crossdle.app.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.crossdle.R;
import com.example.crossdle.game.Board;

public class KeyboardFragment extends Fragment {
    private static final String ARG_BOARD = "ARG_BOARD";

    private static final int[][] KEYS = new int[][]
    {
        {
            R.id.keyboard_button_q,
            R.id.keyboard_button_w,
            R.id.keyboard_button_e,
            R.id.keyboard_button_r,
            R.id.keyboard_button_t,
            R.id.keyboard_button_y,
            R.id.keyboard_button_u,
            R.id.keyboard_button_i,
            R.id.keyboard_button_o,
            R.id.keyboard_button_p
        },
        {
            R.id.keyboard_button_a,
            R.id.keyboard_button_s,
            R.id.keyboard_button_d,
            R.id.keyboard_button_f,
            R.id.keyboard_button_g,
            R.id.keyboard_button_h,
            R.id.keyboard_button_j,
            R.id.keyboard_button_k,
            R.id.keyboard_button_l
        },
        {
            R.id.keyboard_button_z,
            R.id.keyboard_button_x,
            R.id.keyboard_button_c,
            R.id.keyboard_button_v,
            R.id.keyboard_button_b,
            R.id.keyboard_button_n,
            R.id.keyboard_button_m,
        },
    };

    private Board board;

    private static final int KEY_ENTER = R.id.keyboard_button_enter;
    private static final int KEY_BACK = R.id.keyboard_button_back;

    public KeyboardFragment() {}

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
        return inflater.inflate(R.layout.fragment_keyboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setup(view);
    }

    public void onClickKey(View view) {
        board.clickKey(((TextView)view).getText().charAt(0));
    }

    public void onClickBack(View view) {
        board.clickBack();
    }

    public void setup(View view) {
        Button buttonBack = view.findViewById(KEY_BACK);
        buttonBack.setOnClickListener(this::onClickBack);
        for (int y = 0; y < KEYS.length; y++) {
            for (int x = 0; x < KEYS[y].length; x++) {
                Button buttonKey = view.findViewById(KEYS[y][x]);
                buttonKey.setOnClickListener(this::onClickKey);
            }
        }
    }

    public static KeyboardFragment newInstance(Board board) {
        KeyboardFragment fragment = new KeyboardFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOARD, board);
        fragment.setArguments(args);
        return fragment;
    }

    public static KeyboardFragment frame(AppCompatActivity activity, int id, Board board) {
        KeyboardFragment fragment = KeyboardFragment.newInstance(board);
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        transaction.commit();
        return fragment;
    }
}