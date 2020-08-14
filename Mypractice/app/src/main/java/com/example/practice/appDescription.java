package com.example.practice;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

//TheFaCo란?에 쓰이는 클래스
public class appDescription extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //ActionBar actionBar = getSupportActionBar();
        //actionBar.hide();

        setContentView(R.layout.descript_layout);
    }
}