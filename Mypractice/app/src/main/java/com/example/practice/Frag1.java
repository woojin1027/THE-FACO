package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.URL;

public class Frag1 extends Fragment // Fragment 클래스를 상속받아야한다
{

    private View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.frag1,container,false);

        //text1 =(TextView) view.findViewById(R.id.busNum8);

        return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TextView textView8 = layout.findViewById(R.id.busNum8);
        TextView textView8 = view.findViewById(R.id.busNum8);
        textView8.OnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Frag1.this, subActivity.class);
                startActivity(intent);
            }
        };
    }

    //터치이벤트 넣는거 다시

}