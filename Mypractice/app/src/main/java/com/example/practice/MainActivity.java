package com.example.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

//BottomNavigationView 하단 고정시키기

public class MainActivity extends AppCompatActivity {
    private BackPressCloseHandler backPressCloseHandler;

    private BottomNavigationView bottomNavigationView; // 바텀 네비게이션 뷰
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Frag1 frag1;
    private Frag2 frag2;
    private Frag3 frag3;


    @Override
    public void onBackPressed() { //'뒤로' 두번누르면 종료
        backPressCloseHandler.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        backPressCloseHandler = new BackPressCloseHandler(this);


        // 권한 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 1000);
        }


        setContentView(R.layout.main);

        bottomNavigationView = findViewById(R.id.bottomNavi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.action_first:
                        setFrag(0); // 프레그먼트 교체
                        break;
                    case R.id.action_second:
                        setFrag(1);
                        break;
                    case R.id.action_third:
                        setFrag(2);
                        break;
                }
                return true;
            }
        });

        frag1=new Frag1();
        frag2=new Frag2();
        frag3=new Frag3();
        setFrag(0); // 첫 프래그먼트 화면 지정
    }


    // 프레그먼트 교체
    private void setFrag(int n)
    {
        fm = getSupportFragmentManager();
        ft= fm.beginTransaction();
        switch (n)
        {
            case 0:
                ft.replace(R.id.Main_Frame,frag1);
                ft.commit();
                break;

            case 1:
                ft.replace(R.id.Main_Frame,frag2);
                ft.commit();
                break;

            case 2:
                ft.replace(R.id.Main_Frame,frag3);
                ft.commit();
                break;
        }




        /* 원래있던거

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


        Button busbutton1 = (Button)findViewById(R.id.busNum4); //버튼1에 대한 참조획득
        Button busbutton2 = (Button)findViewById(R.id.busNum5); //버튼2에 대한 참조획득
        //버튼1 클릭에 대한 이벤트 처리
        busbutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            //버튼1
            {
            public void onClick(View view)
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

        */
        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //우측상단 메뉴가 보이도록 설정
        getMenuInflater().inflate(R.menu.menu_right, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { // 우측상단 메뉴 설정
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.navigation_menu1://즐겨찾기
                return true;
            case R.id.navigation_menu2: //TheFaCo란?
                //데이터 담아서 팝업(액티비티) 호출
                Intent intent = new Intent(this, popupActivity.class);
                intent.putExtra("data", "'THE FAstest COurse' 의 줄임말 입니다.\n 위 앱의 취지는 현재의 대중교통 서비스앱과 비교하여,가장빠른길을 알려주고, 위 앱의 사용자들또한, 각자의 이상에 가장 빠른길로 나아가는데 도움이 되었으면 하는 바램으로 선정하였습니다.");
                //value에 설명할 내용을 적으면 됨

                startActivityForResult(intent, 1);


//                Intent descript_Intent = new Intent(this, appDescription.class);
//                startActivity(descript_Intent);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                finish();
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}