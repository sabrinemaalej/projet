package com.example.apppfe;



import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class parametre extends AppCompatActivity {

    private SeekBar seekBar;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parametre);

        seekBar = findViewById(R.id.volumeIndicator);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Set the maximum volume based on the stream's max volume
        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        // Set the current progress of the seekbar to the current volume
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    // Method to increase volume when the "plus" button is clicked
    public void upbouton(View view) {
        audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND);
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        Toast.makeText(this, "Volume increased", Toast.LENGTH_SHORT).show();
    }

    // Method to decrease volume when the "moins" button is clicked
    public void downbouton(View view) {
        audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND);
        seekBar.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
        Toast.makeText(this, "Volume decreased", Toast.LENGTH_SHORT).show();
    }
}
