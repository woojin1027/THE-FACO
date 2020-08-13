package com.example.practice;

//M4102 의 버스 아이템 셋팅

import android.graphics.drawable.Drawable;

public class Bus_items
{
    public String getBusstopname()
    {
        return Busstopname;
    }

    public String getBusInfo()
    {
        return BusInfo;
    }

    public String getBusInfo2()
    {
        return BusInfo2;
    }

    public int getImage()
    {
        return Image;
    }

    public int getImage2()
    {
        return Image2;
    }

    public int getRail1()
    {
        return Rail1;
    }

    public int getRail2()
    {
        return Rail2;
    }

    public int getReturnrail()
    {
        return Returnrail;
    }

    public int getRailstop()
    {
        return Railstop;
    }

    public int getTextrail()
    {
        return Textrail;
    }

    public int getTextInfobox()
    {
        return TextInfobox;
    }

    public void setBusstopname(String busstopname)
    {
        this.Busstopname = busstopname;
    }

    public void setBusInfo(String businfo)
    {
        this.BusInfo = businfo;
    }

    public void setBusInfo2(String businfo2)
    {
        this.BusInfo2 = businfo2;
    }

    public void setImage(int image)
    {
        this.Image = image;
    }

    public void setImage2(int image2)
    {
        this.Image2 = image2;
    }

    public void setRail1(int rail1)
    {
        this.Rail1 = rail1;
    }

    public void setRail2(int rail2)
    {
        this.Rail2 = rail2;
    }

    public void setReturnrail(int returnrail)
    {
        this.Returnrail = returnrail;
    }

    public void setRailstop(int railstop)
    {
        this.Railstop = railstop;
    }

    public void setTextrail(int textrail)
    {
        this.Textrail = textrail;
    }

    public void setTextInfobox(int textinfobox)
    {
        this.TextInfobox = textinfobox;
    }

    public Bus_items(String busstopname, String businfo, String businfo2, int image, int image2, int rail1, int rail2, int returnrail, int railstop, int textrail, int textinfobox)
    {
        this.Busstopname = busstopname;
        this.BusInfo = businfo;
        this.BusInfo2 = businfo2;
        this.Image = image;
        this.Image2 = image2;
        this.Rail1 = rail1;
        this.Rail2 = rail2;
        this.Returnrail = returnrail;
        this.Railstop = railstop;
        this.Textrail = textrail;
        this.TextInfobox = textinfobox;
    }

    int Image; //버스아이콘 이미지
    int Image2; //좌석수 배경 이미지
    int Rail1; //상행선 레일 이미지
    int Rail2; //하행선 레일 이미지
    int Returnrail; //회차 레일 이미지
    int Railstop;   //정차하는 정거장 이미지
    int Textrail;   //버스도착정보 레일
    int TextInfobox; //버스도착정보 텍스트박스
    String BusInfo; //버스좌석정보
    String BusInfo2;    //버스도착정보
    String Busstopname; //버스정류장이름
}
