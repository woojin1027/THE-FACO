package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
//출발지와 도착지의 위도경도를 받아서 예측 소요시간(=이동시간 + 대기시간) 알려주기,,.????


public class result extends AppCompatActivity {

    TextView textView;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        textView = findViewById(R.id.textView12);
        intent = getIntent();

        Double db_sx = intent.getDoubleExtra("출발지위도", 0);
        Double db_sy = intent.getDoubleExtra("출발지경도", 0);

        Double db_ex = intent.getDoubleExtra("도착지위도", 0);
        Double db_ey = intent.getDoubleExtra("도착지경도", 0);

        textView.setText("출발지정보\n" + db_sx + "\n" + db_sy + "\n도착지정보\n" + db_ex + "\n" + db_ey);



    }
}
