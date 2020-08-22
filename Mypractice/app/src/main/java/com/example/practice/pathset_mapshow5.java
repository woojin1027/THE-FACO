package com.example.practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
//https://movie13.tistory.com/1
public class pathset_mapshow5 extends Activity {
    pathSetting_end pathSetting_end = new pathSetting_end();

    private final String key = "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D";
    String bbbb = ((pathSetting_end) pathSetting_end.context).mytest;
    int mytest_int = Integer.parseInt(bbbb);
    String aaaa = ((pathSetting_end) pathSetting_end.context).mytest_name;

    TextView text;
    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yujintest);
        text = (TextView) findViewById(R.id.yujintexttest);

    }

    public void mOnClick(View v) {

        switch (v.getId()) {
            case R.id.button:

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        data = getStationLocationList();//아래 메소드를 호출하여 XML data를 파싱해서 String 객체로 얻어오기


                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                text.setText(data); //TextView에 문자열  data 출력
                            }
                        });

                    }
                }).start();

                break;
        }
    }


    String getStationLocationList() {
        StringBuffer buffer = new StringBuffer();


        String queryUrl = "http://openapi.gbis.go.kr/ws/rest/busstationservice"//요청 URL
                + "?serviceKey=" + key
                + "&keyword=" + aaaa;

        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType = xpp.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();//태그 이름 얻어오기
                        if (tag.equals("busStationList")) ;// 첫번째 검색결과
                        else if(tag.equals("mobileNo")) {
                            int a = xpp.next();
                            if (a != mytest_int) //mobileNo랑 다르면
                            {
                                //append하지 않음

                                //근데 append의 반대가 뭘까.......
                                break;
                            }

                        }else if (tag.equals("x")) {
                                buffer.append("위도 : ");
                                xpp.next();
                                buffer.append(xpp.getText());//
                                buffer.append("\n");

                        } else if (tag.equals("y")) {
                                buffer.append("경도 : ");
                                xpp.next();
                                buffer.append(xpp.getText());//
                                buffer.append("\n");
                            }
                        break;


                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기

                        if (tag.equals("busStationList")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈

                        break;
                }

                eventType = xpp.next();
            }

        } catch (Exception e) {
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();
    }
}
