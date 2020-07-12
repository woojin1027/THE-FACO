package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onBackPressed() { //'뒤로' 두번누르면 종료
        backPressCloseHandler.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        backPressCloseHandler = new BackPressCloseHandler(this);

        //상태바 없애기
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);

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
                Toast.makeText(MainActivity.this, "8100번 버스를 조회합니다", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(MainActivity.this, "M4102번 버스를 조회합니다", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //우측상단 메뉴가 보이도록 설정
        getMenuInflater().inflate(R.menu.menu_bottom, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // ''' 메뉴 설정
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.navigation_menu1:
                return true;
            case R.id.navigation_menu2: //TheFaCo란?
                Intent descript_Intent = new Intent(this, app_Description.class);
                startActivity(descript_Intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            case R.id.navigation_menu3:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

