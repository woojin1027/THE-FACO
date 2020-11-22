package com.thefacoteam.thefaco.busBtn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.thefacoteam.thefaco.Bus_items;
import com.thefacoteam.thefaco.BusitemAdapter3;
import com.thefacoteam.thefaco.R;

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

//8100번 버스의 자바 파일
public class btnEvent_3007 extends AppCompatActivity {

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key1 = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint1 = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice"; //버스도착정보목록조회 앞 주소
    private final String endPoint2 = "http://openapi.gbis.go.kr/ws/rest/buslocationservice"; // 버스위치정보목록조회 앞 주소
    private final String endPoint3 = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"; //버스도착정보조회 앞 주소
    private final String AWSendPoint = "https://nime89vfc5.execute-api.ap-northeast-2.amazonaws.com/prod/ride"; //AWS api gateway 의 엔드포인트
    private final String route = "200000110";

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;
    private String result; //rest api 호출한 값 담는 변수

    private ArrayList<String> listBus;
    private ArrayList<String> liststation1;
    private ArrayList<String> liststation2;
    private ArrayList<String> listBusseq;
    private ArrayList<String> liststationId;
    private ArrayList<String> listseatCnt;
    private ArrayList<Integer> listBusstop;
    private ArrayList<String> AllStationId;
    private ArrayList<String> AllStationNm;
    private ArrayList<String> DBStationId;
    private ArrayList<String> DBLineCnt;
    private ArrayList<String> DBStaOrder;
    private ArrayList<java.io.Serializable> DBSeatcnt1;
    private ArrayList<java.io.Serializable> DBSeatcnt2;
    private ArrayList<java.io.Serializable> Buslocation1 = new ArrayList<java.io.Serializable>();
    private ArrayList<java.io.Serializable> Buslocation2 = new ArrayList<>();

    private ArrayList<Integer> CalStaOrder;
    private ArrayList<String> CalculData;
    private ArrayList<String> CalculData2;

    private ArrayList<String> examine = new ArrayList<>();

    BusitemAdapter3 adapter = new BusitemAdapter3();

