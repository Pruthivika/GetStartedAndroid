package com.example.firsttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void test(View view) {
        Intent intent = new Intent(this, Mycharts.class);
        startActivity(intent);
    }

    public void Voice(View view) {
        Intent intent = new Intent(this,VoiceRecog.class);
        startActivity(intent);
    }

    public void AugReality(View view) {
        Intent intent = new Intent(this,AugmentedReality.class);
        startActivity(intent);
    }
}
