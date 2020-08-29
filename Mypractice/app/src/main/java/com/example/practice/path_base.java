package com.example.practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class path_base extends AppCompatActivity // Fragment 클래스를 상속받아야한다
{

    private View view;
    TextView setting1;
    TextView setting2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_base);

        setting1 = (TextView) findViewById(R.id.textview_setting1); //출발지텍스트뷰
        setting2 = (TextView) findViewById(R.id.textview_setting2); //도착지텍스트뷰

        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(path_base.this, pathSetting_start.class);
                startActivity(intent);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,null);
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("출발지를 설정해주세요");
                text.setTextSize(15);
                text.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }});

        setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(path_base.this, pathSetting_end.class);
                startActivity(intent);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,null);
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("도착지를 설정해주세요");
                text.setTextSize(15);
                text.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }});

    }
}