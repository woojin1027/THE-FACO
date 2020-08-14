package com.example.practice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.file.Path;

//출발지와 도착지 설정을 위한 클래스
//https://loveiskey.tistory.com/171
public class PathSetting extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final int[] selectedItem = {0};
        final String[] items = new String[]{"미금역", "정자역", "명동성당"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(PathSetting.this);

        dialog.setTitle("출발지를 고르세요.")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { selectedItem[0]=which;
                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup)findViewById(R.id.toast_layout));
                        TextView text = layout.findViewById(R.id.text);
                        Toast toast = new Toast(getApplicationContext());
                        text.setText(items[selectedItem[0]]);
                        text.setTextSize(15);
                        text.setTextColor(Color.WHITE);
                        toast.setGravity(Gravity.BOTTOM,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }
                })
                .setNeutralButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LayoutInflater inflater = getLayoutInflater();
                        View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup)findViewById(R.id.toast_layout));
                        TextView text = layout.findViewById(R.id.text);
                        Toast toast = new Toast(getApplicationContext());
                        text.setText("취소 버튼을 눌렀습니다.");
                        text.setTextSize(15);
                        text.setTextColor(Color.WHITE);
                        toast.setGravity(Gravity.BOTTOM,0,0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();
                    }
                });
        dialog.create();
        dialog.show();
    }
}




//        setContentView(R.layout.frag3);
//
//        final int[] selectedItem = {0};
//        showDialog = (Button) findViewById(R.id.button_setting1);
//        showDialog.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                //정류장 이름 띄우기
//                final String[] items = new String[]{"미금역", "정자역", "명동성당"};
//
//                AlertDialog.Builder dialog = new AlertDialog.Builder(PathSetting.this);
//                //MainActivity가 맞는지 확인하자 !
//
//                dialog.setTitle("출발지를 고르세요.")
//                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                selectedItem[0]=which;
//                            }
//                        })
//                        .setPositiveButton("확인", new DialogInterface.OnClickListener(){
//
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Toast.makeText(PathSetting.this, items[selectedItem[0]],Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .setNeutralButton("취소", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                LayoutInflater inflater = getLayoutInflater();
//                                View layout = inflater.inflate(R.layout.toast_layout,(ViewGroup)findViewById(R.id.toast_layout));
//                                TextView text = layout.findViewById(R.id.text);
//                                Toast toast = new Toast(getApplicationContext());
//                                text.setText("취소 버튼을 눌렀습니다.");
//                                text.setTextSize(15);
//                                text.setTextColor(Color.WHITE);
//                                toast.setGravity(Gravity.BOTTOM,0,0);
//                                toast.setDuration(Toast.LENGTH_SHORT);
//                                toast.setView(layout);
//                                toast.show();
//                            }
//                        });
//                dialog.create();
//                dialog.show();
//            }
//        });

