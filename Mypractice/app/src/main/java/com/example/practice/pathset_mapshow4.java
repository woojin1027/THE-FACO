package com.example.practice;

import android.os.Bundle;
import android.util.Log;

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
    private final String key = "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D";

    pathSetting_end pathSetting_end = new pathSetting_end();


    private Double db_x;
    private Double db_y;
    Double float_x;
    Double float_y;


    String bbbb = ((pathSetting_end)pathSetting_end.context).mytest;
    int mytest_int = Integer.parseInt(bbbb);
    String aaaa = ((pathSetting_end)pathSetting_end.context).mytest_name;


    @Override
    public void onMapReady(GoogleMap googleMap) {
//        float_x = Double.parseDouble(db_x);
//        float_y = Double.parseDouble(db_y);

        LatLng POINT = new LatLng(db_x, db_y);
        Log.d(tag ,"\n위도 : " + db_x + "\n경도 : " + db_y + "\n");

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(POINT);
        markerOptions.title(aaaa);
        markerOptions.snippet(bbbb);
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(POINT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_search);

        getStationLocationList();
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    //정류소명/번호목록조회 오퍼레이션
    private void getStationLocationList()
    {

        String stationUrl = endPoint + "?serviceKey=" + key + "&keyword=" + mytest_int;
        Log.d(TAG, "정류소 조회 : " + stationUrl);

        try
        {
            setUrlNParser(stationUrl);
            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT: //xml 문서가 시작할 때
                        Log.d(TAG, "파싱 시작합니다.");

                    case XmlPullParser.START_TAG:       //xml 문서의 태그의 첫부분 만날시
                        tag = xpp.getName();    //태그이름 얻어오기
                        if(tag.equals("busStationList"))
                        {
                            if(tag.equals("x"))
                            {
                                db_x = Double.valueOf(xpp.getText());
                                //xpp.next();
                                //float_x.add(xpp.getText());
                            }
                            if(tag.equals("y"))
                            {
                                db_y = Double.valueOf(xpp.getText());
                                //xpp.next();
                                //float_y.add(xpp.getText());
                            }
                            Log.d(TAG, "데이터 얻었습니다.");
                        }  //첫번째 검색 결과


                    case XmlPullParser.TEXT://xml 문서의 텍스트 만날시
                        Log.d(TAG, "Break-"); break;


                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("busStationList")); //첫번째 검색결과 종료
                        Log.d(TAG, "파싱을 종료합니다.");

                        break;
                }
//                eventType = xpp.next();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    private void setUrlNParser(String quary) //URL 세팅
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
