package com.example.practice;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag3 extends Fragment // Fragment 클래스를 상속받아야한다
{

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag3,container,false);

        TextView setting1 = view.findViewById(R.id.textview_setting1); //버튼이 아니라 텍스트뷰로 바꿔봄 -> 경로 설정시 데이터 바꿔야되서
        TextView setting2 = view.findViewById(R.id.textview_setting2);

        //버튼or텍스트뷰 클릭 시 PathSetting으로 이동

        setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), pathSetting_start.class);
                startActivity(intent);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,container,false);
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getActivity());
                text.setText("출발지를 설정해주세요");
                text.setTextSize(15);
                text.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();

            }});

        setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), pathSetting_end.class);
                startActivity(intent);

                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,container,false);
                TextView text = layout.findViewById(R.id.text);
                Toast toast = new Toast(getActivity());
                text.setText("도착지를 설정해주세요");
                text.setTextSize(15);
                text.setTextColor(Color.WHITE);
                toast.setGravity(Gravity.BOTTOM,0,0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            }});

        return view;
    }
}
