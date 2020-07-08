package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class subActivity extends AppCompatActivity {

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key1 = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint1 = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice"; //버스도착정보목록조회 앞 주소
    private final String route = "234000878";

    //xml 변수
    private TextView xmlShowInfo;

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    //xml 값 입력 변수
    private StringBuffer buffer;

    private String stationId_ori;
    private String stationId_mikoom;
    private String stationId_jungja1;
    private String stationId_namdaemoon;
    private String stationId_sooncheon;
    private String stationId_jungja2;
    private String staOrder_ori;
    private String staOrder_mikoom;
    private String staOrder_jungja1;
    private String staOrder_namdaemoon;
    private String staOrder_sooncheon;
    private String staOrder_jungja2;
    private String car1;
    private String min1;
    private String station1;
    private String seat1;
    private String car2;
    private String min2;
    private String station2;
    private String seat2;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        getXmlId();
        buffer = new StringBuffer();

    }

    public void ori(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_ori = "206000040";
        staOrder_ori = "5";
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalItem(stationId_ori, staOrder_ori);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, car1 + " " + min1 + " " + station1);
                        Log.d(TAG, car2 + " " + min2 + " " + station2);
                        if(car1 == null)
                        {
                            buffer.append("도착 정보 없음");
                        }
                        else
                            {
                                buffer.append("오리역 (서울역버스환승센터 방면)\n");
                                buffer.append("첫번째 차량 도착 정보\n");
                                buffer.append("차량 번호 : " + car1 + " \n");
                                buffer.append("남은 시간 : " + min1 + " 분 \n");
                                buffer.append("남은 구간 : " + station1 + "정거장\n");
                                buffer.append("빈 좌석수 : " + seat1 + "\n");
                            }
                        // 두번째 도착 차량은 null이 아닐 경우에만 출력
                        if(car2 != null)
                        {
                            buffer.append("-------------------------\n");
                            buffer.append("두번째 차량 도착 정보\n");
                            buffer.append("차량 번호 : " + car2 + " \n");
                            buffer.append("남은 시간 : " + min2 + "분 \n");
                            buffer.append("남은 구간 : " + station2 + "정거장 \n");
                            buffer.append("빈 좌석수 : " + seat2 + "\n");
                        }
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }


    public void mikoom(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_mikoom = "206000087";
        staOrder_mikoom = "6";
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalItem(stationId_mikoom, staOrder_mikoom);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, car1 + " " + min1 + " " + station1);
                        Log.d(TAG, car2 + " " + min2 + " " + station2);
                        if(car1 == null)
                        {
                            buffer.append("도착 정보 없음");
                        }
                        else
                            {
                                buffer.append("미금역.청솔마을.2001아울렛 (서울역버스환승센터 방면)\n");
                                buffer.append("첫번째 차량 도착 정보\n");
                                buffer.append("차량 번호 : " + car1 + " \n");
                                buffer.append("남은 시간 : " + min1 + " 분 \n");
                                buffer.append("남은 구간 : " + station1 + "정거장\n");
                                buffer.append("빈 좌석수 : " + seat1 + "\n");
                            }
                        // 두번째 도착 차량은 null이 아닐 경우에만 출력
                        if(car2 != null)
                        {
                            buffer.append("-------------------------\n");
                            buffer.append("두번째 차량 도착 정보\n");
                            buffer.append("차량 번호 : " + car2 + " \n");
                            buffer.append("남은 시간 : " + min2 + "분 \n");
                            buffer.append("남은 구간 : " + station2 + "정거장 \n");
                            buffer.append("빈 좌석수 : " + seat2 + "\n");
                        }
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }

    public void jungja1(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_jungja1 = "206000725";
        staOrder_jungja1 = "7";
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalItem(stationId_jungja1, staOrder_jungja1);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, car1 + " " + min1 + " " + station1);
                        Log.d(TAG, car2 + " " + min2 + " " + station2);
                        if(car1 == null)
                        {
                            buffer.append("도착 정보 없음");
                        }
                        else
                            {
                                buffer.append("정자역 (서울역버스환승센터 방면)\n");
                                buffer.append("첫번째 차량 도착 정보\n");
                                buffer.append("차량 번호 : " + car1 + " \n");
                                buffer.append("남은 시간 : " + min1 + " 분 \n");
                                buffer.append("남은 구간 : " + station1 + "정거장\n");
                                buffer.append("빈 좌석수 : " + seat1 + "\n");
                            }
                        // 두번째 도착 차량은 null이 아닐 경우에만 출력
                        if(car2 != null)
                        {
                            buffer.append("-------------------------\n");
                            buffer.append("두번째 차량 도착 정보\n");
                            buffer.append("차량 번호 : " + car2 + " \n");
                            buffer.append("남은 시간 : " + min2 + "분 \n");
                            buffer.append("남은 구간 : " + station2 + "정거장 \n");
                            buffer.append("빈 좌석수 : " + seat2 + "\n");
                        }
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }

    public void namdaemoon(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_namdaemoon = "101000001";
        staOrder_namdaemoon = "29";
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalItem(stationId_namdaemoon, staOrder_namdaemoon);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, car1 + " " + min1 + " " + station1);
                        Log.d(TAG, car2 + " " + min2 + " " + station2);
                        if(car1 == null)
                        {
                            buffer.append("도착 정보 없음");
                        }
                        else
                            {
                                buffer.append("남대문세무서.서울백병원(중) (단국대.치과병원 방면)\n");
                                buffer.append("첫번째 차량 도착 정보\n");
                                buffer.append("차량 번호 : " + car1 + " \n");
                                buffer.append("남은 시간 : " + min1 + " 분 \n");
                                buffer.append("남은 구간 : " + station1 + "정거장\n");
                                buffer.append("빈 좌석수 : " + seat1 + "\n");
                            }
                        // 두번째 도착 차량은 null이 아닐 경우에만 출력
                        if(car2 != null)
                        {
                            buffer.append("-------------------------\n");
                            buffer.append("두번째 차량 도착 정보\n");
                            buffer.append("차량 번호 : " + car2 + " \n");
                            buffer.append("남은 시간 : " + min2 + "분 \n");
                            buffer.append("남은 구간 : " + station2 + "정거장 \n");
                            buffer.append("빈 좌석수 : " + seat2 + "\n");
                        }
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }

    public void sooncheon(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_sooncheon = "102000070";
        staOrder_sooncheon = "32";
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalItem(stationId_sooncheon, staOrder_sooncheon);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, car1 + " " + min1 + " " + station1);
                        Log.d(TAG, car2 + " " + min2 + " " + station2);
                        if(car1 == null)
                        {
                            buffer.append("도착 정보 없음");
                        }
                        else
                            {
                                buffer.append("순천향대학병원 (단국대.치과병원 방면)\n");
                                buffer.append("첫번째 차량 도착 정보\n");
                                buffer.append("차량 번호 : " + car1 + " \n");
                                buffer.append("남은 시간 : " + min1 + " 분 \n");
                                buffer.append("남은 구간 : " + station1 + "정거장\n");
                                buffer.append("빈 좌석수 : " + seat1 + "\n");
                            }
                        // 두번째 도착 차량은 null이 아닐 경우에만 출력
                        if(car2 != null)
                        {
                            buffer.append("-------------------------\n");
                            buffer.append("두번째 차량 도착 정보\n");
                            buffer.append("차량 번호 : " + car2 + " \n");
                            buffer.append("남은 시간 : " + min2 + "분 \n");
                            buffer.append("남은 구간 : " + station2 + "정거장 \n");
                            buffer.append("빈 좌석수 : " + seat2 + "\n");
                        }
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }

    public void jungja2(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_jungja2 = "206000189";
        staOrder_jungja2 = "42";
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalItem(stationId_jungja2, staOrder_jungja2);
                //UI setText 하는 곳
                runOnUiThread(new Runnable(){
                    @Override
                    public void run()
                    {
                        Log.d(TAG, car1 + " " + min1 + " " + station1);
                        Log.d(TAG, car2 + " " + min2 + " " + station2);
                        if(car1 == null)
                        {
                            buffer.append("도착 정보 없음");
                        }
                        else
                            {
                                buffer.append("정자역 (단국대.치과병원 방면)\n");
                                buffer.append("첫번째 차량 도착 정보\n");
                                buffer.append("차량 번호 : " + car1 + " \n");
                                buffer.append("남은 시간 : " + min1 + " 분 \n");
                                buffer.append("남은 구간 : " + station1 + "정거장\n");
                                buffer.append("빈 좌석수 : " + seat1 + "\n");
                            }
                        // 두번째 도착 차량은 null이 아닐 경우에만 출력
                        if(car2 != null)
                        {
                            buffer.append("-------------------------\n");
                            buffer.append("두번째 차량 도착 정보\n");
                            buffer.append("차량 번호 : " + car2 + " \n");
                            buffer.append("남은 시간 : " + min2 + "분 \n");
                            buffer.append("남은 구간 : " + station2 + "정거장 \n");
                            buffer.append("빈 좌석수 : " + seat2 + "\n");
                        }
                        xmlShowInfo.setText(buffer.toString());
                    }
                });
            }
        }).start();
    }


    //오퍼레이션 2 (버스도착정보항목조회)
    private void getBusArrivalItem(String station, String staorder)
    {
        String stationUrl = endPoint1 + "?serviceKey=" + key1 + "&stationId=" + station + "&routeId=" + route + "&staOrder=" + staorder;
        Log.d(TAG, "정류장명 -> 정류장Id : " + stationUrl);

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
                        else if(tag.equals("plateNo1"))
                        {
                            xpp.next();
                            car1 = xpp.getText();
                        }
                        else if(tag.equals("locationNo1"))
                        {
                            xpp.next();
                            station1 = xpp.getText();

                        }
                        else if(tag.equals("predictTime1"))
                        {
                            xpp.next();
                            min1 = xpp.getText();

                        }
                        else if(tag.equals("remainSeatCnt1"))
                        {
                            xpp.next();
                            seat1 = xpp.getText();
                        }
                        else if(tag.equals("plateNo2"))
                        {
                            xpp.next();
                            car2 = xpp.getText();
                        }
                        else if(tag.equals("locationNo2"))
                        {
                            xpp.next();
                            station2 = xpp.getText();
                        }
                        else if(tag.equals("predictTime2"))
                        {
                            xpp.next();
                            min2 = xpp.getText();
                        }
                        else if(tag.equals("remainSeatCnt2"))
                        {
                            xpp.next();
                            seat2 = xpp.getText();
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

    //activitysub.xml 레이아웃 에서 설정된 뷰들을 가져온다
    private void getXmlId()
    {
        //view 의 id 를 R 클래스에서 받아옴
        xmlShowInfo = findViewById(R.id.showInfo);
    }
}
