package com.example.practice;


//마이페이지

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.security.cert.CertPathValidatorException;

public class Frag3 extends Fragment // Fragment 클래스를 상속받아야한다
{
    private View view;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        Button egg_1 = view.findViewById(R.id.egg_1);
        Button reason_1 = view.findViewById(R.id.reason1);
        Button reason_2 = view.findViewById(R.id.reason2);

        egg_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EggActivity.class);
                startActivity(intent); // 액티비티 이동.
            }
        });
        reason_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Reason.class);
                startActivity(intent); // 액티비티 이동.
            }
        });
        reason_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Reason2.class);
                startActivity(intent); // 액티비티 이동.
            }
        });
        return view;

    }
}
