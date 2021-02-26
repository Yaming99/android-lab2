package com.example.myapplication.myapplication2;

import android.view.View;
import android.widget.ImageView;

public class GetImageOnClickListener implements View.OnClickListener {
    private ImageView imageView;

    public GetImageOnClickListener(ImageView iv) {
        // assign the image view
        this.imageView = iv;
    }

    @Override
    public void onClick(View v) {
        new AsyncFlickrJSONData(new AsyncBitmapDownloader(imageView)).execute("https://www.flickr.com/services/feeds/photos_public.gne?tags=trees&format=json&nojsoncallback=?");
    }
}
