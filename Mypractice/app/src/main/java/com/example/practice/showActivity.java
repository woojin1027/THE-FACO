package com.example.practice;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;

//M4102 번 버스의 자바 파일
public class showActivity extends AppCompatActivity
{

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key1 = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint1 = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice"; //버스도착정보목록조회 앞 주소
    private final String endPoint2 = "http://openapi.gbis.go.kr/ws/rest/buslocationservice"; //버스위치정보조회서비스 앞 주소
    private final String endPoint3 = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"; //버스도착정보조회 앞 주소
    private final String AWSendPoint = " https://w5yp3bwer4.execute-api.ap-northeast-2.amazonaws.com/project/projectFunction"; //AWS api gateway 의 엔드포인트
    private final String route = "234001159";

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
    private ArrayList Buslocation1 = new ArrayList();
    private ArrayList Buslocation2 = new ArrayList();

    private ArrayList CalStaOrder;
    private ArrayList CalculData;
    private ArrayList CalculData2;

    private ArrayList examine = new ArrayList();

    BusitemAdapter adapter = new BusitemAdapter();

    int []BusstopArr = new int[]{2,4,5,16,17,18,19,22,23,24,35,36,37};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle("M4102");
        setContentView(R.layout.activity_show);

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

        listBus.add("미금역.청솔마을.2001아울렛");
        listBus.add("불정교사거리(미정차)");
        listBus.add("정자역");
        listBus.add("백궁삼거리(미정차)");
        listBus.add("서현역.AK플라자");
        listBus.add("이매촌한신.서현역.AK프라자");
        listBus.add("판교TG(미정차)");
        listBus.add("금토JC(미정차)");
        listBus.add("서울진입(미정차)");
        listBus.add("청계산입구역(미정차)");
        listBus.add("양재IC(미정차)");
        listBus.add("서초IC(미정차)");
        listBus.add("반포IC(미정차)");
        listBus.add("한남IC(미정차)");
        listBus.add("한남1고가차도(미정차)");
        listBus.add("남산1호터널TG(미정차)");
        listBus.add("남대문세무서.국가인권위원회");
        listBus.add("종로2가사거리(중)");
        listBus.add("을지로입구역.광교");
        listBus.add("북창동.남대문시장");
        listBus.add("서울역교차로(미정차)");
        listBus.add("숭례문");
        listBus.add("남대문시장앞.이회영활동터");
        listBus.add("명동국민은행앞");
        listBus.add("남대문세무서.서울백병원(중)");
        listBus.add("남산1호터널TG(미정차)");
        listBus.add("한남1고가차도(미정차)");
        listBus.add("한남IC(미정차)");
        listBus.add("반포IC(미정차)");
        listBus.add("서초IC(미정차)");
        listBus.add("양재IC(미정차)");
        listBus.add("청계산입구역(미정차)");
        listBus.add("성남진입(미정차)");
        listBus.add("금토JC(미정차)");
        listBus.add("판교TG(미정차)");
        listBus.add("이매촌한신.서현역.AK프라자");
        listBus.add("서현역.AK플라자");
        listBus.add("정자역");
        listBus.add("미금역.청솔마을.2001아울렛");

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
                liststation1.clear();
                liststation2.clear();
                listBusseq.clear();
                liststationId.clear();
                listseatCnt.clear();
                AllStationId.clear();
                DBSeatcnt1.clear();
                DBSeatcnt2.clear();

