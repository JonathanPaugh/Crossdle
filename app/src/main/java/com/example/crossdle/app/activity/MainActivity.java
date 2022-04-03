package com.example.crossdle.app.activity;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import com.example.crossdle.R;
import com.example.crossdle.app.popup.SettingsPopup;


//https://firebase.google.com/docs/auth/android/password-auth?authuser=0

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[] buttons;

    private Button settings;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HistoryActivity.updateBoardCount();
        setContentView(R.layout.activity_main);

        View layoutView = findViewById(R.id.layout_main);
        settings = findViewById(R.id.button_main_settings);

        buttons = new Button[] {
            findViewById(R.id.button_main_daily_crossdle),
            findViewById(R.id.button_main_random_crossdle),
            findViewById(R.id.button_main_history),
            settings
        };

        for (Button button: buttons) {
            button.setOnClickListener(this);
        }

        settings.setOnClickListener(this::onOpenSettings);

        int fadeDuration = 2000;
        animFadeIn(layoutView, fadeDuration);
        layoutView.postDelayed(() -> animSlideIn(findViewById(R.id.layout_main)), (long)(fadeDuration * 0.5));




        mediaPlayer = MediaPlayer.create(this, R.raw.menu_song);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();

        HistoryActivity.getCompleteHistoryDataBase();
    }

    private void animFadeIn(View view, int duration) {
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
            }, (long) i * interval);
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
                view.postDelayed(() -> buttons[index].setVisibility(View.INVISIBLE), duration);
            }, (long) i * interval);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_main_history) {
           HistoryActivity.getCompleteHistoryDataBase();
        }
        int timeout = 1500;
        animSlideOut(findViewById(R.id.layout_main));
        view.postDelayed(() -> changeActivity(view), timeout);
    }


    public void onOpenSettings(View view) {
        Intent intent = new Intent(this, SettingsPopup.class);
        startActivity(intent);
    }

    private void changeActivity(View view) {
        if (view.getId() == R.id.button_main_daily_crossdle
            || view.getId() == R.id.button_main_random_crossdle) {
            Intent intent = new Intent(this, GameActivity.class);
            mediaPlayer.stop();
            mediaPlayer.release();
            startActivity(intent);
        }
        if (view.getId() == R.id.button_main_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
    }
}