package com.example.practice;

public class items_practice {
    String stationName;
    String mobileNo;
    String str_x;
    String str_y;
    public String getstationName()
    {
        return stationName;
    }

    public String getMobileNoo()
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
    public items_practice(String stationName, String mobileNo, String str_x, String str_y)
    {
        this.stationName = stationName;
        this.mobileNo = mobileNo;
        this.str_x = str_x;
        this.str_y = str_y;
    }
}
