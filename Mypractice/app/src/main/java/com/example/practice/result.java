package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//출발지와 도착지의 위도경도를 받아서 예측 소요시간(=이동시간 + 대기시간) 알려주기,,.????


public class result extends AppCompatActivity {

    private final String TAG = "myTag";
    private ArrayList fname = new ArrayList();
    private ArrayList tname = new ArrayList();
    private ArrayList routeid = new ArrayList();
    private ArrayList routenm = new ArrayList();
    private ArrayList<ArrayList<String>> Fname = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> Tname = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> RouteId = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<String>> RouteNm = new ArrayList<ArrayList<String>>();
    private ArrayList Time = new ArrayList();

    TextView textView;
    TextView setting1;
    TextView setting2;
    Intent intent;

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    //대중교통환승경로 조회 서비스 API key
    private final String key = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D";
    private final String endPoint = "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBusNSub"; //요청 URL
    PathitemAdapter adapter = new PathitemAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.path_base);
        setting1 = findViewById(R.id.textview_setting1);
        setting2 = findViewById(R.id.textview_setting2);

        RecyclerView recyclerView = findViewById(R.id.recyclerview1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        intent = getIntent();
        final String str_1 = intent.getStringExtra("출발지"); //정류장명
        final String str_2 = intent.getStringExtra("도착지");

        final Double db_sx = intent.getDoubleExtra("출발지위도", 0);
        final Double db_sy = intent.getDoubleExtra("출발지경도", 0);

        final Double db_ex = intent.getDoubleExtra("도착지위도", 0);
        final Double db_ey = intent.getDoubleExtra("도착지경도", 0);

        setting1.setText(str_1);setting2.setText(str_2);

        Time.clear();
        Tname.clear();
        Fname.clear();
        RouteId.clear();
        RouteNm.clear();

        new Thread(new Runnable() {
            @Override
            public void run() {
                getPathInfoByBusNSubList(Double.toString(db_sy), Double.toString(db_sx), Double.toString(db_ey), Double.toString(db_ex));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, ""+ Fname + Tname + RouteNm + Time);
                        DataSet();
                    }
                });
            }
        }).start();


    }


    public void DataSet()
    {
        for(int i = 0; i < Time.size(); i++)
        {
            //시간이 모두 분으로 나오기 때문에 시,분으로 나누어줌
            if(Integer.parseInt(Time.get(i).toString()) >= 60)
            {
                int imodify = 0;
                int imodify2 = 0;
                imodify = Integer.parseInt(Time.get(i).toString()) / 60;    //시간
                imodify2 = Integer.parseInt(Time.get(i).toString()) % 60;   //분
                String settime = "시간";
                Time.set(i,imodify + settime + imodify2);
            }

            //리사이클러뷰 셋팅
            String Routesetting = new String();
            String Fnamesetting = new String();
            String Tnamesetting = new String();
            int Imagesetting;
            Routesetting = "";
            Fnamesetting = "";
            Tnamesetting = "";
            Imagesetting = 0;
            adapter.addItem(new Path_items("","","",0));

            //조건문 달아서 다시 셋팅
            for(int j = 0; j < RouteNm.get(i).size(); j++)
            {
                Routesetting = Routesetting + RouteNm.get(i).get(j) + "\n";
                if (j > 0)
                {
                    Fnamesetting = Fnamesetting + "(환승)" + Fname.get(i).get(j) + "\n";

                }
                else
                {
                    Fnamesetting = Fnamesetting + Fname.get(i).get(j) + "\n";
                }


                //이미지 셋팅
                if (RouteNm.get(i).get(j).contains("선"))
                {
                    Imagesetting = Imagesetting + R.drawable.bus_3;
                }
                else
                {
                    Imagesetting = Imagesetting +  R.drawable.bus_4;
                }

                //리사이클러뷰 최종 셋팅
                if (j == RouteNm.get(j).size() - 1)
                {
                    adapter.setItem(i, new Path_items("" + Time.get(i).toString() + "분", "" + Routesetting, "" + Fnamesetting + "(하차)" + Tname.get(i).get(j), 0));
                }
            }
        }
    }
    //오퍼레이션 4 (대중교통환승경로조회)
    private void getPathInfoByBusNSubList(String sX, String sY, String eX, String eY)
    {
        String stationUrl = endPoint + "?ServiceKey=" + key + "&startX=" + sX + "&startY=" + sY + "&endX=" + eX + "&endY=" + eY;
        Log.d(TAG, "대중교통환승경로 조회 서비스 : " + stationUrl);

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
                        if(tag.equals("itemList")) //첫번째 검색 결과
                        {
                            fname = new ArrayList();
                            tname = new ArrayList();
                            routeid = new ArrayList();
                            routenm = new ArrayList();
                        }
                        if(tag.equals("pathList"));
                        else if(tag.equals("fname"))    //탑승지이름
                        {
                            xpp.next();
                            fname.add(xpp.getText());
                        }
                        else if(tag.equals("tname"))    //하차지이름
                        {
                            xpp.next();
                            tname.add(xpp.getText());
                        }
                        else if(tag.equals("routeId"))
                        {
                            xpp.next();
                            routeid.add(xpp.getText());
                        }
                        else if(tag.equals("routeNm"))
                        {
                            xpp.next();
                            routenm.add(xpp.getText());
                        }
                        else if(tag.equals("time"))
                        {
                            xpp.next();
                            Time.add(xpp.getText());
                            Fname.add(fname);
                            Tname.add(tname);
                            RouteId.add(routeid);
                            RouteNm.add(routenm);
                        }
                        break;
                    case XmlPullParser.TEXT:            //xml 문서의 텍스트 만날시
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("itemList"));  //첫번째 검색결과 종료
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
