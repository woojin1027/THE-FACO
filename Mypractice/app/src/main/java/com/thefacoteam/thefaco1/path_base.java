package com.thefacoteam.thefaco1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class path_base extends AppCompatActivity
{

    private View view;
    TextView setting1;
    TextView setting2;

    TextView search;
    private String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_base);

//        setting1 = findViewById(R.id.textview_setting1); //출발지텍스트뷰
//        setting2 = findViewById(R.id.textview_setting2); //도착지텍스트뷰
//        search = findViewById(R.id.textView5); //조회
        Intent intent = getIntent();
        Intent intent2 = getIntent();

        final String start = intent.getStringExtra("출발지");
        final String end = intent2.getStringExtra("도착지");

        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_start = new Intent(path_base.this, pathSetting_start.class);
                startActivity(intent_start);
                toastshow("출발지를 설정해주세요");
            }});

        setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_end = new Intent(path_base.this, pathSetting_end.class);
                startActivity(intent_end);
                toastshow("도착지를 설정해주세요");
            }});

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(tag, start +" 와 " + end);
                if (start == null){
                    toastshow("출발지를 설정해주세요!");
                }
                if (end == null){
                    toastshow("도착지를 설정해주세요!");
                }

                if(start !=null & end !=null){
                    Intent intent_result = new Intent(path_base.this, result.class);
                    startActivity(intent_result);
                }
            }
        });
//
//        if(start!=null){
//            setting1.setText(start);
//        }
//        if(end!=null){
//            setting2.setText(end);
//        }
    }

    private void toastshow(String string) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,null);
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(getApplicationContext());
        text.setText(string);
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }
}