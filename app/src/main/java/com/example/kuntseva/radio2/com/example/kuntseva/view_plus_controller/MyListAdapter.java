package com.example.kuntseva.radio2.com.example.kuntseva.view_plus_controller;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class MyListAdapter extends SimpleAdapter {
    String[] from_headers1;
    int[] to_RealViews1;
    List<? extends Map<String, ?>> data1;


    public MyListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.from_headers1 = from;
        this.to_RealViews1 = to;
        this.data1 = data;
    }


    @Override
    public void setViewText(TextView v, String text) {
        super.setViewText(v, text);
    }


    @Override
    public void setViewImage(ImageView v, int value) {
        super.setViewImage(v, value);
    }


}
