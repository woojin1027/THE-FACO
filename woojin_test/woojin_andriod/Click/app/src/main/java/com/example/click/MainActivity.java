package com.example.click;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int number1 = 0;
    Button button;
    Animation scaleup,scaledown;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        scaleup = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaledown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN){
                    button.startAnimation(scaleup);
                }else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    button.startAnimation(scaledown);
                } return false;
            }
        });
    }
    public void onclick(View view) {
        number1 ++;
        TextView textView = (TextView) findViewById(R.id.textview);
        textView.setText("Button Clicked " + number1 + " times");
    }
}