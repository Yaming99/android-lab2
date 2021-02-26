package com.example.myapplication.myapplication2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.RequestQueue;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    private Vector<String> imageUrls;

    public MyAdapter() {
        imageUrls = new Vector<>();
    }

    // add url
    public void add(String url) {
        imageUrls.add(url);
        Log.i("JFL", "Adding to adapter url: " + url);
    }

    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RequestQueue queue = MySingleton.getInstance(parent.getContext()).getRequestQueue();
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bitmaplayout, parent, false);
        }
        // get the image url
        String imageUrl = (String) getItem(position);
        return convertView;
    }
}
