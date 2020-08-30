package com.example.practice;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class BaseActivity2 extends AppCompatActivity
{
    public static ArrayList<Activity> actList = new ArrayList<Activity>();

    public void actFinish2(){
        for(int i = 0; i < actList.size(); i++)
            actList.get(i).finish();
    }
}
