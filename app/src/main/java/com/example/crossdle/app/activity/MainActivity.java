package com.example.crossdle.app.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
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
    private MediaPlayer mediaPlayer2;

    private boolean settingsOpen = false;
    private boolean stopMusic = true;

    String themeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HistoryActivity.updateBoardCount();

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

        Intent intent = getIntent();
        themeData = intent.getStringExtra("theme");
        if(themeData != null){
            switch (themeData){
                case "Strawberry":
                    getApplication().setTheme(R.style.theme_strawberry);
                    System.out.println("hello");
                case "Emerald Forest":
                    getApplication().setTheme(R.style.theme_strawberry);
                case "Ocean":
                    getApplication().setTheme(R.style.theme_strawberry);
            }
        }

        ConstraintLayout constraintLayout = findViewById(R.id.layout_main);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(5000);
        animationDrawable.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        View layoutView = findViewById(R.id.layout_main);

        if (!settingsOpen) {
            int fadeDuration = 2000;
            animFadeIn(layoutView, fadeDuration);
            layoutView.postDelayed(() -> animSlideIn(findViewById(R.id.layout_main)), (long)(fadeDuration * 0.5));

            mediaPlayer = MediaPlayer.create(this, R.raw.menu_song);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        } else {
            settingsOpen = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (!settingsOpen) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
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
                view.postDelayed(() -> {
                    buttons[index].setVisibility(View.INVISIBLE);
                }, duration);
            }, (long) i * interval);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button_main_history) {HistoryActivity.getCompleteHistoryDataBase();}
        int timeout = 1500;
        animSlideOut(findViewById(R.id.layout_main));
        view.postDelayed(() -> changeActivity(view), timeout);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.button_sound_effect);
        mediaPlayer2.start();

    }

    public void onOpenSettings(View view) {
        settingsOpen = true;
        Intent intent = new Intent(this, SettingsPopup.class);
        mediaPlayer2 = MediaPlayer.create(this, R.raw.button_sound_effect);
        mediaPlayer2.start();
        startActivity(intent);
    }

    private void changeActivity(View view) {
        if (view.getId() == R.id.button_main_daily_crossdle)  {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        if (view.getId() == R.id.button_main_random_crossdle) {
            Intent intent = new Intent(this, GameActivity.class);
            intent.putExtra(GameActivity.ARG_TYPE, true);
            startActivity(intent);
        }
        if (view.getId() == R.id.button_main_history) {
            Intent intent = new Intent(this, HistoryActivity.class);
            startActivity(intent);
        }
    }
}