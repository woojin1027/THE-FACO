package com.example.practice;

public class Bus_items2
{

    public String getBusInfo() {
        return BusInfo;
    }

    public void setBusInfo(String busInfo) {
        this.BusInfo = busInfo;
    }

    public String getBusstopname() {
        return Busstopname;
    }

    public void setBusstopname(String busstopname) {
        this.Busstopname = busstopname;
    }

    public Bus_items2(String busInfo, String busstopname)
    {
        this.BusInfo = busInfo;
        this.Busstopname = busstopname;
    }

    String BusInfo;
    String Busstopname;
}
