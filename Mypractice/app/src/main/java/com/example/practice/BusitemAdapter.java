package com.example.practice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

//M4102 의 버스정류장 어댑터
public class BusitemAdapter extends RecyclerView.Adapter<BusitemAdapter.ViewHolder>
{
    ArrayList<Bus_items> items = new ArrayList<Bus_items>();

    public void addItem(Bus_items item)
    {
        //외부에서 item 을 추가시킬 함수
        items.add(item);
    }

    public void setItems(ArrayList<Bus_items> items)
    {
        this.items = items;
    }

    public Bus_items getItem(int position)
    {
        return items.get(position);
    }

    public void setItem(int position, Bus_items item)
    {
        items.set(position, item);
        //M4102 버튼을 눌렀을때 바뀐 리스트를 그대로 보여주기위한 메서드
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        //인플레이션을 통해 뷰 객체 만들기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.bus_item, parent, false);
        return new ViewHolder(itemView);    //뷰홀더 객체 반환
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        //item 을 하나하나 보여주는 함수
        Bus_items item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount()
    {
        //RecyclerView 의 총 개수
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;  //버스정류장 이름
        TextView textView2; //버스도착정보

        public ViewHolder(View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
        }

        public void setItem(Bus_items item)
        {
            textView.setText(item.getBusstopname());
            textView2.setText(item.getBusInfo());
        }
    }


}
