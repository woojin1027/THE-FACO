package com.thefacoteam.thefaco1;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
//여러 액티비티를 한번에 종료하기 위한 액티비티
public class BaseActivity2 extends AppCompatActivity
{
    public static ArrayList<Activity> actList = new ArrayList<Activity>();

    public void actFinish2(){
        for(int i = 0; i < actList.size(); i++)
            actList.get(i).finish();
    }
}
