package com.thefacoteam.thefaco.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thefacoteam.thefaco.Path_items;
import com.thefacoteam.thefaco.R;

import java.util.ArrayList;

public class PathitemAdapter extends RecyclerView.Adapter<PathitemAdapter.ViewHolder>
{
    ArrayList<Path_items> items = new ArrayList<Path_items>();

    public void addItem(Path_items item)
    {
        //외부에서 item 을 추가시킬 함수
        items.add(item);

    }

    public void setItems(ArrayList<Path_items> items)
    {
        this.items = items;
    }

    public Path_items getItem(int position)
    {
        return items.get(position);
    }

    public void setItem(int position, Path_items item)
    {
        items.set(position, item);
        //아이템 변화 처리
        notifyItemChanged(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //인플레이션을 통해 뷰 객체 만들기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.path_item, parent, false);
        return new ViewHolder(itemView);    //뷰홀더 객체 반환
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //item 을 하나하나 보여주는 함수
        Path_items item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        //RecyclerView 의 총 개수
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;      //길찾기 소요시간
        TextView textView2;     //버스,지하철 번호
        TextView textView3;     //출발도착지
        ImageView imageView;    //버스,지하철이미지

        public ViewHolder(View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(R.id.timeinfo);
            textView2 = itemView.findViewById(R.id.BusTrainNum);
            textView3 = itemView.findViewById(R.id.StartEndPlace);
            imageView = itemView.findViewById(R.id.BusTrainimage);
        }

        public void setItem(Path_items item)
        {
            textView.setText(item.getTimeinfo());
            textView2.setText(item.getBusTrainNum());
            textView3.setText(item.getStartEndPlace());
            imageView.setImageResource(item.getBusTrainImage());
        }
    }
}
