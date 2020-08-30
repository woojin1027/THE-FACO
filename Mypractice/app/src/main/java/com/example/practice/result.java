package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//출발지와 도착지의 위도경도를 받아서 예측 소요시간(=이동시간 + 대기시간) 알려주기,,.????


public class result extends AppCompatActivity {

    TextView textView;
    TextView setting1;
    TextView setting2;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_base);
        textView = findViewById(R.id.textView4);
        setting1 = findViewById(R.id.textview_setting1);
        setting2 = findViewById(R.id.textview_setting2);


        intent = getIntent();
        String str_1 = intent.getStringExtra("출발지");
        String str_2 = intent.getStringExtra("도착지");

        Double db_sx = intent.getDoubleExtra("출발지위도", 0);
        Double db_sy = intent.getDoubleExtra("출발지경도", 0);

        Double db_ex = intent.getDoubleExtra("도착지위도", 0);
        Double db_ey = intent.getDoubleExtra("도착지경도", 0);

        setting1.setText(str_1);setting2.setText(str_2);

        textView.setText("출발지정보\n" + db_sx + "\n" + db_sy + "\n도착지정보\n" + db_ex + "\n" + db_ey);



    }
}
