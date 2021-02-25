package com.example.myapplication.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogin(View view) {
        // surround it in a thread
        new Thread() {
            public void run() {
                URL url;
                try {
                    // change link
                    url = new URL("https://httpbin.org/basic-auth/bob/sympa");
                    HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
                    String basicAuth = "Basic " + Base64.encodeToString("bob:sympa".getBytes(), Base64.NO_WRAP);
                    urlConnection.setRequestProperty ("Authorization", basicAuth);
                    // get the content of the page
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        Log.i("JFL", s);
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

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