package com.example.myapplication.myapplication2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewWeakReference;

    public AsyncBitmapDownloader(ImageView iv) {
        this.imageViewWeakReference = new WeakReference<>(iv);
    }

    // method similar to the one in AsyncFlickrJSONData.java
    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bmp = null;
        try {
            // connection to the url
            URL url = new URL(params[0]);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            try {
                // get the data from it
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                // reinstantiate the data as a Bitmap
                bmp = BitmapFactory.decodeStream(is);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    @Override
    protected void onPostExecute(Bitmap bmp) {
        // update the image
        imageViewWeakReference.get().setImageBitmap(bmp);
    }
}
