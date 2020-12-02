package com.thefacoteam.thefaco1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class pathSetting_start extends BaseActivity implements TextWatcher{

    public String mytest;
    public String mytest_name;

    public static Context context;

    ListView list_new;
    ArrayList<HashMap<String, String>> data;
    HashMap<String, String> data_hashmap;
    EditText searchBox;
    ImageView icon_search;
    Button nearby_stop;
    Animation scaleUp,scaleDown;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actList.add(this);
        setContentView(R.layout.pathsetting_start);

        searchBox = (EditText) findViewById(R.id.searchbox1);  //검색창
        list_new = findViewById(R.id.list_new1);  //정류장데이터
        icon_search = (ImageView) findViewById(R.id.icon_search1);  //돋보기 아이콘
        nearby_stop = (Button) findViewById(R.id.nearby_stop1);
        scaleUp = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        scaleDown = AnimationUtils.loadAnimation(this,R.anim.scale_down);

        nearby_stop.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if(motionEvent.getAction() == motionEvent.ACTION_DOWN)
                    nearby_stop.startAnimation(scaleUp);
                else if(motionEvent.getAction()==motionEvent.ACTION_UP){
                    nearby_stop.startAnimation(scaleDown);
                } return false;
            }
        });

        data = new ArrayList<HashMap<String, String>>();

        //신도시-서울 출퇴근용//
        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "단국대.치과병원");
        data_hashmap.put("정류소번호", "47682");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "단국대정문");
        data_hashmap.put("정류소번호", "47717");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "꽃메마을.새에덴교회");
        data_hashmap.put("정류소번호", "29277");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "보정동주민센터");
        data_hashmap.put("정류소번호", "29249");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "오리역");
        data_hashmap.put("정류소번호", "07058");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "미금역.청솔마을.2001아울렛");
        data_hashmap.put("정류소번호", "07333");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "정자역");
        data_hashmap.put("정류소번호", "07624");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "분당구청입구.수내교");
        data_hashmap.put("정류소번호", "07115");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "순천향대학병원");
        data_hashmap.put("정류소번호", "03163");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남대문세무서.국가인권위원회");
        data_hashmap.put("정류소번호", "02287");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "종로2가사거리");
        data_hashmap.put("정류소번호", "01001");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "을지로입구역.광교");
        data_hashmap.put("정류소번호", "02246");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "북창동.남대문시장");
        data_hashmap.put("정류소번호", "02283");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서울역버스환승센터(5번승강장)(중)");
        data_hashmap.put("정류소번호", "02005");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서울역버스환승센터6");
        data_hashmap.put("정류소번호", "02006");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "숭례문");
        data_hashmap.put("정류소번호", "02121");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "명동국민은행앞");
        data_hashmap.put("정류소번호", "02253");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남대문세무서.서울백병원");
        data_hashmap.put("정류소번호", "02001");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "순천향대학병원");
        data_hashmap.put("정류소번호", "03164");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "분당구청입구.수내교");
        data_hashmap.put("정류소번호", "07114");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "정자역");
        data_hashmap.put("정류소번호", "07049");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "미금역.청솔마을.2001아울렛");
        data_hashmap.put("정류소번호", "07331");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "오리역");
        data_hashmap.put("정류소번호", "07057");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "보정동주민센터");
        data_hashmap.put("정류소번호", "29873");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "꽃메마을2단지");
        data_hashmap.put("정류소번호", "29282");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "단국대.치과병원");
        data_hashmap.put("정류소번호", "47683");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서현역");
        data_hashmap.put("정류소번호", "07302");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서현역.AK플라자");
        data_hashmap.put("정류소번호", "07169");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "이매촌한신.서현역.AK프라자");
        data_hashmap.put("정류소번호", "07561");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "이매촌한신.서현역.AK프라자");
        data_hashmap.put("정류소번호", "07168");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남대문시장앞.이회영활동터");
        data_hashmap.put("정류소번호", "02219");
        data.add(data_hashmap);

        //경기대학생 등하교용//

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "사색의광장");
        data_hashmap.put("정류소번호", "29059");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "생명과학대.산업대학");
        data_hashmap.put("정류소번호", "29050");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경희대체육대학.외대");
        data_hashmap.put("정류소번호", "29044");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경희대학교");
        data_hashmap.put("정류소번호", "04241");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "살구골.서광아파트");
        data_hashmap.put("정류소번호", "04144");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "살구골동아아파트");
        data_hashmap.put("정류소번호", "04129");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "살구골현대아파트.영통역4번출구");
        data_hashmap.put("정류소번호", "04135");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "영통역");
        data_hashmap.put("정류소번호", "04150");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "쳥명역3번출구");
        data_hashmap.put("정류소번호", "04160");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "황골벽산아파트");
        data_hashmap.put("정류소번호", "04169");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "영통빌리지");
        data_hashmap.put("정류소번호", "29023");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "삼성전자입구");
        data_hashmap.put("정류소번호", "04123");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "원천주공2단지");
        data_hashmap.put("정류소번호", "04108");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "광교호반베르디움");
        data_hashmap.put("정류소번호", "04377");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "KT동수원지사");
        data_hashmap.put("정류소번호", "04056");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "구법원사거리");
        data_hashmap.put("정류소번호", "04042");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "아주대학교입구");
        data_hashmap.put("정류소번호", "04238");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "아주대.아주대학교병원");
        data_hashmap.put("정류소번호", "04237");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "효성초등학교");
        data_hashmap.put("정류소번호", "03117");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원월드컵경기장.동성중학교");
        data_hashmap.put("정류소번호", "03105");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "여권민원실.풍림아파트");
        data_hashmap.put("정류소번호", "03096");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "우만동4단지");
        data_hashmap.put("정류소번호", "03088");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기남부지방경찰청.봉녕사입구");
        data_hashmap.put("정류소번호", "04002");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기대수원캠퍼스후문.수원박물관");
        data_hashmap.put("정류소번호", "04178");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "의왕톨게이트");
        data_hashmap.put("정류소번호", "27113");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "관문사거리부대앞");
        data_hashmap.put("정류소번호", "21013");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남태령역");
        data_hashmap.put("정류소번호", "27026");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "사당역");
        data_hashmap.put("정류소번호", "22028");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "사당역4번출구");
        data_hashmap.put("정류소번호", "21161");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "남태령역");
        data_hashmap.put("정류소번호", "21163");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "관문사거리부대앞");
        data_hashmap.put("정류소번호", "21014");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "의왕톨게이트");
        data_hashmap.put("정류소번호", "27112");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기대수원캠퍼스후문.수원박물관");
        data_hashmap.put("정류소번호", "04181");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기남부지방경찰청.봉녕사입구");
        data_hashmap.put("정류소번호", "01257");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "연무동");
        data_hashmap.put("정류소번호", "01236");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원시평생학습관.우만주공아파트");
        data_hashmap.put("정류소번호", "03082");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "여권민원실.풍림아파트");
        data_hashmap.put("정류소번호", "03095");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원월드컵경기장.동성중학교");
        data_hashmap.put("정류소번호", "03102");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "효성초등학교");
        data_hashmap.put("정류소번호", "03119");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "아주대.아주대학교병원");
        data_hashmap.put("정류소번호", "03126");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "아주대학교입구");
        data_hashmap.put("정류소번호", "03129");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "구법원사거리");
        data_hashmap.put("정류소번호", "04179");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "KT동수원지사");
        data_hashmap.put("정류소번호", "04058");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "광교호수공원입구.원천교사거리");
        data_hashmap.put("정류소번호", "04086");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "원천주공2단지");
        data_hashmap.put("정류소번호", "04105");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "삼성전자입구");
        data_hashmap.put("정류소번호", "04124");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "프리미엄아울렛");
        data_hashmap.put("정류소번호", "29024");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "황골주공.벽산아파트");
        data_hashmap.put("정류소번호", "04165");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "청명역4번출구");
        data_hashmap.put("정류소번호", "04157");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "훼미리타워");
        data_hashmap.put("정류소번호", "04384");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "영통역8번출구");
        data_hashmap.put("정류소번호", "04141");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "극동.풍림아파트");
        data_hashmap.put("정류소번호", "04131");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "e편한세상아파트");
        data_hashmap.put("정류소번호", "04126");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서그내");
        data_hashmap.put("정류소번호", "29003");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경희대정문");
        data_hashmap.put("정류소번호", "29038");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "외국어대학");
        data_hashmap.put("정류소번호", "29040");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "생명과학대");
        data_hashmap.put("정류소번호", "29049");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "사색의광장");
        data_hashmap.put("정류소번호", "29058");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원버스터미널");
        data_hashmap.put("정류소번호", "02219");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원버스터미널");
        data_hashmap.put("정류소번호", "02224");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원아이파크시티.선일초교");
        data_hashmap.put("정류소번호", "02252");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "우남아파트");
        data_hashmap.put("정류소번호", "02265");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "평생교육학습관.남수원중학교");
        data_hashmap.put("정류소번호", "02267");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기소방본부");
        data_hashmap.put("정류소번호", "04233");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "임광아파트");
        data_hashmap.put("정류소번호", "04234");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "매탄위브하늘채.경기아트센터");
        data_hashmap.put("정류소번호", "04235");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "자유총연맹");
        data_hashmap.put("정류소번호", "04236");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "삼성1차아파트");
        data_hashmap.put("정류소번호", "04007");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "삼성2차아파트");
        data_hashmap.put("정류소번호", "04012");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "시민의숲.양재꽃시장");
        data_hashmap.put("정류소번호", "22297");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "양재역.서초문화예술회관");
        data_hashmap.put("정류소번호", "22004");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "뱅뱅사거리");
        data_hashmap.put("정류소번호", "22006");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "우성아파트");
        data_hashmap.put("정류소번호", "22008");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "강남역.역삼세무서");
        data_hashmap.put("정류소번호", "23287");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "강남역나라빌딩앞");
        data_hashmap.put("정류소번호", "91200");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "역삼역.역삼동.우성아파트");
        data_hashmap.put("정류소번호", "90159");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서초문화예술정보학교");
        data_hashmap.put("정류소번호", "22132");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "양재역커피빈앞");
        data_hashmap.put("정류소번호", "90256");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "시민의숲.양재꽃시장");
        data_hashmap.put("정류소번호", "90256");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "서초포레스타입구");
        data_hashmap.put("정류소번호", "22399");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "광교역사공원.광교카페거리");
        data_hashmap.put("정류소번호", "04232");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "매탄주공4단지.매탄1동주민센터");
        data_hashmap.put("정류소번호", "04009");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "자유총연맹");
        data_hashmap.put("정류소번호", "03120");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기아트센터.매탄위브하늘채");
        data_hashmap.put("정류소번호", "03116");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "임광아파트.야외음악당");
        data_hashmap.put("정류소번호", "03112");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "경기소방본부");
        data_hashmap.put("정류소번호", "02856");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "평생교육학습관.남수원중학교.온수골");
        data_hashmap.put("정류소번호", "02266");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "대원아파트");
        data_hashmap.put("정류소번호", "02264");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "선일초등학교");
        data_hashmap.put("정류소번호", "02256");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "이마트");
        data_hashmap.put("정류소번호", "02232");
        data.add(data_hashmap);

        data_hashmap = new HashMap<String, String>();
        data_hashmap.put("정류장명", "수원버스터미널");
        data_hashmap.put("정류소번호", "02221");
        data.add(data_hashmap);
        //여기까지//



        SimpleAdapter adapter = new SimpleAdapter(
                getApplicationContext(), data,
                android.R.layout.simple_list_item_2,
                new String[]{"정류장명", "정류소번호"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        list_new.setAdapter(adapter);


        list_new.setTextFilterEnabled(true);
        searchBox.addTextChangedListener(this);

/*
        //엔터키막기
        final InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        searchBox.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    imm.hideSoftInputFromWindow(searchBox.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
*/
        //주변 정류장 클릭시 이벤트
        nearby_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pathSetting_start.this, map_around_busstop.class);
                startActivity(intent);
            }
        });


        //돋보기 클릭 시 -> 리스트 띄우고 -> 리스트 누르면 지도 띄우기
        icon_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_new.setVisibility(View.VISIBLE);
                toastshow(v, "검색 결과");
            }
        });


        list_new.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String,String> map =(HashMap<String,String>)list_new.getItemAtPosition(position);
                mytest = map.get("정류소번호"); //07333같은 5글자의 정류소 고유 숫자
                mytest_name = map.get("정류장명");

                Intent intent = new Intent(pathSetting_start.this, pathset_mapshow_start.class);
                //정류장 위도경도 따서 지도에 띄우기
                startActivity(intent);
            }
        });
        context = this;
    }


    public void toastshow(View view, String string) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate( R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(this);
        text.setText(string);
        text.setTextSize(15);
        text.setTextColor(Color.WHITE);
        toast.setGravity(Gravity.BOTTOM,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show(); }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        list_new.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        list_new.setVisibility(View.VISIBLE);
        list_new.setFilterText(searchBox.getText().toString());
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (searchBox.getText().length() == 0) {
            list_new.clearTextFilter();
        }
    }
}