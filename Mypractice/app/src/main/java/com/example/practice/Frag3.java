package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Frag3 extends Fragment // Fragment 클래스를 상속받아야한다
{

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag3,container,false);

        Button button_setting1 = view.findViewById(R.id.button_setting1);
        Button button_setting2 = view.findViewById(R.id.button_setting2);

        //버튼 클릭시 PathSetting으로 이동
        button_setting1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PathSetting.class);
                startActivity(intent);
            }});

        button_setting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PathSetting.class);
                startActivity(intent);
            }});

        return view;
    }
}
