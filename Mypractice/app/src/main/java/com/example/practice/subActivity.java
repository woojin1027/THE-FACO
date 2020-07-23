package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//8100번 버스의 자바 파일
public class subActivity extends AppCompatActivity {

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key1 = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint1 = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice"; //버스도착정보목록조회 앞 주소
    private final String endPoint2 = "http://openapi.gbis.go.kr/ws/rest/buslocationservice"; // 버스위치정보목록조회 앞 주소
    private final String route = "234000878";

    //xml 변수
    //private TextView xmlShowInfo;

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    //xml 값 입력 변수
    //private StringBuffer buffer;

    private ArrayList listBus;
    private ArrayList listmin1;
    private ArrayList listmin2;
    private ArrayList liststation1;
    private ArrayList liststation2;
    private ArrayList listBusseq;
    private ArrayList liststationId;
    private ArrayList listseatCnt;

    BusitemAdapter2 adapter = new BusitemAdapter2();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle("8100");
        setContentView(R.layout.activity_sub);
        //buffer = new StringBuffer();
        listmin1 = new ArrayList();
        listmin2 = new ArrayList();
        liststation1 = new ArrayList();
        liststation2 = new ArrayList();
        listBusseq = new ArrayList();
        liststationId = new ArrayList();
        listseatCnt = new ArrayList();
        listBus = new ArrayList();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        listBus.add("단국대.치과병원");
        listBus.add("단국대정문");
        listBus.add("꽃메마을.새에덴교회");
        listBus.add("보정동주민센터");
        listBus.add("오리역");
        listBus.add("미금사거리(미정차)");
        listBus.add("미금역.청솔마을.2001아울렛");
        listBus.add("불정교사거리(미정차)");
        listBus.add("정자역");
        listBus.add("분당구청입구.수내교");
        listBus.add("판교TG(미정차)");
        listBus.add("금토JC(미정차)");
        listBus.add("서울진입(미정차)");
        listBus.add("청계산입구역(미정차)");
        listBus.add("양재IC(미정차)");
        listBus.add("서초IC(미정차)");
        listBus.add("반포IC(미정차)");
        listBus.add("한남IC(미정차)");
        listBus.add("순천향대학병원");
        listBus.add("한남1고가차도(미정차)");
        listBus.add("남산1호터널TG(미정차)");
        listBus.add("남대문세무서.국가인권위원회");
        listBus.add("종로2가사거리(중)");
        listBus.add("을지로입구역.광교");
        listBus.add("북창동.남대문시장");
        listBus.add("서울역버스환승센터(6번승강장)(중)");
        listBus.add("서울역교차로(미정차)");
        listBus.add("숭례문");
        listBus.add("남대문로3가(미정차)");
        listBus.add("명동국민은행앞");
        listBus.add("남대문세무서.서울백병원(중)");
        listBus.add("남산1호터널TG(미정차)");
        listBus.add("한남1고가차도(미정차)");
        listBus.add("순천향대학병원");
        listBus.add("한남IC(미정차)");
        listBus.add("반포IC(미정차)");
        listBus.add("서초IC(미정차)");
        listBus.add("양재IC(미정차)");
        listBus.add("청계산입구역(미정차)");
        listBus.add("성남진입(미정차)");
        listBus.add("금토JC(미정차)");
        listBus.add("판교TG(미정차)");
        listBus.add("분당구청입구.수내교");
        listBus.add("정자역");
        listBus.add("미금역.청솔마을.2001아울렛");
        listBus.add("오리역");
        listBus.add("보정동주민센터");
        listBus.add("꽃메마을2단지");
        listBus.add("단국대.치과병원");


        for(int i = 0; i < listBus.size(); i++)
        {
            adapter.addItem(new Bus_items("" + listBus.get(i).toString(),""));
        }

    }

    //새로고침 누를때 동작
    public void refresh(View view)
    {
        listmin1.clear();
        listmin2.clear();
        liststation1.clear();
        liststation2.clear();
        listBusseq.clear();
        liststationId.clear();
        listseatCnt.clear();

        //준비상태
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1  버스위치정보조회
                getBusLocationList();

                for(int i = 0; i < listBusseq.size(); i++)
                {
                    for(int j = 1; j <= 39; j++)
                    {
                        if(listBusseq.contains(j) == true)
                        {
                            //오퍼레이션 2
                            getBusArrivalItem(liststationId.get(i).toString(), listBusseq.get(i).toString());
                        }
                    }
                }
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, listBusseq + " " + liststationId + " " + listmin1 + " " + liststation1);
                        Log.d(TAG, listBusseq + " " + liststationId + " " + listmin2 + " " + liststation2);
                        for(int i = 0; i < listBusseq.size(); i++)
                        {
                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"버스가 이 정류장을 지나고 있습니다.\n 빈 좌석 : " + listseatCnt.get(i)));
                            for(int j= 0; j < listBus.size(); j++)
                            {
                                if(Integer.parseInt(listBusseq.get(i).toString()) != j)
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),""));
                                }
                            }
                        }


                    }

                });
            }
        }).start();
        adapter.notifyDataSetChanged();

    }

    //오퍼레이션 2 (버스도착정보항목조회)
    private void getBusArrivalItem(String station, String staorder)
    {
        String stationUrl = endPoint1 + "?serviceKey=" + key1 + "&stationId=" + station + "&routeId=" + route + "&staOrder=" + staorder;
        Log.d(TAG, "버스도착정보조회 : " + stationUrl);

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
                        if(tag.equals("busArrivalList"));  //첫번째 검색 결과
                        else if(tag.equals("locationNo1"))
                        {
                            xpp.next();
                            liststation1.add(xpp.getText());
                        }
                        else if(tag.equals("predictTime1"))
                        {
                            xpp.next();
                            listmin1.add(xpp.getText());
                        }
                        else if(tag.equals("locationNo2"))
                        {
                            xpp.next();
                            liststation2.add(xpp.getText());
                        }
                        else if(tag.equals("predictTime2"))
                        {
                            xpp.next();
                            listmin2.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:            //xml 문서의 텍스트 만날시
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("busArrivalList")); //첫번째 검색결과 종료
                        break;
                }
                eventType = xpp.next();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    //오퍼레이션 1 (버스위치정보목록조회)
    private void getBusLocationList()
    {
        String stationUrl = endPoint2 + "?serviceKey=" + key1 + "&routeId=" + route;
        Log.d(TAG, "버스위치정보 조회 : " + stationUrl);

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
                        if(tag.equals("busLocationList"));  //첫번째 검색 결과
                        else if(tag.equals("stationSeq"))
                        {
                            xpp.next();
                            listBusseq.add(xpp.getText());
                        }
                        else if(tag.equals("stationId"))
                        {
                            xpp.next();
                            liststationId.add(xpp.getText());
                        }
                        else if(tag.equals("remainSeatCnt"))
                        {
                            xpp.next();
                            listseatCnt.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:            //xml 문서의 텍스트 만날시
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("busLocationList")); //첫번째 검색결과 종료
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
