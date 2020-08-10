package com.example.practice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

//해야할것 : 정류장 클릭 시 버튼(이나 텍스트뷰)에 띄우게 바꾸기
//미금역 검색하면 나오는 검은 창 없애기-ㅁ-;;;;

// 참고  https://loveiskey.tistory.com/171

public class pathSetting_8100 extends AppCompatActivity implements TextWatcher {
    EditText searchBox;
    ListView list_excel;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);



        searchBox = (EditText)findViewById(R.id.searchbox);  //검색창
        list_excel = (ListView)findViewById(R.id.list_excel);  //정류장데이터
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1); //텍스트뷰 하나로 구성된 내장 레이아웃
        Excel(); //데이터 읽기

        list_excel.setAdapter(arrayAdapter);
        list_excel.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);
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
            }
//            for(int row = RowStart_2; row <= RowEnd; row++) {
//                String excelload_2 = sheet.getCell(ColumnStart, row).getContents();
//                arrayAdapter.add(excelload_2);
//            }

//
//            //이거 씀
//            final TextView sss = findViewById(R.id.textview_setting1);
//            //여기부터 씀
//            list_excel.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    sss.setText((CharSequence) list_excel);
//
//                }
//            });

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
}