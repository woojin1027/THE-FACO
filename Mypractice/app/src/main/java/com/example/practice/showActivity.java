package com.example.practice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class showActivity extends AppCompatActivity
{

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key1 = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint1 = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice"; //버스도착정보목록조회 앞 주소
    private final String route = "234001159";

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

    private String stationId_jungja;
    private String stationId_seohyun1;
    private String stationId_namdaemoon1;
    private String stationId_myeongdong;
    private String stationid_namdaemoon2;
    private String stationId_seohyun2;
    private String staOrder_jungja;
    private String staOrder_seohyun1;
    private String staOrder_namdaemoon1;
    private String staOrder_myeongdong;
    private String staOrder_namdaemoon2;
    private String staOrder_seohyun2;
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
        setContentView(R.layout.activity_show);

        getXmlId();
        buffer = new StringBuffer();
    }

    public void jungja(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_jungja = "206000725";
        staOrder_jungja = "3";
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
                getBusArrivalItem(stationId_jungja, staOrder_jungja);
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
                            buffer.append("정자역(숭례문 방면)\n");
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


    public void seohyun1(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_seohyun1 = "206000299";
        staOrder_seohyun1 = "5";
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
                getBusArrivalItem(stationId_seohyun1, staOrder_seohyun1);
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
                            buffer.append("서현역.AK플라자(숭례문 방면)\n");
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

    public void namdaemoon1(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_namdaemoon1 = "101000292";
        staOrder_namdaemoon1 = "17";
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
                getBusArrivalItem(stationId_namdaemoon1, staOrder_namdaemoon1);
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
                            buffer.append("남대문세무서.국가인권위원회 (숭례문 방면)\n");
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

    public void myeongdong(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_myeongdong = "101000148";
        staOrder_myeongdong = "24";
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
                getBusArrivalItem(stationId_myeongdong, staOrder_myeongdong);
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
                            buffer.append("명동국민은행앞 (미금역.청솔마을.2001아울렛 방면)\n");
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

    public void namdaemoon2(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationid_namdaemoon2 = "101000001";
        staOrder_namdaemoon2 = "25";
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
                getBusArrivalItem(stationid_namdaemoon2, staOrder_namdaemoon2);
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
                            buffer.append("남대문세무서.서울백병원(중) (미금역.청솔마을.2001아울렛 방면)\n");
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

    public void seohyun2(View view)
    {
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        stationId_seohyun2 = "206000026";
        staOrder_seohyun2 = "37";
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
                getBusArrivalItem(stationId_seohyun2, staOrder_seohyun2);
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
                            buffer.append("서현역.AK플라자 (미금역.청솔마을.2001아울렛 방면)\n");
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

    //activitymain.xml 레이아웃 에서 설정된 뷰들을 가져온다
    private void getXmlId()
    {
        //view 의 id 를 R 클래스에서 받아옴
        xmlShowInfo = findViewById(R.id.showInfo);
    }
}
