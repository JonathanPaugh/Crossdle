package com.example.crossdle.app.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.crossdle.app.HistoryRecyclerAdapter;
import com.example.crossdle.R;
import com.example.crossdle.app.HistoryItem;
import com.example.crossdle.game.Board;

import java.time.LocalTime;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
/*        updateRecycler(R.id.history_recyclerView, new HistoryItem[] {
            new HistoryItem(this, LocalTime.now(), 5, 10, new Board('A')),
            new HistoryItem(this, LocalTime.now(), 2, 4, new Board('B')),
            new HistoryItem(this, LocalTime.now(), 100, 200, new Board('C')),
        });*/
    }

    private void updateRecycler(int recyclerViewId, HistoryItem[] data) {
        RecyclerView recycler = findViewById(recyclerViewId);
        HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(data);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}