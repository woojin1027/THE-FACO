package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //상태바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        Button busbutton1 = (Button)findViewById(R.id.busNum1); //버튼1에 대한 참조획득
        Button busbutton2 = (Button)findViewById(R.id.busNum2); //버튼2에 대한 참조획득
        //버튼1 클릭에 대한 이벤트 처리
        busbutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            //버튼1
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, subActivity.class);
                startActivity(intent);
            }
        });

        //버튼2 클릭에 대한 이벤트 처리
        busbutton2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            //버튼2
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, showActivity.class);
                startActivity(intent);
            }
        });

    }

}

