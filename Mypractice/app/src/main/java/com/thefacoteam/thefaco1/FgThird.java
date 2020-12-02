package com.thefacoteam.thefaco1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

public class FgThird extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    // newInstance constructor for creating fragment with arguments
    public static FgThird newInstance(int page, String title) {
        FgThird fragment = new FgThird();
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
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        EditText tvLabel = (EditText) view.findViewById(R.id.editText3);
        tvLabel.setText(title);

        ImageView iv = (ImageView) view.findViewById(R.id.ivWoo);
        Glide.with(this).load(R.raw.gifwoo).into(iv);

        ImageView iv2 = (ImageView) view.findViewById(R.id.ivWoo2);
        Glide.with(this).load(R.raw.gifwoo2).into(iv2);

        return view;
    }
}