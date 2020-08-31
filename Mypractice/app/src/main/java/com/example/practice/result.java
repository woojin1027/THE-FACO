package com.example.practice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.odsay.odsayandroidsdk.ODsayService;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

//출발지와 도착지의 위도경도를 받아서 예측 소요시간(=이동시간 + 대기시간) 알려주기,,.????


public class result extends AppCompatActivity {

    private final String TAG = "myTag";
    private int Count;
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
    String str_time; //소요시간

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
        textView = findViewById(R.id.textView4);
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

        //textView.setText("출발지정보\n" + db_sx + "\n" + db_sy + "\n도착지정보\n" + db_ex + "\n" + db_ey);
        //init(str_1,str_2,db_sx,db_sy,db_ex,db_ey);

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

//    private void Excel()
//    {
//        Workbook wb = null;
//        Sheet sheet = null;
//        try{
//            InputStream is = getBaseContext().getResources().getAssets().open("GGD_RouteInfo_M");
//            wb = Workbook.getWorkbook(is);
//
//            if(wb != null)
//            {
//                sheet = wb.getSheet(0); //시트 불러오기
//                if(sheet != null)
//                {
//                    int colTotal = sheet.getColumns();  //전체 컬럼
//                    int rowIndexStart = 1;              //row 인텍스 시작
//                    int rowTotal = sheet.getColumn(colTotal-1).length;
//                    int col = 4;
//                    StringBuilder sb;
//                    for(int row = rowIndexStart; row < rowTotal; row++)
//                    {
//                        sb = new StringBuilder();
//                        String contents = sheet.getCell(col, row).getContents();
//
//                    }
//               }
//            }
//
//        }catch (IOException e) {
//            e.printStackTrace();
//        } catch (BiffException e) {
//            e.printStackTrace();
//        }
//    }

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
            Routesetting = "";
            Fnamesetting = "";
            Tnamesetting = "";
            adapter.addItem(new Path_items("","","",0));

            //조건문 달아서 다시 셋팅
            for(int j = 0; j < RouteNm.get(j).size(); j++)
            {
                Routesetting = Routesetting + RouteNm.get(i).get(j) + "\n";
                Fnamesetting = Fnamesetting + Fname.get(i).get(j) + "\n";


                if(j == RouteNm.get(j).size() - 1)
                {
                    adapter.setItem(i,new Path_items("" + Time.get(i).toString() + "분","" + Routesetting,"" + Fnamesetting + "(하차)" + Tname.get(i).get(j),0));
                }
            }
        }
    }
    //오퍼레이션 1 (버스위치정보목록조회)
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


    private void init(final String str_1, final String str_2, final Double db_sx, final Double db_sy, final Double db_ex, final Double db_ey) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(tag, "순서 3 : 쓰레드 내부");
                searchPubTransPath(str_1,str_2,db_sx,db_sy,db_ex,db_ey);
                //순서 4 : 파싱

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(tag, "runOnUi쓰레드 내부");
                    }
                });
            }
        }).start();
    }

    public String searchPubTransPath(String one, String two, Double a, Double b, Double c, Double d) //버스이용 경로 조회 파싱
    {

        Log.d(tag, "순서 4 : 파싱");
        StringBuffer buffer = new StringBuffer();
        String queryUrl = "http://ws.bus.go.kr/api/rest/pathinfo/getPathInfoByBus"//요청 URL
                + "?ServiceKey=" + key
                + "&startX=" + b
                + "&startY=" + a
                + "&endX=" + d
                + "&endY=" + c;

        try {
            Log.d(tag, "들어옵니까???");
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
                        if(tag.equals("itemList"));// 첫번째 검색결과
//                        else if (tag.equals("fname"))  //출발 정류장이름과 다르면
//                        {
//                            String p_fname = String.valueOf(xpp.next());
//                            if(p_fname != one){break;}
//                        }
//                        else if (tag.equals("tname"))  //도착 정류장이름과 다르면
//                        {
//                            String p_fname2 = String.valueOf(xpp.next());
//                            if(p_fname2 != two){break;}
//                        }
                        else if (tag.equals("routeId"))  //8100번과 m4102번 버스가 아니라면
                        {
                            xpp.next();
                            String p_routeId = xpp.getText();
                            if(p_routeId != "234001159" || p_routeId != "234000878")
                            {break;}
                            //왜 마지막 값으로 받아오는거지ㅡㅡ
                        }
                        else if (tag.equals("time")){
                            xpp.next();
                            //buffer.append(xpp.getText());
                            str_time = xpp.getText();
                        } // 첫번째 검색결과
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName(); //태그 이름 얻어오기
                        if (tag.equals("itemList")) ;// 첫번째 검색결과종료..줄바꿈
                        break;
                }
                eventType = xpp.next();
            }

        } catch (Exception e) {
            Log.d(tag, "에러발생");
        }
        Log.d(tag, "이동 시간은 : " + str_time);
        Log.d(tag, "파싱종료");

        return buffer.toString();
    }
}
