package com.example.practice;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

//해야할것 : 정류장 클릭 시 텍스트뷰에 띄우게 바꾸기


public class yujin_note extends AppCompatActivity implements TextWatcher {

    ListView listView;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> country;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        listView = findViewById(R.id.list_excel);
        data = new ArrayList<HashMap<String, String>>();



        country = new HashMap<String, String>();
        country.put("STATION_NM", "Korea");
        country.put("MOBILE_NO", "Seoul");
        data.add(country);

        country = new HashMap<String, String>();
        country.put("STATION_NM", "Japan");
        country.put("MOBILE_NO", "Tokyo");
        data.add(country);

        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(), data,
                android.R.layout.simple_list_item_2,
                new String[]{"STATION_NM", "MOBILE_NO"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        listView.setAdapter(adapter);


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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}