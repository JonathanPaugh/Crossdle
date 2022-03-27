package com.example.crossdle.app.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crossdle.R;

public class GamebarFragment extends Fragment {
    public GamebarFragment() {}

    public static GamebarFragment newInstance() {
        GamebarFragment fragment = new GamebarFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gamebar, container, false);
    }
}