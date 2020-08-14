package com.example.practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

//해야할것 : 정류장 클릭 시 텍스트뷰에 띄우게 바꾸기


public class pathSetting_start extends AppCompatActivity implements TextWatcher {
    EditText searchBox;
    ListView list_excel;
    ArrayAdapter<String> arrayAdapter;
    TextView textView;
    Button nearby_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        searchBox = (EditText)findViewById(R.id.searchbox);  //검색창
        list_excel = (ListView)findViewById(R.id.list_excel);  //정류장데이터
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //텍스트뷰 한개로 구성된 내장 레이아웃
        nearby_stop = (Button) findViewById(R.id.nearby_stop);


        Excel(); //데이터 읽기

        list_excel.setAdapter(arrayAdapter);
        list_excel.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);


        nearby_stop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pathSetting_start.this, mapActivity.class);
                startActivity(intent);
            }
        });


        //정류장 클릭시 이벤트
        list_excel.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = (String)parent.getItemAtPosition(position);
                textView = findViewById(R.id.textview_setting1);
                //textView.setText(selected_item); 출발지 텍스트가 안바뀐다.......ㅠ
                toastshow(view, selected_item);
                pathSetting_start.super.onBackPressed();
            }
        });
    }

    private void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("StationInfo.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);

            int MaxColumn = 2;
            int RowStart = 1; //B열
            int RowEnd = sheet.getColumn(MaxColumn - 1).length -1;
            int ColumnStart = 1; //2행부터
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
        list_excel.setFilterText(searchBox.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (searchBox.getText().length() == 0) {
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