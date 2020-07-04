package com.example.practice0617_1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* 나중에 패키지 이름 바꾸기  https://c10106.tistory.com/2268  <참고
* activity_main에서 리스트 개수에 맞게 이미지 띄우는 방법 찾기
* activity_main에서 리스트 옆의 동그라미 없애는 방법 찾기
* splash 색이 맘에 안든다 불편ㅡㅡ
* bus_detail.xml UI바꾸기
*/

public class MainActivity extends AppCompatActivity {

    TextView textView1;
    ListView Bus_List;
    ArrayList<String> data;
    ArrayAdapter<String> adapter;
    private BackPressCloseHandler backPressCloseHandler;


    @Override
    public void onBackPressed() { //'뒤로' 두번누르면 종료
        backPressCloseHandler.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        backPressCloseHandler = new BackPressCloseHandler(this);

        /*ActionBar ab = getSupportActionBar() ;
        ab.setIcon(R.drawable.icon_pink);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
        왜 아이콘이랑 글자랑 동시에 표현이 안되는거야ㅠ-ㅠ
        */

        // 권한 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {Manifest.permission.INTERNET}, 1000);
        }


        data = new ArrayList<String>();
        data.add("M4102");
        data.add("8100");
        //https://gtothe1.tistory.com/entry/%EB%A6%AC%EC%8A%A4%ED%8A%B8%EB%B7%B0-%ED%95%AD%EB%AA%A9-%EC%B6%94%EA%B0%80-%EC%82%AD%EC%A0%9C%ED%95%98%EA%B8%B0
        //위에 링크 참고
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, data);
        Bus_List = (ListView) findViewById(R.id.Bus_List);
        Bus_List.setAdapter(adapter);
        Bus_List.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        //list에서 항목을 선택했을 때 호출되는 메소드 설정
        Bus_List.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //선택한 데이터를 Toast로 출력
                /*putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값*/
                String item = data.get(position);
                Toast.makeText(MainActivity.this, item + "번 버스를 조회합니다", Toast.LENGTH_SHORT).show();
                openActivity2(); //새로운 창 열기
            }
        });
    }

    public void openActivity2() { //버스 고르면 bus_detail창 여는 함수
        Intent bus_detail_Intent = new Intent(this, bus_detail_class.class);
        startActivity(bus_detail_Intent);
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

}//class end