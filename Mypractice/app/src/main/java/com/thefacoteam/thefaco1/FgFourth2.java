package com.thefacoteam.thefaco1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FgFourth2 extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FgFourth2 newInstance(int page, String title) {
        FgFourth2 fragment = new FgFourth2();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fourth2, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView_fourth2);
        tvLabel.setText("현 주변 정거장을 확인해요!");

        ImageView iv = (ImageView) view.findViewById(R.id.iv4);
        Glide.with(this).load(R.raw.gif4).into(iv);
        return view;
    }
}