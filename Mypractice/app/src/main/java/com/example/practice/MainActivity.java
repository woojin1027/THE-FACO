package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


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
        //상태바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        backPressCloseHandler = new BackPressCloseHandler(this);
        setContentView(R.layout.activity_main_1);
        TextView textView3 = (TextView) findViewById(R.id.textView3);
        TextView textView4 = (TextView) findViewById(R.id.textView4);
        TextView textView5 = (TextView) findViewById(R.id.textView5);
        TextView textView6 = (TextView) findViewById(R.id.textView6);



        //텍스트뷰4 클릭에 대한 이벤트 처리
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            //텍스트뷰4 = 추천경로
            public void onClick(View view) {
                setContentView(R.layout.activity_main_2);
            }
        });


        /*//텍스트뷰5 클릭에 대한 이벤트 처리
        //근데 안먹힘ㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠㅠ
        textView5.setOnClickListener(new View.OnClickListener() {
            @Override
            //텍스트뷰5 = 대기인원파악
            public void onClick(View view) {
                setContentView(R.layout.activity_main_1);
            }
        });*/


        Button busbutton1 = (Button)findViewById(R.id.busNum4); //버튼1에 대한 참조획득
        Button busbutton2 = (Button)findViewById(R.id.busNum5); //버튼2에 대한 참조획득
        //버튼1 클릭에 대한 이벤트 처리
        busbutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            //버튼1
            public void onClick(View view)
            {
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup)findViewById(R.id.toast_layout));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("8100번 버스를 조회합니다");
                text.setTextSize(15);
                text.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();

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
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup)findViewById(R.id.toast_layout));
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getApplicationContext());
                text.setText("M4102번 버스를 조회합니다");
                text.setTextSize(15);
                text.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
                toast.show();

                Intent intent = new Intent(MainActivity.this, showActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //우측상단 메뉴가 보이도록 설정
        getMenuInflater().inflate(R.menu.menu_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // ''' 메뉴 설정
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.navigation_menu1://즐겨찾기
                return true;
            case R.id.navigation_menu2: //TheFaCo란?
                Intent descript_Intent = new Intent(this, app_Description.class);
                startActivity(descript_Intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}