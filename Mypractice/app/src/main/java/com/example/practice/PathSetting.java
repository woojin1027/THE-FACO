package com.example.practice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.file.Path;

//출발지와 도착지 설정을 위한 클래스
//https://loveiskey.tistory.com/171
public class PathSetting extends AppCompatActivity {
    private Button showDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);

        final int[] selectedItem = {0};
        showDialog = (Button) findViewById(R.id.button_set1);
        showDialog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //정류장 이름 띄우기
                final String[] items = new String[]{"미금역", "정자역", "명동성당"};

                AlertDialog.Builder dialog = new AlertDialog.Builder(PathSetting.this);
                //MainActivity가 맞는지 확인하자 !

                dialog.setTitle("출발지를 고르세요.")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem[0]=which;
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PathSetting.this, items[selectedItem[0]],Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(PathSetting.this, "취소 버튼을 눌렀습니다.",Toast.LENGTH_SHORT).show();

                            }
                        });
                dialog.create();
                dialog.show();
            }
        });
    }
}
