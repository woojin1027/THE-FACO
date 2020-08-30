package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.odsay.odsayandroidsdk.ODsayService;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

//출발지와 도착지의 위도경도를 받아서 예측 소요시간(=이동시간 + 대기시간) 알려주기,,.????


public class result extends AppCompatActivity {

    TextView textView;
    TextView setting1;
    TextView setting2;
    Intent intent;
    private String tag;

    //오디세이 API key
    private final String key = "R5bpJwNRAixx4VHc0lP0BvI4bGQJAOn7s0CC0iNKads";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_base);
        textView = findViewById(R.id.textView4);
        setting1 = findViewById(R.id.textview_setting1);
        setting2 = findViewById(R.id.textview_setting2);


        intent = getIntent();
        String str_1 = intent.getStringExtra("출발지"); //정류장명
        String str_2 = intent.getStringExtra("도착지");

        Double db_sx = intent.getDoubleExtra("출발지위도", 0);
        Double db_sy = intent.getDoubleExtra("출발지경도", 0);

        Double db_ex = intent.getDoubleExtra("도착지위도", 0);
        Double db_ey = intent.getDoubleExtra("도착지경도", 0);

        setting1.setText(str_1);setting2.setText(str_2);

        textView.setText("출발지정보\n" + db_sx + "\n" + db_sy + "\n도착지정보\n" + db_ex + "\n" + db_ey);
        searchPubTransPath(db_sx,db_sy,db_ex,db_ey);

//
//        // 싱글톤 생성, Key 값을 활용하여 객체 생성
//        ODsayService odsayService = ODsayService.init(getApplicationContext(), key);
//        // 서버 연결 제한 시간(단위(초), default : 5초)
//        odsayService.setReadTimeout(5000);
//        // 데이터 획득 제한 시간(단위(초), default : 5초)
//        odsayService.setConnectionTimeout(5000);
//
//        // 콜백 함수 구현
//        OnResultCallbackListener onResultCallbackListener = new OnResultCallbackListener() {
//            // 호출 성공 시 실행
//            @Override
//            public void onSuccess(ODsayData odsayData, API api) {
//                try {
//                    // API Value 는 API 호출 메소드 명을 따라갑니다.
//                    if (api == API.BUS_STATION_INFO) {
//                        String stationName = odsayData.getJson().getJSONObject("result").getString("stationName");
//                        Log.d("Station name : %", stationName);
//                    }
//                }catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            // 호출 실패 시 실행
//            @Override
//            public void onError(int i, String s, API api) {
//                if (api == API.BUS_STATION_INFO) {}
//            }
//        };
//        // API 호출
//        odsayService.requestBusStationInfo("107475", onResultCallbackListener());

    }
//
//    private OnResultCallbackListener onResultCallbackListener() {
//        return null;
//    }


    public String searchPubTransPath(Double a, Double b, Double c, Double d) //대중교통 길찾기 파싱
    {

        Log.d(tag, "순서 4 : 파싱");
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "https://api.odsay.com/v1/api/searchPubTransPath"//요청 URL
                + "?apiKey=" + key
                + "?lang=0"
                + "&SX=" + b
                + "&SY=" + a
                + "&EX=" + d
                + "&EY=" + c;

        Log.d(tag, queryUrl);

        String str_x = null;
        String str_y = null;
        try {
            URL url = new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is = url.openStream(); //url위치로 입력스트림 연결
            Log.d(tag, queryUrl);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

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
                        if (tag.equals("result")) ;// 첫번째 검색결과
                        else if (tag.equals("totalTime")) {
                            xpp.next();
                            buffer.append(xpp.getText());
                            str_x = xpp.getText();
                            int int_a = Integer.parseInt(str_x);
                            //buffer.append("\n");
                        } else if (tag.equals("busNo")) {
                            int int_b = xpp.next();
                            str_y = Integer.toString(int_b);
                            if (str_y == "8100" | str_y == "m4102") {
                                xpp.next();
                                buffer.append(xpp.getText());
                                str_y = xpp.getText();
                                Double double_x = Double.parseDouble(str_y);
                            } else {
                                break;
                            }

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

        } catch (Exception e) {
            Log.d(tag, "에러발생");
        }
        Log.d(tag, str_x + " 와 " + str_y);
        Log.d(tag, "파싱종료");

        return buffer.toString();
    }
}
