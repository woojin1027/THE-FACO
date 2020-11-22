package com.thefacoteam.thefaco;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.thefacoteam.thefaco.busBtn.btnEvent_3007;
import com.thefacoteam.thefaco.busBtn.btnEvent_8100;
import com.thefacoteam.thefaco.busBtn.btnEvent_M4102;

public class Frag1 extends Fragment// Fragment 클래스를 상속받아야한다
{

    private View view;
    Animation scaleUp,scaleDown;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag1,container,false);
        final Button button8 = view.findViewById(R.id.busNum8);
        final Button button9 = view.findViewById(R.id.busNum9);
        final Button button10 = view.findViewById(R.id.busNum10);

        scaleUp = AnimationUtils.loadAnimation(getContext(),R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(getContext(),R.anim.scale_down);

        button8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                    button8.startAnimation(scaleUp);
                else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    button8.startAnimation(scaleDown);
                } return false;
            }
        });
        button9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                    button9.startAnimation(scaleUp);
                else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    button9.startAnimation(scaleDown);
                } return false;
            }
        });
        button10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                    button10.startAnimation(scaleUp);
                else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    button10.startAnimation(scaleDown);
                } return false;
            }
        });


        //버튼 클릭시 sub, showActivity로 이동
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastshow8100(container);
                Intent intent = new Intent(getActivity(), btnEvent_8100.class);
                startActivity(intent);
        }});


        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastshowM4102(container);
                Intent intent = new Intent(getActivity(), btnEvent_M4102.class);
                startActivity(intent);
            }});

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastshowM4102(container);
                Intent intent = new Intent(getActivity(), btnEvent_3007.class);
                startActivity(intent);
            }});

        return view;
    }

    //custom toast띄우기
   private void toastshow8100(ViewGroup container) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,container,false);
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(getActivity());
        text.setText("8100번 버스를 조회합니다");
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    private void toastshowM4102(ViewGroup container) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,container,false);
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(getActivity());
        text.setText("M4102번 버스를 조회합니다");
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}