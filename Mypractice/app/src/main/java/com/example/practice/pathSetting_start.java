package com.example.practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.MapView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import jxl.Image;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

//해야할것 : 정류장 클릭 시 텍스트뷰에 띄우게 바꾸기


public class pathSetting_start extends AppCompatActivity implements TextWatcher {

    public String selected_item;
    ListView list_new;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> data_hashmap;
    EditText searchBox;
    ImageView icon_search;
    Button nearby_stop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        searchBox = (EditText) findViewById(R.id.searchbox);  //검색창
        list_new = findViewById(R.id.list_new);  //정류장데이터
        icon_search = (ImageView) findViewById(R.id.icon_search);  //돋보기 아이콘
        nearby_stop = (Button) findViewById(R.id.nearby_stop);

        data = new ArrayList<HashMap<String, String>>();




        /*데이터 넣기 노가다 작업 시작지점*/
        //https://m.blog.naver.com/PostView.nhn?blogId=gi_balja&logNo=221162720020&proxyReferer=https:%2F%2Fwww.google.com%2F


        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "단국대.치과병원");
        data_hashmap.put("MOBILE_NO", "1");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "단국대정문");
        data_hashmap.put("MOBILE_NO", "2");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "꽃메마을.새에덴교회");
        data_hashmap.put("MOBILE_NO", "3");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "보정동주민센터");
        data_hashmap.put("MOBILE_NO", "4");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "오리역");
        data_hashmap.put("MOBILE_NO", "5");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "미금역.청솔마을.2001아울렛");
        data_hashmap.put("MOBILE_NO", "6");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "정자역");
        data_hashmap.put("MOBILE_NO", "7");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("STATION_NM", "분당구청입구.수내교");
        data_hashmap.put("MOBILE_NO", "8");
        data.add(data_hashmap);


        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(), data,
                android.R.layout.simple_list_item_2,
                new String[]{"STATION_NM", "MOBILE_NO"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        list_new.setAdapter(adapter);

        /*노가다 끝지점*/


        list_new.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);


        //엔터키막기
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });

        //주변 정류장 클릭시 이벤트
        nearby_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(pathSetting_start.this, mapActivity.class);
                startActivity(intent);
            }
        });


        //돋보기 클릭 시 -> 리스트 띄우고 -> 리스트 누르면 지도 띄우기

        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_new.setVisibility(View.VISIBLE);
                toastshow(v, "검색 결과");
//                selected_item = (String)parent.getItemAtPosition(position);
//                toastshow(v, selected_item);
            }
        });

        list_new.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ListView list_new = (ListView)parent;
                String item = (String) list_new.getItemAtPosition(position);
                toastshow(view, item + "를 선택하시겠습니까?");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void toastshow(View view, String string) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate( R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(this);
        text.setText(string);
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show(); }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        list_new.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        list_new.setVisibility(View.INVISIBLE);
        list_new.setFilterText(searchBox.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (searchBox.getText().length() == 0) {
            list_new.clearTextFilter();
        }
    }
}