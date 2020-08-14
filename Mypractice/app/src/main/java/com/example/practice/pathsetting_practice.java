package com.example.practice;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class pathsetting_practice extends AppCompatActivity implements TextWatcher {
    private ListView list_excel;
    EditText searchBox;
    final List<String[]> listItems = new LinkedList<String[]>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        searchBox = (EditText)findViewById(R.id.searchbox);  //검색창
        list_excel = (ListView)findViewById(R.id.list_excel);  //정류장데이터

        Excel();
        list_excel.setAdapter((ListAdapter) listItems);
        list_excel.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);
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
            int RowEnd = sheet.getColumn(MaxColumn - 1).length - 1;
            int ColumnStart = 1; //2행부터
            int ColumnEnd = sheet.getRow(2).length - 1;


            //excel file의 B2셀부터 B열의 모든 정보를 띄움 ( = 2019년 기준 경기도에 있는 모든 버스정류장 이름)
            for (int row = RowStart; row <= RowEnd; row++) {
                String excelload = sheet.getCell(ColumnStart, row).getContents();
                int row2 = row + 6;
                String excelload_2 = sheet.getCell(ColumnStart, row2).getContents();
                listItems.add(new String[]{ excelload , excelload_2});
            }
        }catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } finally {
            list_excel.setAdapter((ListAdapter) listItems);
            workbook.close();
        }


//        final int size = 20;
//        for(int i=0; i<size; i++){
//            listItems.add(new String[] { "Main title "+i, "Sub title "+i });
//        }
    }

    private void initList() {
        list_excel = (ListView) findViewById(R.id.list_excel);

        ArrayAdapter<String []> adapter = new ArrayAdapter<String []>(this,
                android.R.layout.simple_list_item_2,
                android.R.id.text1,
                listItems){
            @Override
            public View getView(int position, View convertView, ViewGroup parent){
                View row = super.getView(position, convertView, parent);

                TextView text1 = (TextView) row.findViewById(android.R.id.text1);
                TextView text2 = (TextView) row.findViewById(android.R.id.text2);

                String[] item = listItems.get(position);
                text1.setText(item[0]);
                text2.setText(item[1]);
                return row;
            }
        };



        list_excel.setAdapter(adapter);



        list_excel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                String[] item = listItems.get(position);
                String message = item[0]+"\n"+item[1];

                Toast.makeText(getApplicationContext(),
                        message,
                        Toast.LENGTH_SHORT).show();
            }
        });
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