    private int []BusstopArr = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,30,31,32,33,37,38,39,40,41,42,43,50,
            51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69};
    private String [] Buslist = new String[]{"수원버스터미널", "수원버스터미널", "수원아이파크시티.선일초교", "우남아파트",
            "평생교육학습관.남수원중학교", "경기소방본부", "임광아파트", "매탄위브하늘채.경기아트센터", "자유총연맹", "삼성1차아파트",
            "삼성2차아파트", "구법원사거리", "아주대학교입구", "아주대.아주대학교병원", "효성초등학교", "수원월드컵경기장.동성중학교",
            "여권민원실.풍림아파트", "우만동4단지", "경기남부지방경찰청.봉녕사입구", "경기대수원캠퍼스후문.수원박물관" , "동수원TG(미정차)",
            "광교(미정차)", "신갈JC(미정차)", "죽전(미정차)", "서울TG(미정차)", "판교IC(미정차)", "금토JC(미정차)", "서울진입(미정차)",
            "청계산입구역(미정차)", "염곡IC(미정차)", "시민의숲.양재꽃시장", "양재역.서초문화예술회관", "뱅뱅사거리", "래미안아파트.파이낸셜뉴스",
            "강남역.역삼세무서", "역삼역교차로(미정차)", "국기원입구교차로(미정차)", "강남역나라빌딩앞", "래미안아파트.파이낸셜뉴스", "서초문화예술정보학교",
            "양재역커피빈앞", "시민의숲.양재꽃시장", "서초포레스타입구", "내곡IC(미정차)", "헌릉IC(미정차)", "고등IC(미정차)", "서판교IC(미정차)",
            "서분당IC(미정차)", "서수지TG(미정차)", "광교역사공원.광교카페거리", "경기대수원캠퍼스후문.수원박물관", "경기남부지방경찰청.봉녕사입구",
            "연무동", "수원시평생학습관.우만주공아파트", "여권민원실.풍림아파트", "수원월드컵경기장.동성중학교", "효성초등학교", "아주대.아주대학교병원",
            "아주대학교입구", "구법원사거리", "매탄주공4단지.매탄1동주민센터", "자유총연맹", "경기아트센터.매탄위브하늘채", "임광아파트.야외음악당",
            "경기소방본부", "평생교육학습관.남수원중학교.온수골", "대원아파트", "선일초등학교", "이마트", "수원버스터미널", "수원버스터미널"};

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setTitle("3007");
        setContentView(R.layout.activity_bus3);

        liststation1 = new ArrayList<>();
        liststation2 = new ArrayList<String>();
        listBusseq = new ArrayList<>();
        liststationId = new ArrayList<String>();
        listseatCnt = new ArrayList<String>();
        listBus = new ArrayList<String>();
        listBusstop = new ArrayList<>();
        AllStationId = new ArrayList<String>();
        DBSeatcnt1 = new ArrayList<java.io.Serializable>();
        DBSeatcnt2 = new ArrayList<>();
        AllStationNm = new ArrayList<String>();

        for(int i = 0; i < BusstopArr.length; i++)
        {
            listBusstop.add(BusstopArr[i]);
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        for(String temp:Buslist){
            listBus.add(temp);
        }

        //리사이클러뷰 셋팅
        RecyclerViewSet();

        liststation1.clear();
        liststation2.clear();
        listBusseq.clear();
        liststationId.clear();
        listseatCnt.clear();
        AllStationNm.clear();
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


    //리사이클러뷰 셋팅
    public void RecyclerViewSet()
    {
        for(int i = 0; i < listBus.size(); i++)
        {
            if(i == 0)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,null,0));
            }
            else if(i < 34)
            {
                if(listBusstop.contains(i))
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,null,0));
                }
                else
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                }
            }
            else if(i == 34)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
            }
            else if(i < 70)
            {
                if (listBusstop.contains(i))
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                }
                else
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                }
            }
            else
            {
                adapter.addItem(new Bus_items("" + listBus.get(i),null,null,null,0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
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

                Log.d(TAG,""+AllStationNm);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        //버스 리셋
                        for(int j = 0; j < listBus.size(); j++)
                        {
                            if(j == 0)
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j) + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),null,0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                }
                            }
                            else if(j < 34)
                            {
                                if(listBusstop.contains(j))
                                {
                                    if(DBStationId.contains(AllStationId.get(j)))
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j) + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),null,0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                }
                            }
                            else if(j == 34)
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null, "•" + liststation1.get(j) + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j) + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))) ,null,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                            else if(j < 69)
                            {
                                if(listBusstop.contains(j))
                                {
                                    if(DBStationId.contains(AllStationId.get(j)))
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j),null, "•" + liststation1.get(j) + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j) + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),null,0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                            else
                            {
                                if(DBStationId.contains(AllStationId.get(j)))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null, "•" + liststation1.get(j) + " " + CalculData.get(DBStationId.indexOf(AllStationId.get(j))) + "\n\n" + "•" + liststation2.get(j) + " " + CalculData2.get(DBStationId.indexOf(AllStationId.get(j))),null,0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStationId.indexOf(AllStationId.get(j))),R.drawable.lineinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j),null,"•" + liststation1.get(j) + "\n\n" + "•" + liststation2.get(j),null,0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                }
                            }
                        }
                        //버스위치 셋팅
                        for(int i = 0; i < listBusseq.size(); i++)
                        {
                            if(Integer.parseInt(listBusseq.get(i)) - 1 < 34)
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i)) < 10)
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),null,  "•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),"" + listseatCnt.get(i) + "석",R.drawable.busicon4,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석", "•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),null,R.drawable.busicon4,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i)) < 10)
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),null,"•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석",R.drawable.busicon4,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1),null,R.drawable.busicon4,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox,null,0));
                                    }
                                }

                            }
                            else if(Integer.parseInt(listBusseq.get(i)) - 1 == 34)
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i)) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),null,"•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),"" + listseatCnt.get(i) + "석",R.drawable.busicon4,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),null,R.drawable.busicon4,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i)) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),null,"•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석",R.drawable.busicon4,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1),null,R.drawable.busicon4,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                }

                            }
                            else
                            {
                                if(DBStaOrder.contains(listBusseq.get(i)))
                                {
                                    if(Integer.parseInt(listseatCnt.get(i)) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),null, "•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),"" + listseatCnt.get(i) + "석",R.drawable.busicon4,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석", "•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData.get(DBStaOrder.indexOf(listBusseq.get(i))) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1) + " " + CalculData2.get(DBStaOrder.indexOf(listBusseq.get(i))),null,R.drawable.busicon4,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,"대기현황\n\n" + DBLineCnt.get(DBStaOrder.indexOf(listBusseq.get(i))),R.drawable.lineinfobox2));
                                    }
                                }
                                else
                                {
                                    if(Integer.parseInt(listseatCnt.get(i)) < 10)
                                    {

                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),null,"•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석",R.drawable.busicon4,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
                                    }
                                    else
                                    {
                                        adapter.setItem(Integer.parseInt(listBusseq.get(i)) - 1,new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i)) - 1),"" + listseatCnt.get(i) + "석","•" + liststation1.get(Integer.parseInt(listBusseq.get(i)) - 1) + "\n\n" + "•" + liststation2.get(Integer.parseInt(listBusseq.get(i)) - 1),null,R.drawable.busicon4,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2,null,0));
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

        DBStationId = new ArrayList<>();
        DBLineCnt = new ArrayList<>();
        DBStaOrder = new ArrayList<>();
        try
        {
            JSONObject jObject = new JSONObject(result);
            JSONArray jarray = jObject.getJSONArray("Items");
            for(int i = 0; i < jarray.length(); i++)
            {
                JSONObject obj = jarray.getJSONObject(i);
                if(Integer.parseInt(obj.getString("BusNumber")) == 3007)
                {
                    DBStationId.add(obj.getString("StationId"));
                    DBLineCnt.add(obj.getString("Detect_Number(People)"));
                    DBStaOrder.add(obj.getString("Staorder"));
                }
            }

            Log.d(TAG, "JSON Parsing: " + DBStationId + " " + DBLineCnt + " " + DBStaOrder);
        }catch(JSONException e){e.printStackTrace();}
    }

    //대기인원 수 계산
    private void DataCalculate()
    {
        Log.d(TAG, "버스도착정보항목조회 : " + DBStationId.size() + "번 호출");

        CalculData = new ArrayList<String>();
        CalculData2 = new ArrayList<String>();
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
            CalStaOrder.add(Integer.parseInt(DBStaOrder.get(i)));
        }
        Collections.sort(CalStaOrder);

        //1번째 2번째 버스 위치 셋팅
        for(int j = 0; j < DBStationId.size(); j++)
        {
            getBusArrivalItem(DBStationId.get(j), DBStaOrder.get(j), j);

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
                Buslocation1.set(j, Integer.parseInt(DBStaOrder.get(j)) - Integer.parseInt(Buslocation1.get(j).toString()));
                Buslocation2.set(j, -1);
            }
            else if(Buslocation1.get(j) != null && Buslocation2.get(j) != null)
            {
                Buslocation1.set(j, Integer.parseInt(DBStaOrder.get(j)) - Integer.parseInt(Buslocation1.get(j).toString()));
                Buslocation2.set(j, Integer.parseInt(DBStaOrder.get(j)) - Integer.parseInt(Buslocation2.get(j).toString()));
            }
        }

        //대기인원 계산
        for(int i = 0; i < DBStationId.size(); i++)
        {
            if(DBSeatcnt1.get(i).equals(-1) && DBSeatcnt2.get(i).equals(-1))
            {
                CalculData.set(i,"");
                CalculData2.set(i,"");
            }
            else if(!DBSeatcnt1.get(i).equals(-1) && DBSeatcnt2.get(i).equals(-1))
            {
                CalculData.set(i,"");
                CalculData2.set(i,"");
                int first1 = 0, firstinfo1 = 0;
                int first2 = 0, firstinfo2 = 0;
                for(int j = 0; j < DBStationId.size() ; j++)
                {
                    //상행선 일때
                    if(Integer.parseInt(DBStaOrder.get(i)) < 35 && Integer.parseInt(DBStaOrder.get(j)) < 35)
                    {
                        if(Integer.parseInt(DBStaOrder.get(i)) >= Integer.parseInt(DBStaOrder.get(j)))
                        {
                            //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스가 같은 버스일 때
                            if(Buslocation1.get(i) == Buslocation1.get(j))
                            {
                                //대기인원 수와 좌석수 뺄셈 계산
                                if(Integer.parseInt(DBStaOrder.get(i)) > Integer.parseInt(DBStaOrder.get(j)))
                                {
                                    first1 = first1 - Integer.parseInt(DBLineCnt.get(j));
                                }
                                //조건문 돌리기
                                if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 >= Integer.parseInt(DBLineCnt.get(i)))
                                {
                                    CalculData.set(i, "(전부 탑승가능)");
                                } else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0)
                                {
                                    firstinfo1 = first1 + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                    CalculData.set(i, "(" + firstinfo1 + "명 탑승가능)");
                                } else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 <= 0)
                                {
                                    CalculData.set(i, "(탑승불가)");
                                }
                            }
                        }
                    }
                    else if(Integer.parseInt(DBStaOrder.get(i)) == 35)
                    {
                        CalculData.set(i,"");
                        CalculData2.set(i,"");
                    }
                    //하행선 일때
                    else
                    {
                        if(Integer.parseInt(DBStaOrder.get(i)) >= Integer.parseInt(DBStaOrder.get(j)))
                        {
                            //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스가 같은 버스일 때
                            if(Buslocation1.get(i) == Buslocation1.get(j))
                            {
                                //대기인원 수와 좌석수 뺄셈 계산
                                if(Integer.parseInt(DBStaOrder.get(i)) > Integer.parseInt(DBStaOrder.get(j)))
                                {
                                    first2 = first2 - Integer.parseInt(DBLineCnt.get(j));
                                }
                                //조건문 돌리기
                                if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 >= Integer.parseInt(DBLineCnt.get(i)))
                                {
                                    CalculData.set(i, "(전부 탑승가능)");
                                }
                                else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 > 0)
                                {
                                    firstinfo2 = first2 + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                    CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                }
                                else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 <= 0)
                                {
                                    CalculData.set(i, "(탑승불가)");
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                int first1 = 0, second1 = 0, firstinfo1 = 0, secondinfo1 = 0;
                int first2 = 0, second2 = 0, firstinfo2 = 0, secondinfo2 = 0;
                for(int j = 0; j < DBStationId.size(); j++)
                {
                    //상행선 일때
                    if(Integer.parseInt(DBStaOrder.get(i)) < 35 && Integer.parseInt(DBStaOrder.get(j)) < 35)
                    {
                        if(Integer.parseInt(DBStaOrder.get(i)) >= Integer.parseInt(DBStaOrder.get(j)))
                        {
                            //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스가 같은 버스일 때
                            if (Buslocation1.get(i) == Buslocation1.get(j) || Buslocation2.get(i) == Buslocation2.get(j))
                            {
                                //대기인원 수와 좌석수 뺄셈 계산
                                if(Integer.parseInt(DBStaOrder.get(i)) > Integer.parseInt(DBStaOrder.get(j)))
                                {
                                    first1 = first1 - Integer.parseInt(DBLineCnt.get(j));
                                }
                                //조건문 돌리기
                                if(j <= DBStationId.size() - 1)
                                {
                                    if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 >= Integer.parseInt(DBLineCnt.get(i)))
                                    {
                                        CalculData.set(i,"(전부 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBLineCnt.get(i)) - (first1 + Integer.parseInt(DBSeatcnt1.get(i).toString())) <= Integer.parseInt(DBSeatcnt2.get(i).toString()))
                                    {
                                        firstinfo1 = first1 + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                        CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                        CalculData2.set(i,"(그외 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 <= 0 && Integer.parseInt(DBLineCnt.get(i)) - (first1 + Integer.parseInt(DBSeatcnt1.get(i).toString())) > Integer.parseInt(DBSeatcnt2.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 > 0)
                                    {
                                        secondinfo1 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1;
                                        CalculData.set(i,"(탑승불가)");
                                        CalculData2.set(i,"(" + secondinfo1 + "명 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBLineCnt.get(i)) - (first1 + Integer.parseInt(DBSeatcnt1.get(i).toString())) > Integer.parseInt(DBSeatcnt2.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 > 0)
                                    {
                                        firstinfo1 = first1 + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                        secondinfo1 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1;
                                        CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                        CalculData2.set(i,"(" + secondinfo1 + "명 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 <= 0)
                                    {
                                        CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                        CalculData2.set(i,"(탑승불가)");
                                    }
                                    else if(first1 + Integer.parseInt(DBSeatcnt1.get(i).toString()) <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 <= 0)
                                    {
                                        CalculData.set(i,"(탑승불가)");
                                        CalculData2.set(i,"(탑승불가)");
                                    }
                                }
                            }
                            //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스와 두번째 오늘 버스가 같은 버스일 때
                            else if (Buslocation2.get(i) == Buslocation1.get(j))
                            {
                                //대기인원 수와 좌석수 뺄셈 계산
                                if(Integer.parseInt(DBStaOrder.get(i)) > Integer.parseInt(DBStaOrder.get(j)) && Integer.parseInt(DBStaOrder.get(j)) > Integer.parseInt(Buslocation2.get(i).toString()))
                                {
                                    second1 = second1 - Integer.parseInt(DBLineCnt.get(j));
                                }
                                //조건문 돌리기
                                if(j <= DBStationId.size() - 1)
                                {
                                    if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1  >= Integer.parseInt(DBLineCnt.get(i)))
                                    {
                                        CalculData.set(i,"(전부 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1  >= Integer.parseInt(DBLineCnt.get(i)) - Integer.parseInt(DBSeatcnt1.get(i).toString()))
                                    {
                                        firstinfo1 = Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1;
                                        CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                        CalculData2.set(i,"(그외 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1  < Integer.parseInt(DBLineCnt.get(i)) - Integer.parseInt(DBSeatcnt1.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 > 0)
                                    {
                                        secondinfo1 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1;
                                        CalculData.set(i,"(탑승불가)");
                                        CalculData2.set(i,"(" + secondinfo1 + "명 탑승가능");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1  >= Integer.parseInt(DBLineCnt.get(i)))
                                    {
                                        CalculData.set(i,"(탑승불가)");
                                        CalculData2.set(i,"(전부 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1  <= 0)
                                    {
                                        firstinfo1 = Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1;
                                        CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                        CalculData2.set(i,"(탑승불가)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1  < Integer.parseInt(DBLineCnt.get(i)) - Integer.parseInt(DBSeatcnt1.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 > 0)
                                    {
                                        firstinfo1 = Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1;
                                        secondinfo1 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1;
                                        CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                        CalculData2.set(i,"(" + secondinfo1 + "명 탑승가능)");
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1  <= 0)
                                    {
                                        CalculData.set(i,"(탑승불가)");
                                        CalculData2.set(i,"(탑승불가)");
                                    }
                                }
                            }
                        }
                    }
                    else if(Integer.parseInt(DBStaOrder.get(i)) == 35)
                    {
                        CalculData.set(i,"");
                        CalculData2.set(i,"");
                    }
                    //하행선 일때
                    else {
                        if (Integer.parseInt(DBStaOrder.get(i)) >= Integer.parseInt(DBStaOrder.get(j)))
                        {
                            //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스가 같은 버스일 때
                            if (Buslocation1.get(i) == Buslocation1.get(j) || Buslocation2.get(i) == Buslocation2.get(j))
                            {
                                //대기인원 수와 좌석수 뺄셈 계산
                                if (Integer.parseInt(DBStaOrder.get(i)) > Integer.parseInt(DBStaOrder.get(j)))
                                {
                                    first2 = first2 - Integer.parseInt(DBLineCnt.get(j));
                                }
                                //조건문 돌리기
                                if (j <= DBStationId.size() - 1)
                                {
                                    if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 >= Integer.parseInt(DBLineCnt.get(i)))
                                    {
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(전부 탑승가능)");
                                        }
                                    }
                                    else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 > 0 && Integer.parseInt(DBLineCnt.get(i)) - (first2 + Integer.parseInt(DBSeatcnt1.get(i).toString())) <= Integer.parseInt(DBSeatcnt2.get(i).toString()))
                                    {
                                        firstinfo2 = first2 + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i, "");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i, "(그외 탑승가능)");
                                        }
                                    }
                                    else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 <= 0 && Integer.parseInt(DBLineCnt.get(i)) - (first2 + Integer.parseInt(DBSeatcnt1.get(i).toString())) > Integer.parseInt(DBSeatcnt2.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 > 0)
                                    {
                                        secondinfo2 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2;
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i, "");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i, "(" + secondinfo2 + "명 탑승가능)");
                                        }
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 > 0 && Integer.parseInt(DBLineCnt.get(i)) - (first2 + Integer.parseInt(DBSeatcnt1.get(i).toString())) > Integer.parseInt(DBSeatcnt2.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 > 0)
                                    {
                                        firstinfo2 = first2 + Integer.parseInt(DBSeatcnt1.get(i).toString());
                                        secondinfo2 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2;
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i,"(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i, "");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i,"(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i,"(" + secondinfo2 + "명 탑승가능)");
                                        }
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first1 > 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 <= 0)
                                    {

                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                            CalculData2.set(i, "");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i,"(" + firstinfo1 + "명 탑승가능)");
                                            CalculData2.set(i,"(탑승불가)");
                                        }
                                    }
                                    else if(first1 + Integer.parseInt(DBSeatcnt1.get(i).toString()) <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second1 <= 0)
                                    {
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i,"(탑승불가)");
                                            CalculData2.set(i, "");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i,"(탑승불가)");
                                            CalculData2.set(i,"(탑승불가)");
                                        }
                                    }
                                }
                            }
                            //대기인원이 있는 정류장들의 각각의 첫번째 오는 버스와 두번째 오늘 버스가 같은 버스일 때
                            else if (Buslocation2.get(i) == Buslocation1.get(j))
                            {
                                //대기인원 수와 좌석수 뺄셈 계산
                                if (Integer.parseInt(DBStaOrder.get(i)) > Integer.parseInt(DBStaOrder.get(j)) && Integer.parseInt(DBStaOrder.get(j)) > Integer.parseInt(Buslocation2.get(i).toString()))
                                {
                                    second2 = second2 - Integer.parseInt(DBLineCnt.get(j));
                                }
                                //조건문 돌리기
                                if (j <= DBStationId.size() - 1)
                                {
                                    if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 >= Integer.parseInt(DBLineCnt.get(i)))
                                    {
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(전부 탑승가능)");
                                        }
                                    }
                                    else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 >= Integer.parseInt(DBLineCnt.get(i)) - Integer.parseInt(DBSeatcnt1.get(i).toString()))
                                    {
                                        firstinfo2 = Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2;
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i,"");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i, "(그외 탑승가능)");
                                        }
                                    }
                                    else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 < Integer.parseInt(DBLineCnt.get(i)) - Integer.parseInt(DBSeatcnt1.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 > 0)
                                    {
                                        secondinfo2 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2;
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i,"");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i, "(" + secondinfo2 + "명 탑승가능");
                                        }
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2  >= Integer.parseInt(DBLineCnt.get(i)))
                                    {
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i,"(탑승불가)");
                                            CalculData2.set(i,"");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i,"(전부 탑승가능)");
                                        }
                                    }
                                    else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 <= 0)
                                    {
                                        firstinfo2 = Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2;
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i,"");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i, "(탑승불가)");
                                        }
                                    }
                                    else if (Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 > 0 && Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 < Integer.parseInt(DBLineCnt.get(i)) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 < Integer.parseInt(DBLineCnt.get(i)) - Integer.parseInt(DBSeatcnt1.get(i).toString()) && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2 > 0)
                                    {
                                        firstinfo2 = Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2;
                                        secondinfo2 = Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2;
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i,"");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(" + firstinfo2 + "명 탑승가능)");
                                            CalculData2.set(i, "(" + secondinfo2 + "명 탑승가능)");
                                        }
                                    }
                                    else if(Integer.parseInt(DBSeatcnt1.get(i).toString()) + first2 <= 0 && Integer.parseInt(DBSeatcnt2.get(i).toString()) + second2  <= 0)
                                    {
                                        if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt1.get(i)))) >= 35 && Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) < 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i,"");
                                        }
                                        else if(Integer.parseInt(listBusseq.get(listseatCnt.indexOf(DBSeatcnt2.get(i)))) >= 35)
                                        {
                                            CalculData.set(i, "(탑승불가)");
                                            CalculData2.set(i, "(탑승불가)");
                                        }
                                    }
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
            if(liststation1.get(i).equals("출발대기") || liststation1.get(i).equals("곧 도착") || liststation1.get(i).equals("운행종료"))
            {
                liststation1.set(i, liststation1.get(i));
            }
            else
            {
                int indx1 = 0;
                indx1 = liststation1.get(i).indexOf("분");
                liststation1.set(i, liststation1.get(i).substring(0,indx1+1));
            }

            if(liststation2.get(i).equals("출발대기") || liststation2.get(i).equals("곧 도착") || liststation2.get(i).equals("운행종료"))
            {
                liststation2.set(i, liststation2.get(i));
            }
            else
            {
                int indx2 = 0;
                indx2 = liststation2.get(i).indexOf("분");
                liststation2.set(i, liststation2.get(i).substring(0,indx2+1));
            }
        }
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
                        else if(tag.equals("stNm"))
                        {
                            xpp.next();
                            AllStationNm.add(xpp.getText()); //모든 버스정류장 이름 담아오기
                        }
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
