package com.example.crossdle.app.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;


import com.example.crossdle.R;
import com.example.crossdle.app.popup.SettingsPopup;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button dailyCrossword;
    Button randomCrossword;
    Button history;
    Button settings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dailyCrossword = findViewById(R.id.button_main_daily_crossdle);
        randomCrossword = findViewById(R.id.button_main_random_crossdle);
        history = findViewById(R.id.button_main_history);
        settings = findViewById(R.id.button_main_settings);

        dailyCrossword.setOnClickListener(this);
        randomCrossword.setOnClickListener(this);
        history.setOnClickListener(this);
        settings.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == dailyCrossword.getId() || view.getId() == randomCrossword.getId()) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        if (view.getId() == history.getId()) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }

        if (view.getId() == settings.getId()) {
            Intent intent = new Intent(this, SettingsPopup.class);
            startActivity(intent);
        }
    }
}