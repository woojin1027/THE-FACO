package com.example.thefaco;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity
{

    //final 변수는 한번만 할당한다. 두번이상 할당하려 할때 컴파일 오류!
    private final String TAG = "myTag";
    private final String key = "AGosnxF7ORMEFRnphkCbkve01B6SaEZpj5R2kD03%2B43HobZwgWC2BqRthRvHeMOEWK1M%2BAPASvsbGc3K7Z9V8A%3D%3D"; //버스도착정보목록조회 인증키
    private final String endPoint = "http://openapi.gbis.go.kr/ws/rest/busarrivalservice";     //버스도착정보목록조회 앞 주소


    //xml 변수
    private EditText xmlBusNum;
    private EditText xmlStationNum;
    private TextView xmlShowInfo;

    //파싱을 위한 필드 선언
    private URL url;
    private InputStream is;
    private XmlPullParserFactory factory;
    private XmlPullParser xpp;
    private String tag;
    private int eventType;

    //xml 값 입력 변수
    private String busNum;  //버스 번호
    private String stationNum = ""; //출발 정류장 Num
    private StringBuffer buffer;

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
        //상태바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        
        //xml 아이디 얻어오기
        getXmlId();
        buffer = new StringBuffer(); //문자열 변경,추가시 사용
    }

    //검색하기 버튼
    public void search(View view)
    {
        //사용자한테 출발정류장, 도착정류장 알아오기
        busNum = xmlBusNum.getText().toString(); //사용자가 입력한 버스번호를 문자열로 변환
        stationNum = xmlStationNum.getText().toString(); //사용자가 입력한 버스정류장 번호를 문자열로 변환
        car1 = min1 = station1 = seat1 = car2 = min2 = station2 = seat2 = null;
        buffer = null;
        buffer = new StringBuffer();
        xmlShowInfo.setText("");

        //입력값 검사 함수 만들기
        if(examineData())
        {
            return; //true 를 반환할 경우 값이 잘못됨
        }
        //준비상태의 스레드: 코딩 상에서 start() 메소드를 호출하면 run() 메소드에 설정된 스레드가 Runnable 상태로 진입
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                //오퍼레이션 1
                getBusArrivalList(stationNum);
                //오퍼레이션 2
                //getBusStationList(stationName);

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


    //오퍼레이션 1 (버스도착정보목록조회)
   private void getBusArrivalList(String station)
    {
        String stationUrl = endPoint + "/station?serviceKey=" + key + "&stationId=" + station;
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


    //Url, xmlPullParser 객체 생성 및 초기화
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

    private boolean examineData()
    {
        //사용자가 하나 이상의 값을 입력하지 않은 경우
        if(busNum.equals("") || stationNum.equals(""))
        {
            //Toast = 사용자에게 짧은 메시지 형식으로 정보를 전달하는 팝업
            Toast User_null = Toast.makeText(this, "값을 입력해 주세요!", Toast.LENGTH_SHORT);
            User_null.show();
            return true;
        }

        //입력값은 반드시 숫자
        String UserNum = "([0-9])"; //정규표현식 사용
        Pattern pattern_symbol = Pattern.compile(UserNum);

        //버스 번호 유효성 검사 pattern 객체를 matcher 메소드를 호출하여 사용
        Matcher matcher_busNum = pattern_symbol.matcher(busNum);
        if(matcher_busNum.find() == false)
        {
            Toast User_num = Toast.makeText(this, "버스 번호를 다시 입력해주세요 !", Toast.LENGTH_SHORT);
            User_num.show();
            return true;
        }

        //정류장 번호 유효성 검사
        Matcher matcher_stationNum = pattern_symbol.matcher(stationNum);
        if(matcher_stationNum.find() == false)
        {
            Toast User_stationNum = Toast.makeText(this, "정류장 번호를 다시 입력해주세요!", Toast.LENGTH_SHORT);
            User_stationNum.show();
            return true;
        }

       return false; //사용자의 값이 정상일때 false 반환
    }

    //activitymain.xml 레이아웃 에서 설정된 뷰들을 가져온다
    private void getXmlId()
    {
        //view 의 id 를 R 클래스에서 받아옴
        xmlBusNum = findViewById(R.id.busNum);
        xmlStationNum = findViewById(R.id.stationNum);
        xmlShowInfo = findViewById(R.id.showInfo);
    }


}