                init();
                //2~3번 눌러야 바뀔 확률이 높기 때문에 notifyDataSetchanged() 를 세번 호출
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    //리사이클러뷰 셋팅 메서드
    public void RecyclerViewSet()
    {
        for(int i = 0; i < listBus.size(); i++)
        {
            if(i == 0)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,null,0));
            }
            else if(i < 21)
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
            else if(i == 21)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
            }
            else if(i < 38)
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

                TextSet();

                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, listBusseq + " " + liststationId + " " + liststation1);
                        //버스 리셋
                        for(int j = 0; j < listBus.size(); j++)
                        {
                            if(j == 0)
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j).toString() + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                }
                            }
                            else if(j < 21)
                            {
                                if(listBusstop.contains(j))
                                {
                                    if(DBStationId.contains(AllStationId.get(j)))
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j).toString() + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                }
                            }
                            else if(j == 21)
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null, "•" + liststation1.get(j).toString() + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j).toString() + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) ,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                            else if(j < 38)
                            {
                                if(listBusstop.contains(j))
                                {
                                    if(DBStationId.contains(AllStationId.get(j)))
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null, "•" + liststation1.get(j).toString() + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j).toString() + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                            else
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null, "•" + liststation1.get(j).toString() + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j).toString() + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"•" + liststation1.get(j).toString() + "\n\n" + "•" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                        }
                        //버스위치 셋팅
                        for(int i = 0; i < listBusseq.size(); i++)
                        {
                            if(Integer.parseInt(listBusseq.get(i).toString()) - 1 < 21)
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석",  "•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석", "•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                }

                            }
                            else if(Integer.parseInt(listBusseq.get(i).toString()) - 1 == 21)
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }

                            }
                            else
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석", "•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석", "•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString()) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString() + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString()) - 1).toString(),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }

                            }
                        }

                    }

                });
            }
        }).start();
    }

    //새로고침버튼 메서드
    public void refresh(View view)
    {
        liststation1.clear();
        liststation2.clear();
        listBusseq.clear();
        liststationId.clear();
        listseatCnt.clear();
        AllStationId.clear();

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
            conn.setRequestMethod("GET"); //GET 방식으로 호출
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
                if(Integer.parseInt(obj.getString("BusNumber")) == 4102 || Integer.parseInt(obj.getString("BusNumber")) == 81004102)
                {
                    DBStationId.add(obj.getString("StationId"));
                    DBLineCnt.add(obj.getString("Detect_Number(People)"));
                    DBStaOrder.add(obj.getString("Staorder"));
                }
            }
            DBStaOrder.set(1,3);
            DBStaOrder.set(2,1);

            Log.d(TAG, "JSON Parsing: " + DBStationId + " " + DBLineCnt + " " + DBStaOrder);
        }catch(JSONException e){e.printStackTrace();}
    }

    //대기인원 수 계산
    private void DataCalculate()
    {
        Log.d(TAG, "버스도착정보항목조회 : " + DBStationId.size() + "번 호출");

        CalculData = new ArrayList();
        CalculData2 = new ArrayList();
        CalStaOrder = new ArrayList<>();
        CalStaOrder.clear();
        CalculData.clear();
        CalculData2.clear();
        DBSeatcnt1.clear();
        DBSeatcnt2.clear();
        Buslocation1.clear();
        Buslocation2.clear();
        examine.clear();

        //for 문을 돌리기 위해 강제로 각각 리스트 인덱스에 빈 값을 추가
        for(int i = 0; i < DBStationId.size(); i++)
        {
            examine.add("");
            CalculData2.add("");
            CalculData.add("");
            DBSeatcnt1.add(-1);
            DBSeatcnt2.add(-1);
            Buslocation1.add(-1);
            Buslocation2.add(-1);
            CalStaOrder.add(Integer.parseInt(DBStaOrder.get(i).toString()));
        }
        Collections.sort(CalStaOrder);

        //1번째 2번째 버스 위치 셋팅
        for(int j = 0; j < DBStationId.size(); j++)
        {
            getBusArrivalItem(DBStationId.get(j).toString(), DBStaOrder.get(j).toString(), j);

            //파싱값이 존재하지않을 때 -1 로 채워넣는다
            if(Buslocation1.get(j) == null && Buslocation2.get(j) == null)
            {
                DBSeatcnt2.set(j, -1);
                Buslocation1.set(j, -1);
                Buslocation2.set(j, -1);
            }
            else if (Buslocation1.get(j) != null && Buslocation2.get(j) == null)
            {
                DBSeatcnt2.set(j, -1);
                Buslocation1.set(j, Integer.parseInt(DBStaOrder.get(j).toString()) - Integer.parseInt(Buslocation1.get(j).toString()));
                Buslocation2.set(j, -1);
            }
            else if(Buslocation1.get(j) != null && Buslocation2.get(j) != null)
            {
                Buslocation1.set(j, Integer.parseInt(DBStaOrder.get(j).toString()) - Integer.parseInt(Buslocation1.get(j).toString()));
                Buslocation2.set(j, Integer.parseInt(DBStaOrder.get(j).toString()) - Integer.parseInt(Buslocation2.get(j).toString()));
            }
        }

        for(int i = 0; i < DBStationId.size(); i++)
        {
            if(DBSeatcnt1.get(i).equals(-1) && DBSeatcnt2.get(i).equals(-1))
            {
                CalculData.set(i, "");
                CalculData2.set(i, "");
            }
            else if(DBSeatcnt2.get(i).equals(-1))
            {
                CalculData.set(i,"");
                CalculData2.set(i,"");
                int first = 0, firstinfo = 0;
                for(int j = 0; j < DBStationId.size() ; j++)
                {
                    if(Integer.parseInt(DBStaOrder.get(i).toString()) >= Integer.parseInt(DBStaOrder.get(j).toString()))
                    {
                        //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스가 같은 버스일 때
                        if(Buslocation1.get(i) == Buslocation1.get(j))
                        {
                            //대기인원 수와 좌석수 뺄셈 계산
                            if(Integer.parseInt(DBStaOrder.get(i).toString()) > Integer.parseInt(DBStaOrder.get(j).toString()))
                            {
                                first = first - Integer.parseInt(DBLineCnt.get(j).toString());
                            }
                            //조건문 돌리기
                            if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first >= Integer.parseInt(DBLineCnt.get(i).toString())) {
                                CalculData.set(i, "(모든인원 탑승가능)");
                            } else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first < Integer.parseInt(DBLineCnt.get(i).toString()) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first > 0) {
                                firstinfo = first + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                CalculData.set(i, "(" + firstinfo + "명 탑승가능)");
                            } else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first < Integer.parseInt(DBLineCnt.get(i).toString()) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first <= 0) {
                                CalculData.set(i, "(탑승불가)");
                            }

                        }
                    }
                }
            }
            else
            {
                int first = 0, second = 0, firstinfo = 0, secondinfo = 0;
                for(int j = 0; j < DBStationId.size(); j++)
                {
                    if(Integer.parseInt(DBStaOrder.get(i).toString()) >= Integer.parseInt(DBStaOrder.get(j).toString()))
                    {
                        //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스가 같은 버스일 때
                        if (Buslocation1.get(i) == Buslocation1.get(j) || Buslocation2.get(i) == Buslocation2.get(j))
                        {
                            //대기인원 수와 좌석수 뺄셈 계산
                            if(Integer.parseInt(DBStaOrder.get(i).toString()) > Integer.parseInt(DBStaOrder.get(j).toString()))
                            {
                                first = first - Integer.parseInt(DBLineCnt.get(j).toString());
                            }
                            //조건문 돌리기
                            if(j <= DBStationId.size() - 1)
                            {
                                if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first >= Integer.parseInt(DBLineCnt.get(i).toString()))
                                {
                                    CalculData.set(i,"(모든인원 탑승가능)");
                                }
                                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first < Integer.parseInt(DBLineCnt.get(i).toString()) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first > 0 && Integer.parseInt(DBLineCnt.get(i).toString()) - (first + Integer.parseInt(DBSeatcnt1.get(i).toString())) <= Integer.parseInt(DBSeatcnt2.get(i).toString()))
                                {
                                    firstinfo = first + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                    secondinfo = Integer.parseInt(DBLineCnt.get(i).toString()) - first;
                                    CalculData.set(i,"(" + firstinfo + "명 탑승가능)");
                                    CalculData2.set(i,"(나머지인원 탑승가능)");
                                }
                                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first < Integer.parseInt(DBLineCnt.get(i).toString()) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first <= 0 && Integer.parseInt(DBLineCnt.get(i).toString()) - (first + Integer.parseInt(DBSeatcnt1.get(i).toString())) > Integer.parseInt(DBSeatcnt2.get(i).toString()))
                                {
                                    secondinfo = Integer.parseInt(DBSeatcnt2.get(i).toString());
                                    CalculData.set(i,"(탑승불가)");
                                    CalculData2.set(i,"(" + secondinfo + "명 탑승가능)");
                                }
                            }
                        }
                        //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스와 두번째 오늘 버스가 같은 버스일 때
                        else if (Buslocation2.get(i) == Buslocation1.get(j))
                        {
                            //대기인원 수와 좌석수 뺄셈 계산
                            if(Integer.parseInt(DBStaOrder.get(i).toString()) > Integer.parseInt(DBStaOrder.get(j).toString()) && Integer.parseInt(DBStaOrder.get(j).toString()) > Integer.parseInt(Buslocation2.get(i).toString()))
                            {
                                second = second - Integer.parseInt(DBLineCnt.get(j).toString());
                            }
                            //조건문 돌리기
                            if(j <= DBStationId.size() - 1)
                            {
                                if(Integer.parseInt(DBSeatcnt1.get(i).toString())  >= Integer.parseInt(DBLineCnt.get(i).toString()))
                                {
                                    CalculData.set(i,"(모든인원 탑승가능)");
                                }
                                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) < Integer.parseInt(DBLineCnt.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second  >= Integer.parseInt(DBLineCnt.get(i).toString()) - Integer.parseInt(DBSeatcnt1.get(i).toString()))
                                {
                                    firstinfo = Integer.parseInt(DBSeatcnt1.get(i).toString());
                                    CalculData.set(i,"(" + firstinfo + "명 탑승가능)");
                                    CalculData2.set(i,"(나머지인원 탑승가능)");
                                }
                                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second  < Integer.parseInt(DBLineCnt.get(i).toString()) - Integer.parseInt(DBSeatcnt1.get(i).toString()))
                                {
                                    secondinfo = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second;
                                    CalculData.set(i,"(탑승불가)");
                                    CalculData2.set(i,"(" + secondinfo + "명 탑승가능");
                                }
                                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) > 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second  <= 0)
                                {
                                    firstinfo = Integer.parseInt(DBSeatcnt1.get(i).toString());
                                    CalculData.set(i,"(" + firstinfo + "명 탑승가능)");
                                    CalculData2.set(i,"(탑승불가)");
                                }
                                else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) > 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second  < Integer.parseInt(DBLineCnt.get(i).toString()) - Integer.parseInt(DBSeatcnt1.get(i).toString()))
                                {
                                    firstinfo = Integer.parseInt(DBSeatcnt1.get(i).toString());
                                    secondinfo = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second;
                                    CalculData.set(i,"(" + firstinfo + "명 탑승가능)");
                                    CalculData2.set(i,"(" + secondinfo + "명 탑승가능)");
                                }
                                else
                                {
                                    CalculData.set(i,"(탑승불가)");
                                    CalculData2.set(i,"(탑승불가)");
                                }
                            }

                        }
                    }

                }
            }
        }
        Log.d(TAG,"대기인원 계산" + CalculData + " " + CalculData2 + DBSeatcnt1 + DBSeatcnt2 + Buslocation1 + Buslocation2);
    }

    private void TextSet()
    {
        for(int i = 0; i < liststation1.size(); i++)
        {
            if(liststation1.get(i).toString().equals("출발대기") || liststation1.get(i).toString().equals("곧 도착") || liststation1.get(i).toString().equals("운행종료"))
            {
                liststation1.set(i,liststation1.get(i).toString());
            }
            else
            {
                int indx1 = 0;
                indx1 = liststation1.get(i).toString().indexOf("분");
                liststation1.set(i, liststation1.get(i).toString().substring(0,indx1+1));
            }

            if(liststation2.get(i).toString().equals("출발대기") || liststation2.get(i).toString().equals("곧 도착") || liststation2.get(i).toString().equals("운행종료"))
            {
                liststation2.set(i, liststation2.get(i));
            }
            else
            {
                int indx2 = 0;
                indx2 = liststation2.get(i).toString().indexOf("분");
                liststation2.set(i, liststation2.get(i).toString().substring(0,indx2+1));
            }
        }
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
                        else if(tag.equals("stationSeq")) //정류소 순번
                        {
                            xpp.next();
                            listBusseq.add(xpp.getText());
                        }
                        else if(tag.equals("stationId")) //정류소 ID
                        {
                            xpp.next();
                            liststationId.add(xpp.getText());
                        }
                        else if(tag.equals("remainSeatCnt")) //남은 좌석수
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

    //오퍼레이션 2 (버스도착정보항목조회)
    private void getBusArrivalItem(String station, String staorder, int count)
    {
        String stationUrl = endPoint1 + "?serviceKey=" + key1 + "&stationId=" + station + "&routeId=" + route + "&staOrder=" + staorder;

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
                            Buslocation1.set(count,xpp.getText());
                        }
                        else if(tag.equals("locationNo2"))
                        {
                            xpp.next();
                            Buslocation2.set(count,xpp.getText());
                        }
                        else if(tag.equals("plateNo2"))
                        {
                            xpp.next();
                            examine.set(count,xpp.getText());
                        }
                        else if(tag.equals("remainSeatCnt1"))
                        {
                            xpp.next();
                            DBSeatcnt1.set(count,xpp.getText());
                        }
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
