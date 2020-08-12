package com.example.practice;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class BackPressCloseHandler extends Activity{
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressCloseHandler(Activity context){
        this.activity = context;
    }

    public void onBackPressed(){
        if (System.currentTimeMillis()>backKeyPressedTime+2000){
            backKeyPressedTime = System.currentTimeMillis();
            showGuide();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2000){
            activity.finish();
            toast.cancel();
        }
    }

    private void showGuide() {
//        LayoutInflater inflater = getLayoutInflater();
//        View layout = inflater.inflate( R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
//        TextView text = layout.findViewById(R.id.text);
//        Toast toast = new Toast(this);
//        text.setText("\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.");
//        text.setTextSize(15);
//        text.setTextColor(Color.WHITE);
//        toast.setGravity(Gravity.BOTTOM,0,0);
//        toast.setDuration(Toast.LENGTH_SHORT);
//        toast.setView(layout);
//        toast.show();
//

          toast = Toast.makeText(activity, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
          toast.show();
    }
}
