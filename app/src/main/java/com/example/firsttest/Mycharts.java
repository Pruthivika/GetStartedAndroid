package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.SeekBar;

public class Mycharts extends AppCompatActivity {

    CircleFillView circleFill;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycharts);

        circleFill = (CircleFillView) findViewById(R.id.circleFillView);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setProgress(circleFill.getValue());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
                                           {
                                               @Override
                                               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
                                               {
                                                   if (fromUser)
                                                       circleFill.setValue(progress);
                                               }

                                               @Override
                                               public void onStartTrackingTouch(SeekBar seekBar) {}

                                               @Override
                                               public void onStopTrackingTouch(SeekBar seekBar) {}
                                           }
        );
    }
}
