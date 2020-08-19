package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.Color;
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
    private final String endPoint3 = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRouteAll"; //버스도착정보조회 앞 주소
    private final String route = "234000878";

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    private ArrayList listsample;
    private ArrayList listBus;
    private ArrayList liststation1;
    private ArrayList liststation2;
    private ArrayList listBusseq;
    private ArrayList liststationId;
    private ArrayList listseatCnt;
    private ArrayList listBusstop;

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
        listsample = new ArrayList();
        listBusseq = new ArrayList();
        liststationId = new ArrayList();
        listseatCnt = new ArrayList();
        listBus = new ArrayList();
        listBusstop = new ArrayList();

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


        liststation1.clear();
        liststation2.clear();
        listBusseq.clear();
        liststationId.clear();
        listseatCnt.clear();

        for(int i = 0; i < listBus.size(); i++)
        {
            if(i == 0)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox));
            }
            else if(i < 25)
            {
                if(listBusstop.contains(i))
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox));
                }
                else
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                }
            }
            else if(i == 25)
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2));
            }
            else if(i < 48)
            {
                if (listBusstop.contains(i))
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2));
                }
                else
                {
                    adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                }
            }
            else
            {
                adapter.addItem(new Bus_items("" + listBus.get(i).toString(),null,null,0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2));
            }
        }



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

                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, listBusseq + " " + liststationId + " " + liststation1 + " " + liststation2 + " " + listsample);
                        //버스 리셋
                        for(int j = 0; j < listBus.size(); j++)
                        {
                            if(j == 0)
                            {
                                adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox));
                            }
                            else if(j < 25)
                            {
                                if(listBusstop.contains(j))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                }
                            }
                            else if(j == 25)
                            {
                                adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2));
                            }
                            else if(j < 48)
                            {
                                if(listBusstop.contains(j))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),null,0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                            }
                            else
                            {
                                adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2));
                            }
                        }
                        //버스위치 셋팅
                        for(int i = 0; i < listBusseq.size(); i++)
                        {
                            if(Integer.parseInt(listBusseq.get(i).toString()) < 25)
                            {
                                if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                }
                                else
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                }
                            }
                            else if(Integer.parseInt(listBusseq.get(i).toString()) == 25)
                            {
                                if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                {

                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                            }
                            else
                            {
                                if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                {

                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                            }
                        }

                    }

                });
            }
        }).start();

        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);

        //당겨서 새로고침 구현
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh()
            {
                //준비상태
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        liststation1.clear();
                        liststation2.clear();
                        listBusseq.clear();
                        liststationId.clear();
                        listseatCnt.clear();

                        //오퍼레이션 1  버스위치정보조회
                        getBusLocationList();

                        //오퍼레이션 3 버스도착정보조회
                        getArrInfoByRouteAllList();

                        //UI setText 하는 곳
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run()
                            {
                                Log.d(TAG, listBusseq + " " + liststationId + " " + liststation1 + " " + liststation2);
                                //버스 리셋
                                for(int j = 0; j < listBus.size(); j++)
                                {
                                    if(j == 0)
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox));
                                    }
                                    else if(j < 25)
                                    {
                                        if(listBusstop.contains(j))
                                        {
                                            adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox));
                                        }
                                        else
                                        {
                                            adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                        }
                                    }
                                    else if(j == 25)
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2));
                                    }
                                    else if(j < 48)
                                    {
                                        if(listBusstop.contains(j))
                                        {
                                            adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2));
                                        }
                                        else
                                        {
                                            adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                        }
                                    }
                                    else
                                    {
                                        adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2));
                                    }
                                }
                                //버스위치 셋팅
                                for(int i = 0; i < listBusseq.size(); i++)
                                {
                                    if(Integer.parseInt(listBusseq.get(i).toString()) < 25)
                                    {
                                        if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                        {

                                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                        }
                                        else
                                        {
                                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                        }
                                    }
                                    else if(Integer.parseInt(listBusseq.get(i).toString()) == 25)
                                    {
                                        if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                        {

                                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                        }
                                        else
                                        {
                                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                        }
                                    }
                                    else
                                    {
                                        if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                        {

                                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                        }
                                        else
                                        {
                                            adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                        }
                                    }
                                }
                            }

                        });
                    }
                }).start();

                //2~3번 눌러야 바뀔 확률이 높기 때문에 notifyDataSetchanged() 를 세번 호출
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                adapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    //새로고침 버튼 메서드
    public void refresh(View view)
    {
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

                //오퍼레이션 3 버스도착정보조회
                getArrInfoByRouteAllList();

                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, listBusseq + " " + liststationId + " " + liststation1 + " " + liststation2);
                        //버스 리셋
                        for(int j = 0; j < listBus.size(); j++)
                        {
                            if(j == 0)
                            {
                                adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop3,R.drawable.textrail,R.drawable.textinfobox));
                            }
                            else if(j < 25)
                            {
                                if(listBusstop.contains(j))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,R.drawable.railstop1,R.drawable.textrail,R.drawable.textinfobox));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                }
                            }
                            else if(j == 25)
                            {
                                adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,0,R.drawable.returnrail,R.drawable.returnicon,R.drawable.textrail2,R.drawable.textinfobox2));
                            }
                            else if(j < 48)
                            {
                                if(listBusstop.contains(j))
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop2,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                            }
                            else
                            {
                                adapter.setItem(j,new Bus_items("" + listBus.get(j).toString(),null,"1번째 버스\n" + liststation1.get(j).toString() + "\n2번째 버스\n" + liststation2.get(j).toString(),0,0,0,R.drawable.rail2,0,R.drawable.railstop3,R.drawable.textrail2,R.drawable.textinfobox2));
                            }
                        }
                        //버스위치 셋팅
                        for(int i = 0; i < listBusseq.size(); i++)
                        {
                            if(Integer.parseInt(listBusseq.get(i).toString()) < 25)
                            {
                                if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                {

                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                }
                                else
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,R.drawable.rail1,0,0,0,R.drawable.textrail,R.drawable.textinfobox));
                                }
                            }
                            else if(Integer.parseInt(listBusseq.get(i).toString()) == 25)
                            {
                                if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                {

                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,0,0,R.drawable.returnrail,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                            }
                            else
                            {
                                if(Integer.parseInt(listseatCnt.get(i).toString()) < 10)
                                {

                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote2,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                                else
                                {
                                    adapter.setItem(Integer.parseInt(listBusseq.get(i).toString()),new Bus_items("" + listBus.get(Integer.parseInt(listBusseq.get(i).toString())),"" + listseatCnt.get(i) + "석","1번째 버스\n" + liststation1.get(Integer.parseInt(listBusseq.get(i).toString())).toString() + "\n2번째 버스\n" + liststation2.get(Integer.parseInt(listBusseq.get(i).toString())).toString(),R.drawable.busicon,R.drawable.seatnote,0,R.drawable.rail2,0,0,R.drawable.textrail2,R.drawable.textinfobox2));
                                }
                            }
                        }

                    }

                });
            }
        }).start();
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
        adapter.notifyDataSetChanged();
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
//                        else if(tag.equals("stNm"))
//                        {
//                            xpp.next();
//                            listsample.add(xpp.getText());
//                        }
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
    private void getBusArrivalItem(String station, String staorder)
    {
        String stationUrl = endPoint1 + "?serviceKey=" + key1 + "&stationId=" + station + "&routeId=" + route + "&staOrder=" + staorder;
        Log.d(TAG, "버스도착정보항목조회 : " + stationUrl);

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
                        else if(tag.equals("locationNo2"))
                        {
                            xpp.next();
                            liststation2.add(xpp.getText());
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
