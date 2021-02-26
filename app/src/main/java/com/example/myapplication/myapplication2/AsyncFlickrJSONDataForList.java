package com.example.myapplication.myapplication2;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    private MyAdapter adapter;

    public AsyncFlickrJSONDataForList(MyAdapter adapter) {
        // assign the adapter
        this.adapter = adapter;
    }

    // same method as before
    @Override
    protected JSONObject doInBackground(String... params) {
        JSONObject result = null;
        try {
            URL url = new URL(params[0]);
            // connection to the url
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            try {
                // get the data from it
                InputStream is = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(is);
                // reinstantiate the data as a JSONObject
                result = new JSONObject(s);
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        try {
            // get JSON array of items
            JSONArray items = result.getJSONArray("items");
            // Loop through all the items
            for(int i = 0; i < items.length(); i++) {
                // get the image url
                String imageUrl = items.getJSONObject(i).getJSONObject("media").getString("m");
                // add url
                adapter.add(imageUrl);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // same method as before
    private String readStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            // read output stream to its end
            int i = inputStream.read();
            while(i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            return byteArrayOutputStream.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
