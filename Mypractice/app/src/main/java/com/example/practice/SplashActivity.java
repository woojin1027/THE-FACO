package com.example.practice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {
    AnimationDrawable animDrawable;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_splash);
        LinearLayout view = (LinearLayout) findViewById(R.id.viewcontainer);
        ImageView imageView = findViewById(R.id.mainIcon);
        imageView.setImageResource(R.drawable.logo_transparent);
        //전체화면만들기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);


        animDrawable = (AnimationDrawable) (view.getBackground() != null && view.getBackground() instanceof AnimationDrawable ?
                view.getBackground() : null);
        if (animDrawable != null) {
            animDrawable.setEnterFadeDuration(700);
            animDrawable.setExitFadeDuration(700);
        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 2100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animDrawable != null && !animDrawable.isRunning()) {
            animDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animDrawable != null && animDrawable.isRunning()) {
            animDrawable.stop();
        }
    }
}
