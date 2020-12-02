package com.thefacoteam.thefaco1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FgSecond2 extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FgSecond2 newInstance(int page, String title) {
        FgSecond2 fragment = new FgSecond2();
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
        View view = inflater.inflate(R.layout.fragment_second2, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView_second2);
        tvLabel.setText("대중교통 경로를 검색하세요!");

        ImageView iv = (ImageView) view.findViewById(R.id.iv2);
        Glide.with(this).load(R.raw.gif2).into(iv);
        return view;
    }
}