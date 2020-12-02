package com.thefacoteam.thefaco1;

//M4102 의 버스 아이템 셋팅

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

    public String getLineInfo()
    {
        return LineInfo;
    }

    public String getBusInfo_R()
    {
        return BusInfo_R;
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

    public int getLineInfobox()
    {
        return LineInfobox;
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

    public void setLineInfo(String lineinfo)
    {
        this.LineInfo = lineinfo;
    }

    public void setBusInfo_R(String busInfo_R)
    {
        this.BusInfo_R = busInfo_R;
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

    public void setLineInfobox(int lineinfobox)
    {
        this.LineInfobox = lineinfobox;
    }

    public Bus_items(String busstopname, String businfo, String businfo2, String businfo_R, int image, int image2, int rail1, int rail2, int returnrail, int railstop, int textrail, int textinfobox, String lineinfo, int lineinfobox)
    {
        this.Busstopname = busstopname;
        this.BusInfo = businfo;
        this.BusInfo_R = businfo_R;
        this.BusInfo2 = businfo2;
        this.Image = image;
        this.Image2 = image2;
        this.Rail1 = rail1;
        this.Rail2 = rail2;
        this.Returnrail = returnrail;
        this.Railstop = railstop;
        this.Textrail = textrail;
        this.TextInfobox = textinfobox;
        this.LineInfobox = lineinfobox;
        this.LineInfo = lineinfo;
    }

    int Image; //버스아이콘 이미지
    int Image2; //좌석수 배경 이미지
    int Rail1; //상행선 레일 이미지
    int Rail2; //하행선 레일 이미지
    int Returnrail; //회차 레일 이미지
    int Railstop;   //정차하는 정거장 이미지
    int Textrail;   //버스도착정보 레일
    int TextInfobox; //버스도착정보 텍스트박스
    int LineInfobox; //대기인원수 텍스트박스
    String BusInfo; //버스좌석정보
    String BusInfo_R;   //버스좌석정보(빨강)
    String BusInfo2;    //버스도착정보
    String Busstopname; //버스정류장이름
    String LineInfo;    //정류장대기인원수
}
