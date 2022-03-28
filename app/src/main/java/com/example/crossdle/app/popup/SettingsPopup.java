package com.example.crossdle.app.popup;

import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import com.example.crossdle.R;

public class SettingsPopup extends Activity implements View.OnClickListener{
    AudioManager audioManager;
    int currentMusicVolume, currentEffectsVolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getActionBar().hide();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.8), (int)(height*.42));

        Button confirm = findViewById(R.id.button_settings_confirm);
        confirm.setOnClickListener(this);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentMusicVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        currentEffectsVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar seekBarMusicVolume =findViewById(R.id.seekBar_settings_music_volume);
        SeekBar seekBarEffectsVolume =findViewById(R.id.seekBar_settings_effects_volume);

        seekBarMusicVolume.setMax(maxVolume);
        seekBarEffectsVolume.setMax(maxVolume);
        seekBarMusicVolume.setProgress(currentMusicVolume);
        seekBarEffectsVolume.setProgress(currentEffectsVolume);

        seekBarMusicVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                currentMusicVolume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarEffectsVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                currentEffectsVolume = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        finish();
    }
}