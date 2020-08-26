package com.example.practice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class stationApi extends AppCompatActivity {
    //정류장 위도 경도 파싱
    public static Context context;
    pathSetting_end pathSetting_end = new pathSetting_end();
    private String tag;
    Float float_x;
    Float float_y;
    String str_x, str_y;

    private final String key = "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D";


    String aaaa = ((pathSetting_end) pathSetting_end.context).mytest_name;
    String bbbb = ((pathSetting_end) pathSetting_end.context).mytest;
    int mytest_int = Integer.parseInt(bbbb);

    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_search);

        init();

    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                getStation();
                Log.d(tag, "여긴들어옴?");
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(stationApi.this, practice.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }

    private void getStation() {
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://openapi.gbis.go.kr/ws/rest/busstationservice"//요청 URL
                + "?serviceKey=" + key
                + "&keyword=" + mytest_int;

        Log.d(tag, "여긴?");
        try
        {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결
            Log.d(tag, queryUrl);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            Log.d(tag, "여긴??22222");
            String tag = null;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        Log.d(tag, "파싱시작");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("busStationList")) ;// 첫번째 검색결과
                        else if (tag.equals("mobileNo")) {
                            int a = xpp.next();
                            if (a != mytest_int) //mobileNo랑 다르면
                            {
                                //append하지 않음
                                //근데 append의 반대가 뭘까.......
                                break;
                            }
                        } else if (tag.equals("x")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_x = xpp.getText();
                            float_x = Float.parseFloat(str_x);
                            buffer.append("\n");

                        } else if (tag.equals("y")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_y = xpp.getText();
                            float_y = Float.parseFloat(str_y);
                            buffer.append("\n");
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("busStationList")) ;// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch(Exception e){Log.d(tag, "에러발생");}
        Log.d(tag,"위도 : "+float_x +"\n경도 : "+float_y);
        Log.d(tag,"파싱종료");
//        setMyLocation(float_x,float_y,mGoogleMap);


    }
}
