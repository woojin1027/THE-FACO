package com.thefacoteam.thefaco1;


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

public class Frag3 extends Fragment // Fragment 클래스를 상속받아야한다
{
    private View view;
    Animation scaleUp,scaleDown;
    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override

    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.frag3, container, false);

        Button egg_1 = view.findViewById(R.id.egg_1);
        final Button reason_1 = view.findViewById(R.id.reason1);
        final Button reason_2 = view.findViewById(R.id.reason2);

        scaleUp = AnimationUtils.loadAnimation(getContext(),R.anim.scale_up2);
        scaleDown = AnimationUtils.loadAnimation(getContext(),R.anim.scale_down2);

        reason_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                    reason_1.startAnimation(scaleUp);
                else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    reason_1.startAnimation(scaleDown);
                } return false;
            }
        });

        reason_2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                    reason_2.startAnimation(scaleUp);
                else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    reason_2.startAnimation(scaleDown);
                } return false;
            }
        });

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
