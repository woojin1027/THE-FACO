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

    public int getImage()
    {
        return Image;
    }

    public void setBusstopname(String busstopname)
    {
        this.Busstopname = busstopname;
    }

    public void setBusInfo(String businfo)
    {
        this.BusInfo = businfo;
    }

    public void setImage(int image)
    {
        this.Image = image;
    }

    public Bus_items(String busstopname, String businfo, int image)
    {
        this.Busstopname = busstopname;
        this.BusInfo = businfo;
        this.Image = image;
    }

    public Bus_items(String businfo)
    {
        this.BusInfo = businfo;
    }

    int Image;
    String BusInfo;
    String Busstopname;
}
