package com.example.practice;

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

import java.util.ArrayList;
//똑같이 따라함
public class PracticeAdapter extends RecyclerView.Adapter<PracticeAdapter.ViewHolder>
{
    ArrayList<Practice_items> items = new ArrayList<>();
    //Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    //직전에 클릭됐던 Item의 position
    private int prePosition = -1;

    public void addItem(Practice_items item) {
        //외부에서 item 을 추가시킬 함수
        items.add(item);
    }

    public void setItem(int position, Practice_items item) {
        items.set(position, item);
        //M4102 버튼을 눌렀을때 바뀐 리스트를 그대로 보여주기위한 메서드
        notifyItemChanged(position);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //인플레이션을 통해 뷰 객체 만들기
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.practice_item, parent, false);
        return new ViewHolder(itemView);    //뷰홀더 객체 반환
    }


    @Override
    public void onBindViewHolder(@NonNull PracticeAdapter.ViewHolder holder, final int position) {
//item 을 하나하나 보여주는 함수
        Practice_items item = items.get(position);
        holder.setItem(item);

        final ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.onBind(items.get(position), position, selectedItems);

        viewHolder.setOnItemClickListener(new OnItemClickListener_practice() {
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
    public int getItemCount() {
        //RecyclerView 의 총 개수
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView;  //정류장명
        TextView textView2; //모바일넘버
        TextView textView3; //경도
        TextView textView4; //위도
        ImageView imageView; //버스 이미지

        LinearLayout linearlayout; //카드뷰 전체의 레이아웃
        //LinearLayout linearlayout2; //접기펼치기 레이아웃

        OnItemClickListener_practice onItemClickListener_practice;

        public ViewHolder(View itemView)
        {
            super(itemView);

            //카드뷰안에 있는 요소들
            textView = itemView.findViewById(R.id.text1);
            textView2 = itemView.findViewById(R.id.text2);
            textView3 = itemView.findViewById(R.id.text3);
            textView4 = itemView.findViewById(R.id.text4);
            imageView = itemView.findViewById(R.id.BusTrainimage1);

            linearlayout = itemView.findViewById(R.id.LinearLayout_practice);
            //linearlayout2 = itemView.findViewById(R.id.linearlayout2);

            linearlayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onItemClickListener_practice.onItemClick();
                }
            });

        }

        public void onBind(Practice_items item, int position, SparseBooleanArray selectedItems) {
            textView.setText(item.getStationName());
            textView2.setText(item.getMobileNo());
            textView3.setText(item.getstr_x());
            textView4.setText(item.getstr_y());
            imageView.setImageResource(item.getImage());

            changeVisibility(selectedItems.get(position));

        }

        private void changeVisibility(final boolean isExpanded) {
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(500);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    /* 높이 변경
                    linearlayout2.getLayoutParams().height = (int) animation.getAnimatedValue();
                    linearlayout2.requestLayout();
                    // View가 실제로 사라지게하는 부분
                    linearlayout2.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

                     */
                }
            });
            // Animation start
            va.start();
        }

        public void setOnItemClickListener(OnItemClickListener_practice onItemClickListener_practice) {
            this.onItemClickListener_practice = onItemClickListener_practice;
        }

        public void setItem(Practice_items item) {
            textView.setText(item.getStationName());
            textView2.setText(item.getMobileNo());
            textView3.setText(item.getstr_x());
            textView4.setText(item.getstr_y());
            imageView.setImageResource(item.getImage());

        }
    }
}
