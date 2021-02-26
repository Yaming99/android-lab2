package com.example.myapplication.myapplication2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncFlickrJSONData extends AsyncTask<String, Void, JSONObject> {
    // method similar to the previous one in MainActivity
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
            // get the image url
            String imageUrl = result.getJSONArray("items").getJSONObject(0).getJSONObject("media").getString("m");
            Log.i("JFL", imageUrl);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // same method as before
    private String readStream(InputStream inputStream) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int i = inputStream.read();
            // read output stream to its end
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