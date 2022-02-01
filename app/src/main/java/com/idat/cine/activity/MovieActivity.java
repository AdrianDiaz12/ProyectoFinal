package com.idat.cine.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.idat.cine.R;
import com.idat.cine.screen.MovieScreen;

public class MovieActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Intent i = new Intent(this, MovieScreen.class);
        startActivity(i);
        finish();
    }
}