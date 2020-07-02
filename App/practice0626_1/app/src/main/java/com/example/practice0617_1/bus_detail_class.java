package com.example.practice0617_1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/* 버스를 골랐을 때 나오는 화면 */

public class bus_detail_class extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_detail);


        TextView textView2 = (TextView)findViewById(R.id.busNumber);
        ImageView imageView2 = (ImageView)findViewById(R.id.F5);

        //일단 M4102로 테스트


    }//class end
}
