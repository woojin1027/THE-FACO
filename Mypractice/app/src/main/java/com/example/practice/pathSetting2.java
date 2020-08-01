package com.example.practice;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class pathSetting2 extends Activity {
    EditText searchBox ;
    ListView listview ;
    String[] listItemsValue = new String[] {
            //api로 정류장이름 끌어오기
            //실패하면 하나하나넣기

            "Android",
            "PHP",
            "Web Development",
            "Blogger",
            "SEO",
            "Photoshop"
    };
    List<String> ListViewString ;

    ArrayAdapter<String> arrayadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting_2);

        searchBox = (EditText)findViewById(R.id.editText1);
        listview = (ListView)findViewById(R.id.listView1);

        ListViewString = new ArrayList<String>(Arrays.asList(listItemsValue));

        arrayadapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_2, android.R.id.text1, listItemsValue);

        listview.setAdapter(arrayadapter);

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                pathSetting2.this.arrayadapter.getFilter().filter(s);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}