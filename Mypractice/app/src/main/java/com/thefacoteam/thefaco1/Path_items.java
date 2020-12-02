package com.thefacoteam.thefaco1;

public class Path_items
{
    String Timeinfo;
    String BusTrainNum;
    String StartEndPlace;
    int BusTrainImage;

    public String getTimeinfo()
    {
        return Timeinfo;
    }

    public String getBusTrainNum()
    {
        return BusTrainNum;
    }

    public String getStartEndPlace()
    {
        return StartEndPlace;
    }

    public int getBusTrainImage()
    {
        return BusTrainImage;
    }

    public void setTimeinfo(String timeinfo)
    {
        this.Timeinfo = timeinfo;
    }

    public void setBusTrainNum(String busTrainNum)
    {
        this.BusTrainNum = busTrainNum;
    }

    public void setStartEndPlace(String startEndPlace)
    {
        this.StartEndPlace = startEndPlace;
    }

    public void setBusTrainImage(int busTrainImage)
    {
        this.BusTrainImage = busTrainImage;
    }

    public Path_items(String timeinfo, String busTrainNum, String startEndPlace, int busTrainImage)
    {
        this.Timeinfo = timeinfo;
        this.BusTrainNum = busTrainNum;
        this.StartEndPlace = startEndPlace;
        this.BusTrainImage = busTrainImage;
    }

}
