package com.example.crossdle.app.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import com.example.crossdle.R;



//https://firebase.google.com/docs/auth/android/password-auth?authuser=0

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button dailyCrossword;
    private Button randomCrossword;
    private Button history;
    private Button settings;

    private Button[] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttons = new Button[] {
            findViewById(R.id.button_main_daily_crossdle),
            findViewById(R.id.button_main_random_crossdle),
            findViewById(R.id.button_main_history),
            findViewById(R.id.button_main_settings)
        };

        for (Button button: buttons) {
            button.setOnClickListener(this);
        }

        View view = findViewById(R.id.layout_main);

        int fadeDuration = 2000;
        animFadeIn(view, fadeDuration);
        view.postDelayed(() -> animSlideIn(findViewById(R.id.layout_main)), (long)(fadeDuration * 0.5));
    }

    private void animFadeIn(View view, int duration) { ;
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void animSlideIn(View view) {
        int interval = 250;
        int duration = 1000;

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fui_slide_in_right);
            animation.setDuration(duration);
            view.postDelayed(() -> {
                buttons[index].startAnimation(animation);
                buttons[index].setVisibility(View.VISIBLE);
            }, i * interval);
        }
    }

    private void animSlideOut(View view) {
        int interval = 250;
        int duration = 1000;

        for (int i = 0; i < buttons.length; i++) {
            final int index = i;
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.fui_slide_out_left);
            animation.setDuration(duration);
            view.postDelayed(() -> {
                buttons[index].startAnimation(animation);
                view.postDelayed(() -> {
                    buttons[index].setVisibility(View.INVISIBLE);
                }, duration);
            }, i * interval);
        }
    }

    @Override
    public void onClick(View view) {
        int timeout = 1500;
        animSlideOut(findViewById(R.id.layout_main));
        view.postDelayed(() -> changeActivity(view), timeout);
    }

    private void changeActivity(View view) {
        if (view.getId() == R.id.button_main_daily_crossdle
            || view.getId() == R.id.button_main_random_crossdle) {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.button_main_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
    }
}