package com.thefacoteam.thefaco.adapter;

import android.animation.ValueAnimator;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thefacoteam.thefaco.Bus_items;
import com.thefacoteam.thefaco.OnBusItemClickListener;
import com.thefacoteam.thefaco.R;

import java.util.ArrayList;

//3007의 버스정류장 어댑터
public class BusitemAdapter3 extends RecyclerView.Adapter<BusitemAdapter3.ViewHolder>
{
    ArrayList<Bus_items> items = new ArrayList<Bus_items>();

    //Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    //직전에 클릭됐던 Item의 position
    private int prePosition = -1;

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
        //3007 버튼을 눌렀을때 바뀐 리스트를 그대로 보여주기위한 메서드
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
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position)
    {
        //item 을 하나하나 보여주는 함수
        Bus_items item = items.get(position);
        holder.setItem(item);

        final ViewHolder viewHolder = (ViewHolder)holder;
        viewHolder.onBind(items.get(position),position,selectedItems);

        viewHolder.setOnBusItemClickListener(new OnBusItemClickListener() {
            @Override
            public void onItemClick() {
                if (selectedItems.get(position)) {
                    // 펼쳐진 Item을 클릭 시
                    selectedItems.delete(position);
                } else {
                    // 직전의 클릭됐던 Item의 클릭상태를 지움
                    selectedItems.delete(prePosition);
                    // 클릭한 Item의 position을 저장
                    selectedItems.put(position, true);
                }
                // 해당 포지션의 변화를 알림
                if (prePosition != -1) notifyItemChanged(prePosition);
                notifyItemChanged(position);
                // 클릭된 position 저장
                prePosition = position;
            }
        });


    }

    @Override
    public int getItemCount()
    {
        //RecyclerView 의 총 개수
        return items.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;  //버스정류장 이름
        TextView textView2; //버스좌석정보
        TextView textView3; //버스도착정보
        TextView textView4; //버스좌석정보(빨강)
        TextView textView7; //정류장 대기인원 수
        ImageView imageView; //버스 이미지
        ImageView imageView2; //좌석수 이미지
        ImageView imageView3; //상행선 레일 이미지
        ImageView imageView4; //하행선 레일 이미지
        ImageView imageView5; //회차 레일 이미지2
        ImageView imageView6;  //정차하는 정거장 이미지
        ImageView imageView7;   //버스도착정보 레일
        ImageView imageView8;   //버스도착정보 텍스트상자
        ImageView imageView9;   //버스대기인원 텍스트상자
        LinearLayout linearlayout; //카드뷰 전체의 레이아웃
        LinearLayout linearlayout2; //접기펼치기 레이아웃

        OnBusItemClickListener onBusItemClickListener;

        public ViewHolder(View itemView)
        {
            super(itemView);

            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView7 = itemView.findViewById(R.id.textView7);
            imageView = itemView.findViewById(R.id.busicon);
            imageView2 = itemView.findViewById(R.id.note);
            imageView3 = itemView.findViewById(R.id.rail1);
            imageView4 = itemView.findViewById(R.id.rail2);
            imageView5 = itemView.findViewById(R.id.returnrail);
            imageView6 = itemView.findViewById(R.id.railstop);
            imageView7 = itemView.findViewById(R.id.infoimage1);
            imageView8 = itemView.findViewById(R.id.infoimage2);
            imageView9 = itemView.findViewById(R.id.lineinfo);
            linearlayout = itemView.findViewById(R.id.linearlayout);
            linearlayout2 = itemView.findViewById(R.id.linearlayout2);

            linearlayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onBusItemClickListener.onItemClick();
                }
            });

        }

        public void onBind(Bus_items item, int position, SparseBooleanArray selectedItems)
        {
            textView.setText(item.getBusstopname());
            textView2.setText(item.getBusInfo());
            textView3.setText(item.getBusInfo2());
            textView4.setText(item.getBusInfo_R());
            textView7.setText(item.getLineInfo());
            imageView.setImageResource(item.getImage());
            imageView2.setImageResource(item.getImage2());
            imageView3.setImageResource(item.getRail1());
            imageView4.setImageResource(item.getRail2());
            imageView5.setImageResource(item.getReturnrail());
            imageView6.setImageResource(item.getRailstop());
            imageView7.setImageResource(item.getTextrail());
            imageView8.setImageResource(item.getTextInfobox());
            imageView9.setImageResource(item.getLineInfobox());

            changeVisibility(selectedItems.get(position));

        }

        private void changeVisibility(final boolean isExpanded)
        {
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(500);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // 높이 변경
                    linearlayout2.getLayoutParams().height = (int) animation.getAnimatedValue();
                    linearlayout2.requestLayout();
                    // View가 실제로 사라지게하는 부분
                    linearlayout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }

        public void setOnBusItemClickListener(OnBusItemClickListener onBusItemClickListener)
        {
            this.onBusItemClickListener = onBusItemClickListener;
        }

        public void setItem(Bus_items item)
        {
            textView.setText(item.getBusstopname());
            textView2.setText(item.getBusInfo());
            textView3.setText(item.getBusInfo2());
            textView4.setText(item.getBusInfo_R());
            textView7.setText(item.getLineInfo());
            imageView.setImageResource(item.getImage());
            imageView2.setImageResource(item.getImage2());
            imageView3.setImageResource(item.getRail1());
            imageView4.setImageResource(item.getRail2());
            imageView5.setImageResource(item.getReturnrail());
            imageView6.setImageResource(item.getRailstop());
            imageView7.setImageResource(item.getTextrail());
            imageView8.setImageResource(item.getTextInfobox());
            imageView9.setImageResource(item.getLineInfobox());

        }

    }

}
