package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

//8100번 버스의 자바 파일
public class subActivity extends AppCompatActivity {

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key1 = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint1 = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice"; //버스도착정보목록조회 앞 주소
    private final String endPoint2 = "http://openapi.gbis.go.kr/ws/rest/buslocationservice"; // 버스위치정보목록조회 앞 주소
    private final String endPoint3 = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"; //버스도착정보조회 앞 주소
    private final String AWSendPoint = " https://w5yp3bwer4.execute-api.ap-northeast-2.amazonaws.com/project/projectFunction"; //AWS api gateway 의 엔드포인트
    private final String route = "234000878";

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;
    private String result; //rest api 호출한 값 담는 변수

    private ArrayList listBus;
    private ArrayList liststation1;
    private ArrayList liststation2;
    private ArrayList listBusseq;
    private ArrayList liststationId;
    private ArrayList listseatCnt;
    private ArrayList listBusstop;
    private ArrayList AllStationId;
    private ArrayList DBStationId;
    private ArrayList DBLineCnt;
    private ArrayList DBStaOrder;
    private ArrayList DBSeatcnt1;
    private ArrayList DBSeatcnt2;

    private ArrayList CalStaOrder;
    private ArrayList CalculData;
    private ArrayList CalculData2;

    BusitemAdapter2 adapter = new BusitemAdapter2();

    int []BusstopArr = new int[]{1,2,3,4,6,8,9,18,21,22,23,24,27,29,30,33,42,43,44,45,46,47};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle("8100");
        setContentView(R.layout.activity_sub);

        liststation1 = new ArrayList();
        liststation2 = new ArrayList();
        listBusseq = new ArrayList();
        liststationId = new ArrayList();
        listseatCnt = new ArrayList();
        listBus = new ArrayList();
        listBusstop = new ArrayList();
        AllStationId = new ArrayList();
        DBSeatcnt1 = new ArrayList();
        DBSeatcnt2 = new ArrayList();

        for(int i = 0; i < BusstopArr.length; i++)
        {
            listBusstop.add(BusstopArr[i]);
        }

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

        //리사이클러뷰 셋팅
        RecyclerViewSet();

        liststation1.clear();
        liststation2.clear();
        listBusseq.clear();
        liststationId.clear();
        listseatCnt.clear();
        AllStationId.clear();
        DBSeatcnt1.clear();
        DBSeatcnt2.clear();


        init();

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        //당겨서 새로고침 구현
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                init();

