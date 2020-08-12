package com.example.practice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

//해야할것 : 정류장 클릭 시 버튼(이나 텍스트뷰)에 띄우게 바꾸기
//미금역 검색하면 나오는 검은 창 없애기-ㅁ-;;;;

// 참고  https://loveiskey.tistory.com/171

public class pathSetting_start extends AppCompatActivity implements TextWatcher {
    EditText searchBox;
    ListView list_excel;
    ArrayAdapter<String> arrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        searchBox = (EditText)findViewById(R.id.searchbox);  //검색창
        list_excel = (ListView)findViewById(R.id.list_excel);  //정류장데이터
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1); //텍스트뷰 한개로 구성된 내장 레이아웃
        Excel(); //데이터 읽기

        list_excel.setAdapter(arrayAdapter);
        list_excel.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);


        //정류장 클릭시 이벤트
        list_excel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selected_item = (String)parent.getItemAtPosition(position);
                Toast myToast = Toast.makeText(getApplicationContext(), selected_item, Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }


    public void Excel() {
        Workbook workbook = null;
        Sheet sheet = null;
        try {
            InputStream inputStream = getBaseContext().getResources().getAssets().open("StationInfo.xls");
            workbook = Workbook.getWorkbook(inputStream);
            sheet = workbook.getSheet(0);

            int MaxColumn = 2;
            int RowStart = 1; //B열
            int RowStart_2 = 7; //H열
            int RowEnd = sheet.getColumn(MaxColumn - 1).length -1;
            int ColumnStart = 1; //2행부터
            int ColumnEnd = sheet.getRow(2).length - 1;


            //excel file의 B2셀부터 B열의 모든 정보를 띄움 ( = 2019년 기준 경기도에 있는 모든 버스정류장 이름)
            for(int row = RowStart; row <= RowEnd; row++) {
                String excelload = sheet.getCell(ColumnStart, row).getContents();
                arrayAdapter.add(excelload);
//                String excelload_2 = sheet.getCell(ColumnStart, row+6).getContents();
//                arrayAdapter.add(excelload_2);

            }
//            for(int row = RowStart_2; row <= RowEnd; row++) {
//                String excelload_2 = sheet.getCell(ColumnStart, row).getContents();
//                arrayAdapter.add(excelload_2);
//            }



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


    //주변 정류장 검색
    public void mynear(View view) {

    }
}