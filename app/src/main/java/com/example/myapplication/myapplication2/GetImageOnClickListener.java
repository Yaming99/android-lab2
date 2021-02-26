package com.example.myapplication.myapplication2;

import android.view.View;

public class GetImageOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        new AsyncFlickrJSONData().execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json");
    }
}
