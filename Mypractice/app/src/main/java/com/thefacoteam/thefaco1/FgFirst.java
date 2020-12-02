package com.thefacoteam.thefaco1;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

public class FgFirst extends Fragment {
    // Store instance variables
    private String title;
    private int page;

    private ImageView test;
    // newInstance constructor for creating fragment with arguments
    public static FgFirst newInstance(int page, String title) {
        FgFirst fragment = new FgFirst();
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
        View view = inflater.inflate(R.layout.fragment_first, container, false);

        EditText tvLabel = (EditText) view.findViewById(R.id.editText1);
        tvLabel.setText(title);
        
        return view;
    }
}