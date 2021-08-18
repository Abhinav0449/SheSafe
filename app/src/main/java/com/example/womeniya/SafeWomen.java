package com.example.womeniya;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SafeWomen extends AppCompatActivity {
    private static int loading = 1000;

    @Override
    protected void onCreate(Bundle a) {
        super.onCreate(a);
        setContentView(R.layout.activity_entry);
        getSupportActionBar().hide();
        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SafeWomen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, loading);
    }
}
