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


public class unused_pathsetting extends AppCompatActivity implements TextWatcher {
    public String selected_item;
    EditText searchBox;
    ListView list_excel;
    ArrayAdapter<String> arrayAdapter;
    TextView textView;
    Button nearby_stop;
    MapView mapView;
    ImageView icon_search;
    String selected_item2="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        searchBox = (EditText)findViewById(R.id.searchbox);  //검색창
        icon_search = (ImageView)findViewById(R.id.icon_search);  //돋보기 아이콘

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //텍스트뷰 한개로 구성된 내장 레이아웃
        nearby_stop = (Button) findViewById(R.id.nearby_stop);


        Excel(); //데이터 읽기

        list_excel.setVisibility(View.INVISIBLE);
        list_excel.setAdapter(arrayAdapter);
        list_excel.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);


        //엔터키막기
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER)
                {
                    imm.hideSoftInputFromWindow(searchBox.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });


        //주변 정류장 클릭시 이벤트
        nearby_stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(unused_pathsetting.this, mapActivity.class);
                startActivity(intent);
            }
        });


        //정류장 클릭시 이벤트

        //을 돋보기 클릭시로 바꿈
        icon_search.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                selected_item = String.valueOf(searchBox.getText());

                Intent intent = new Intent(unused_pathsetting.this, pathset_mapshow.class);
                try {
                    selected_item2 = URLEncoder.encode(selected_item, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
//                intent.putExtra("selected_item", selected_item2);
                startActivity(intent);
            }
        });
//
//        list_excel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                selected_item = (String)parent.getItemAtPosition(position);
//
//
//                //이름이 같은 정류장은 어케 구분하지
//
//
//                int selected_item_MOBILE_NO = (int) parent.getItemIdAtPosition(position);
//
//                textView = findViewById(R.id.textview_setting1);
//                //textView.setText(selected_item); 출발지 텍스트가 안바뀐다.......ㅠ
//                toastshow(view, selected_item);
//
//                Intent intent = new Intent(pathSetting_start.this, pathset_mapshow.class);
//                intent.putExtra("selected_item", selected_item);
//                startActivity(intent);
//
//                //pathSetting_start.super.onBackPressed();
//            }
//        });
    }



    private void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("StationInfo.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);

            int MaxColumn = 2;
            int RowStart = 1; //2행부터 아래로
            int RowEnd = sheet.getColumn(MaxColumn - 1).length -1;
            int ColumnStart = 1; //B열만
            int ColumnEnd = sheet.getRow(2).length - 1;


            //excel file의 B2셀부터 B열의 모든 정보를 띄움 ( = 2019년 기준 경기도에 있는 모든 버스정류장 이름)
            for(int row = RowStart; row <= RowEnd; row++) {
                String excelload = sheet.getCell(ColumnStart, row).getContents();
                arrayAdapter.add(excelload);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            list_excel.setAdapter(arrayAdapter);
            workbook.close();
        }
    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        //list_excel.setVisibility(View.VISIBLE);
        list_excel.setFilterText(searchBox.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (searchBox.getText().length() == 0) {
            //list_excel.setVisibility(View.VISIBLE);
            list_excel.clearTextFilter();
        }
    }


    public void toastshow(View view, String string) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate( R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(this);
        text.setText(string + "을(를) 선택");
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show(); }

}