package com.example.practice;

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

    public void setBusstopname(String busstopname)
    {
        this.Busstopname = busstopname;
    }

    public void setBusInfo(String businfo)
    {
        this.BusInfo = businfo;
    }

    public Bus_items(String busstopname, String businfo)
    {
        this.Busstopname = busstopname;
        this.BusInfo = businfo;
    }

    public Bus_items(String businfo)
    {
        this.BusInfo = businfo;
    }

    String BusInfo;
    String Busstopname;
}
