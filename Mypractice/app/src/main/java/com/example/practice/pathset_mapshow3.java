package com.example.practice;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class pathset_mapshow3 extends FragmentActivity implements OnMapReadyCallback {

    private pathSetting_end pathSetting_end;
    float float_x = 0, float_y=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.map_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_search);
        mapFragment.getMapAsync(this);

        boolean initem=false , in_x=false, in_y=false;
        String str_x, str_y;


        try{
            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/busstationservice?serviceKey="
                    + "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D"
                    + "&keyword="
                    + pathSetting_end.mytest
                    //pathSetting_end.mytest가 정류소번호(ex 02001)이다
            ); //검색 URL부분

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");
            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("busStationList")){
                            initem = true;
                        }
                        if(parser.getName().equals("x")){
                            in_x = true;
                        }
                        if(parser.getName().equals("y")){
                            in_y = true;
                        }

                        if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                            Toast myToast = Toast.makeText(this.getApplicationContext(),"message error", Toast.LENGTH_SHORT);
                            myToast.show();
                        }
                        break;


                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(initem){
                            if(in_x){
                                str_x = parser.getText();
                                in_x = false;
                                float_x = Float.parseFloat(str_x);
                            }
                            if(in_y){ //isMapx이 true일 때 태그의 내용을 저장.
                                str_y = parser.getText();
                                in_y = false;
                                float_y = Float.parseFloat(str_y);
                            }
                        }
                        break;


                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("busStationList")){
                            Toast myToast = Toast.makeText(this.getApplicationContext(),"위도 : "+ float_x +"\n 경도: "+ float_y +"\n", Toast.LENGTH_SHORT);
                            myToast.show();

                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
            Toast myToast = Toast.makeText(this.getApplicationContext(),"에러가..났습니다..", Toast.LENGTH_SHORT);
            myToast.show();
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng POINT = new LatLng(float_x, float_y);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(POINT);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(POINT));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        Toast.makeText(this,          // 현재 화면의 제어권자
                float_x+"와"+float_y, // 보여줄 메시지
                Toast.LENGTH_LONG)    // 보여줄 기간 (길게, 짧게)
                .show();
    }
}