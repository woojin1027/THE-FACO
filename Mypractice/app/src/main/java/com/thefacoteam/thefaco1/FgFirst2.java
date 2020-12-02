package com.thefacoteam.thefaco1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FgFirst2 extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    private ImageView test;
    // newInstance constructor for creating fragment with arguments
    public static FgFirst2 newInstance(int page, String title) {
        FgFirst2 fragment = new FgFirst2();
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
        View view = inflater.inflate(R.layout.fragment_first2, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.textView_first2);
        tvLabel.setText("버스를 선택해보세요!");

        ImageView iv = (ImageView) view.findViewById(R.id.iv);
        Glide.with(this).load(R.raw.gif1).into(iv);
        return view;
    }
}