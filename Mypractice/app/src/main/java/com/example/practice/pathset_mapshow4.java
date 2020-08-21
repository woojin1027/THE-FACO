package com.example.practice;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
//https://olsh1108o.tistory.com/entry/Android-Open-API-%EC%82%AC%EC%9A%A9%ED%95%B4%EC%84%9C-%EB%B6%80%EC%82%B0-%EB%B2%84%EC%8A%A4-%EC%96%B4%ED%94%8C-%EB%A7%8C%EB%93%A4%EA%B8%B0

public class pathset_mapshow4 extends FragmentActivity implements OnMapReadyCallback {

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    private final String TAG = "myTag";
    private final String endPoint = "http://openapi.gbis.go.kr/ws/rest/busstationservice"; //정류소 조회 서비스
    private final String key1 = "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D";

    pathSetting_end pathSetting_end;

    private String str_x;
    private String str_y;
    float float_x;
    float float_y;


    @Override
    public void onMapReady(GoogleMap googleMap) {
        float_x = Float.parseFloat(str_x);
        float_y = Float.parseFloat(str_y);

        LatLng POINT = new LatLng(float_x, float_y);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(POINT);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(POINT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        Toast.makeText(this,          // 현재 화면의 제어권자
                float_x +"와"+ float_y, // 보여줄 메시지
                Toast.LENGTH_LONG)    // 보여줄 기간 (길게, 짧게)
                .show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_search);
//준비상태
        new Thread(new Runnable() {
            @Override
            public void run() {
                //오퍼레이션 1  버스위치정보조회
                getStationLocationList();
            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //정류소명/번호목록조회 오퍼레이션
    private void getStationLocationList()
    {
        String stationUrl = endPoint + "?serviceKey=" + key1 + "&keyword=" + pathSetting_end.mytest;
        Log.d(TAG, "정류소 조회 : " + stationUrl);

        try
        {
            setUrlNParser(stationUrl);
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT: //xml 문서가 시작할 때
                        break;
                    case XmlPullParser.START_TAG:       //xml 문서의 태그의 첫부분 만날시
                        tag = xpp.getName();    //태그이름 얻어오기
                        if(tag.equals("busStationList"));  //첫번째 검색 결과
                        else if(tag.equals("x"))
                        {
                            str_x = xpp.getText();
                            xpp.next();
                            //float_x.add(xpp.getText());
                        }
                        else if(tag.equals("y"))
                        {
                            str_y = xpp.getText();
                            xpp.next();
                            //float_y.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:            //xml 문서의 텍스트 만날시
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("busStationList")); //첫번째 검색결과 종료
                        break;
                }
                eventType = xpp.next();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void setUrlNParser(String quary)
    {
        try
        {
            url = new URL(quary);   //문자열로 된 요청 url 을 URL객체로 생성
            is = url.openStream();  //입력스트림 객체 is 를 연다

            factory = XmlPullParserFactory.newInstance();
            xpp = factory.newPullParser();  //XmlPullParserFactory를 이용해 XmlPullParser 객체 생성
            xpp.setInput(new InputStreamReader(is, "UTF-8"));   //xml 입력받기

            xpp.next(); //공백을 기준으로 입력을 받는다.
            eventType = xpp.getEventType();
        }catch(Exception e){}
    }
}
