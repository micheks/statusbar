package com.alliky.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.alliky.statusbar.StatusBarUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtils.with(MainActivity.this).reset().statusBarDarkFont(false, 0.3f).statusBarColor(R.color.colorAccent).fitsSystemWindows(true).init();
    }
}