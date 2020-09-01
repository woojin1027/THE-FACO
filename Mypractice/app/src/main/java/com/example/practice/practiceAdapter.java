package com.example.practice;

import java.util.ArrayList;

public class practiceAdapter {
    ArrayList<items_practice> items = new ArrayList<>();

    public void addItem(items_practice item)
    {
        //외부에서 item 을 추가시킬 함수
        items.add(item);
    }
}