                //2~3번 눌러야 바뀔 확률이 높기 때문에 notifyDataSetchanged() 를 세번 호출
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    //리사이클러뷰 셋팅
    public void RecyclerViewSet()
    {
        for(int i = 0; i < listBus.size(); i++)
        {
            if(i == 0)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,null,0));
            }
            else if(i < 25)
            {
                if(listBusstop.contains(i))
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,null,0));
                }
                else
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                }
            }
            else if(i == 25)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
            }
            else if(i < 48)
            {
                if (listBusstop.contains(i))
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                }
                else
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                }
            }
            else
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
            }
        }
    }

    //조건문 처리 메서드
    public void init()
    {
        //준비상태
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1  버스위치정보조회
                getBusLocationList();

                //오퍼레이션 3 버스도착정보조회
                getArrInfoByRouteAllList();

                //rest api 호출
                getLineData();

                //호출한 rest api JSON 값을 변환
                JSONParser();

                //대기인원 계산
                DataCalculate();

                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, listBusseq + " " + liststationId + " " + result);
                        //버스 리셋
                        for(int j = 0; j < listBus.size(); j++)
                        {
                            if(j == 0)
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스" + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation1.get(j).toString() + "\n2번째 버스" + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,"대기인원 수\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.woodicon));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                }
                            }
                            else if(j < 25)
                            {
                                if(listBusstop.contains(j))
                                {
                                    if(DBStationId.contains(AllStationId.get(j)))
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스" + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation1.get(j).toString() + "\n2번째 버스" + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,"대기인원 수\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.woodicon));
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                }
                            }
                            else if(j == 25)
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스" + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation1.get(j).toString() + "\n2번째 버스" + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation2.get(j).toString(),0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.woodicon));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                            else if(j < 48)
                            {
                                if(listBusstop.contains(j))
                                {
                                    if(DBStationId.contains(AllStationId.get(j)))
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스" + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation1.get(j).toString() + "\n2번째 버스" + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.woodicon));
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                            else
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스" + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation1.get(j).toString() + "\n2번째 버스" + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) + "\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.woodicon));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                        }
                        //버스위치 셋팅
                        for(int i = 0; i < listBusseq.size(); i++)
                        {
                            if(Integer.parseInt(listBusseq.get(i).toString()) - 1 < 25)
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스" + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스" + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,"대기인원 수\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.woodicon));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스" + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스" + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,"대기인원 수\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.woodicon));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                }

                            }
                            else if(Integer.parseInt(listBusseq.get(i).toString()) - 1 == 25)
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스" + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스" + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.woodicon));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스" + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스" + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.woodicon));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }

                            }
                            else
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스" + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스" + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.woodicon));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스" + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스" + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기인원 수\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.woodicon));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }

                            }
                        }

                    }

                });
            }
        }).start();
    }

    //새로고침 버튼 메서드
    public void refresh(View view)
    {
        liststation1.clear();
        liststation2.clear();
        listBusseq.clear();
        liststationId.clear();
        listseatCnt.clear();
        AllStationId.clear();
        DBSeatcnt1.clear();
        DBSeatcnt2.clear();

        init();
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
    }

    //rest api 호출
    private void getLineData()
    {
        result = null;
        try
        {
            Log.d(TAG, "AWS rest api 호출: " + AWSendPoint);
            //rest api 에 연결
            URL url = new URL(AWSendPoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream is = conn.getInputStream();

            StringBuilder builder = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            String line;
            while((line = reader.readLine()) != null)
            {
                builder.append(line);
            }

            //결과 출력
            result = builder.toString();

        }catch(Exception e){
            Log.e("REST_API", "GET method failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //DB 에서 받은 JSON 데이터를 ArrayList 에 파싱하여 저장
    private void JSONParser()
    {

        DBStationId = new ArrayList();
        DBLineCnt = new ArrayList();
        DBStaOrder = new ArrayList();
        try
        {
            JSONObject jObject = new JSONObject(result);
            JSONArray jarray = jObject.getJSONArray("Items");
            for(int i = 0; i < jarray.length(); i++)
            {
                JSONObject obj = jarray.getJSONObject(i);
                if(Integer.parseInt(obj.getString("ts")) == 8100 || Integer.parseInt(obj.getString("ts")) == 81004102)
                {
                    DBStationId.add(obj.getString("StationId"));
                    DBLineCnt.add(obj.getString("Detect_Number(People)"));
                    DBStaOrder.add(obj.getString("staorder"));
                }
            }

            Log.d(TAG, "JSON Parsing: " + DBStationId + " " + DBLineCnt + " " + DBStaOrder);
        }catch(JSONException e){e.printStackTrace();}
    }

    //대기인원 수 계산
    private void DataCalculate()
    {
        Log.d(TAG, "버스도착정보항목조회 : " + DBStationId.size() + "번 호출");

        int first = 0, second = 0;
        CalculData = new ArrayList();
        CalculData2 = new ArrayList();
        CalStaOrder = new ArrayList();
        CalStaOrder.clear();
        CalculData.clear();
        CalculData2.clear();
        DBSeatcnt1.clear();
        DBSeatcnt2.clear();

        //for 문을 돌리기 위해 강제로 각각 리스트 인덱스에 빈 값을 추가
        for(int i = 0; i < DBStationId.size(); i++)
        {
            CalculData2.add("");
            CalculData.add("");
            DBSeatcnt1.add(-1);
            DBSeatcnt2.add(-1);
        }

        for(int i = 0; i < DBStationId.size(); i++)
        {
            getBusArrivalItem(DBStationId.get(i).toString(), DBStaOrder.get(i).toString(), i);
            CalStaOrder.add(DBStaOrder.get(i));
            if(Integer.parseInt(DBSeatcnt1.get(i).toString()) == -1  || Integer.parseInt(DBSeatcnt2.get(i).toString()) == -1)
            {
                CalculData.set(i,"");
                CalculData2.set(i,"");
            }
            else
            {
                if(Integer.parseInt(DBSeatcnt1.get(i).toString()) - Integer.parseInt(DBLineCnt.get(i).toString()) >= 0)
                {
                    CalculData.set(i,"(모든인원 탑승가능)");
                }
                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) - Integer.parseInt(DBLineCnt.get(i).toString()) < 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + Integer.parseInt(DBSeatcnt1.get(i).toString()) - Integer.parseInt(DBLineCnt.get(i).toString()) >= 0)
                {
                    first = Integer.parseInt(DBSeatcnt1.get(i).toString());   //첫번째 버스 탑승가능인원 수
                    CalculData.set(i,"(" + first + "명 탑승가능)");
                    CalculData2.set(i,"(나머지인원 탑승가능)");
                }
                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) - Integer.parseInt(DBLineCnt.get(i).toString()) < 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + Integer.parseInt(DBSeatcnt1.get(i).toString()) - Integer.parseInt(DBLineCnt.get(i).toString()) < 0)
                {
                    first = Integer.parseInt(DBSeatcnt1.get(i).toString());   //첫번째 버스 탑승가능인원 수
                    second = Integer.parseInt(DBSeatcnt2.get(i).toString());    //두번째 버스 탑승가능인원 수
                    CalculData.set(i,"(" + first + "명 탑승가능)");
                    CalculData2.set(i,"(나머지" + second + "명 탑승가능)");
                }
            }
        }

        Log.d(TAG,"대기인원 계산" + CalculData + " " + CalculData2 + DBSeatcnt1 + DBSeatcnt2);
    }

    //오퍼레이션 3 (버스도착정보항목조회)
    private void getArrInfoByRouteAllList()
    {
        String stationUrl = endPoint3 + "?ServiceKey=" + key1 + "&busRouteId=" + route;
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
                        if(tag.equals("itemList"));  //첫번째 검색 결과
                        else if(tag.equals("stId"))
                        {
                            xpp.next();
                            AllStationId.add(xpp.getText());
                        }
                        else if(tag.equals("arrmsg1")) //첫번째 버스의 도착정보 메세지
                        {
                            xpp.next();
                            liststation1.add(xpp.getText());
                        }
                        else if(tag.equals("arrmsg2")) //두번째 버스의 도착정보 메세지
                        {
                            xpp.next();
                            liststation2.add(xpp.getText());
                        }
                        break;
                    case XmlPullParser.TEXT:            //xml 문서의 텍스트 만날시
                        break;
                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if(tag.equals("itemList")); //첫번째 검색결과 종료
                        break;
                }
                eventType = xpp.next();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    //오퍼레이션 2 (버스도착정보항목조회)
    private void getBusArrivalItem(String station, String staorder, int count)
    {
        String stationUrl = endPoint1 + "?serviceKey=" + key1 + "&stationId=" + station + "&routeId=" + route + "&staOrder=" + staorder;
        //Log.d(TAG, "버스도착정보항목조회 : " + stationUrl);

        String s = null;
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
                        else if(tag.equals("remainSeatCnt1"))
                        {
                            xpp.next();
                            DBSeatcnt1.set(count,xpp.getText());
                        }
//                        else if (tag.equals("predictTime2"))
//                        {
//                            xpp.next();
//                            s = xpp.getText();
//                            if(s == null)
//                            {
//                                DBSeatcnt2.set(count, -1);
//                                continue;
//                            }
//                        }
                        else if(tag.equals("remainSeatCnt2"))
                        {
                            xpp.next();
                            DBSeatcnt2.set(count,xpp.getText());
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

        Log.d(TAG, "" + s);
    }

    //오퍼레이션 1 (버스위치정보목록조회)
    private void getBusLocationList()
    {
        String stationUrl = endPoint2 + "?serviceKey=" + key1 + "&routeId=" + route;
        Log.d(TAG, "버스위치정보목록 조회 : " + stationUrl);

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
                            listBusseq.add(xpp.getText()); //정류소 순번
                        }
                        else if(tag.equals("stationId"))
                        {
                            xpp.next();
                            liststationId.add(xpp.getText()); //정류소 ID
                        }
                        else if(tag.equals("remainSeatCnt"))
                        {
                            xpp.next();
                            listseatCnt.add(xpp.getText()); //남은 좌석수
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
