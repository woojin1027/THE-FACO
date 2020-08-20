package com.example.practice;

import android.os.Bundle;
import android.os.StrictMode;
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

//리스트 클릭 시 그 특정 정류장을 보여주는 클래스
/*https://coding-factory.tistory.com/39*/

public class pathset_mapshow extends FragmentActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    private pathSetting_end pathSetting_end;
    float float_x, float_y;
    float Array[][];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.enableDefaults();
        setContentView(R.layout.map_search);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_search);
        mapFragment.getMapAsync(this);


        //TextView status1 = (TextView) findViewById(R.id.result2); //파싱된 결과확인

        boolean bool_x = false, bool_y = false;

        String string_x, string_y;


        try {
            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/busstationservice?serviceKey=" //정류소명/번호목록조회
                    + "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D"
                    + "&keyword="
                    + pathSetting_end.selected_item
            );


            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("busStationList")) {
                            if (parser.getName().equals("x")) {
                                bool_x = true;
                            }
                            if (parser.getName().equals("y")) {
                                bool_y = true;
                            }
                            if(parser.getName().equals("message")){ //message 태그를 만나면 에러 출력
                                Toast.makeText(this,"error",Toast.LENGTH_LONG).show();

//                                status1.setText(status1.getText()+"에러");
                                //여기에 에러코드에 따라 다른 메세지를 출력하도록 할 수 있다.
                            }
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (bool_x) {
                            string_x = parser.getText();
                            float_x = Float.parseFloat(string_x);
                            bool_x = false;
                        }
                        if (bool_y) {
                            string_y = parser.getText();
                            float_y = Float.parseFloat(string_y);
                            bool_y = false;
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("msgbody")) {
                            Toast.makeText(this, "\n 위도:" + float_x + "\n 경도:" + float_y + "\n", Toast.LENGTH_LONG).show();

//      수정중                      status1.setText(status1.getText()+"\n 위도 : " + float_x + "\n 경도 : " + float_y +"\n");
//                            int i = 0, j = 0;
//                            for (i = 0, i <= 10, i++){
//                                for (j = 0, j <= 10, j++) {
//                                    Array[i][j] = Array[(float) float_x][float_y];
//                                }
//                            }
//                            Array[i][j]
                        }
                        break;
                }
                parserEvent = parser.next();
            }

        } catch (Exception e) {
            Toast.makeText(this,"에러가 났습니다",Toast.LENGTH_LONG).show();
//            status1.setText("에러가 났습니다");
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(37.56, 126.97);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10));

//     수정중   if (float_x != 0){
//            do {
//                LatLng marker[] = new LatLng[float_x, float_y];
//                MarkerOptions markerOptions = new MarkerOptions();
//
//            }while();
//        }

    }
}