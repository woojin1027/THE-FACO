package com.example.practice;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

public class pathset_mapshow extends Activity implements OnMapReadyCallback {
    GoogleMap mapView;

    private pathSetting_start pathSetting_start;
    String string_x, string_y;
    int int_x, int_y;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathsetting);
        StrictMode.enableDefaults();

        TextView status1 = (TextView) findViewById(R.id.result); //파싱된 결과확인!

        boolean bool_x = false, bool_y = false;


        try {
            URL url = new URL("http://openapi.gbis.go.kr/ws/rest/busstationservice?serviceKey="
                    + "d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D"
                    + "&keyword="
                    + pathSetting_start.selected_item
            );


            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("x")) {
                            bool_x = true;
                        }
                        if (parser.getName().equals("y")) {
                            bool_y = true;
                        }
                        break;
                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (bool_x) {
                            string_x = parser.getText();
                            bool_x = false;
                            int_x = Integer.parseInt(string_x);
                        }
                        if (bool_y) {
                            string_y = parser.getText();
                            bool_y = false;
                            int_y = Integer.parseInt(string_y);
                        }
                        break;
                }
                parserEvent = parser.next();
            }

        } catch (Exception e) {
            status1.setText("에러발생");
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng SEOUL = new LatLng(int_x, int_y);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(SEOUL);
        markerOptions.title("서울");
        markerOptions.snippet("한국의 수도");
        mapView.addMarker(markerOptions);

        mapView.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        mapView.animateCamera(CameraUpdateFactory.zoomTo(10));



    }
}


//
//    public void main(String[] args) throws IOException {
//        StringBuilder urlBuilder = new StringBuilder("http://openapi.gbis.go.kr/ws/rest/busstationservice"); /*URL*/
//        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=d6tEeUjm3AQ5KdyZhb2TVkcsfbM88hHVzwSaYUb4qRYG7N2Pzc9yw71hTeHUNmz7IUrf7GyX%2Ffe5hmgmn7qVqA%3D%3D"); /*Service Key*/
//        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(pathSetting_start.selected_item, "UTF-8")); /*정류소명 또는 번호(2자리이상)*/
//        URL url = new URL(urlBuilder.toString());
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setRequestMethod("GET");
//        conn.setRequestProperty("Content-type", "application/json");
//        System.out.println("Response code: " + conn.getResponseCode());
//        BufferedReader rd;
//
//        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
//            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//        }
//        else {
//            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
//        }
//        StringBuilder sb = new StringBuilder();
//        String line;
//        while ((line = rd.readLine()) != null) {
//            sb.append(line);
//        }
//        rd.close();
//        conn.disconnect();
//        System.out.println(sb.toString());
//
//
//
//

//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//
//        LatLng SEOUL = new LatLng(37.56, 126.97);
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(SEOUL);
//        markerOptions.title("서울");
//        markerOptions.snippet("한국의 수도");
//        mapView.addMarker(markerOptions);
//
//        mapView.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
//        mapView.animateCamera(CameraUpdateFactory.zoomTo(10));
//    }
//}
