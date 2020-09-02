package com.example.practice;

import java.util.ArrayList;

public class Practice_items
{
    public String getStationName()
    {
        return stationName;
    }

    public String getMobileNo()
    {
        return mobileNo;
    }

    public String getstr_x()
    {
        return str_x;
    }

    public String getstr_y()
    {
        return str_y;
    }

    public int getImage()
    {
        return Image;
    }

    public Practice_items(String stationName, String mobileNo, String str_x, String str_y, int image)
    {
        this.stationName = stationName;
        this.mobileNo = mobileNo;
        this.str_x = str_x;
        this.str_y = str_y;
        this.Image = image;
    }

    String stationName;
    String mobileNo;
    String str_x;
    String str_y;
    int Image; //버스아이콘 이미지
}
