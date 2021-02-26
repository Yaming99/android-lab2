package com.example.myapplication.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    // authentication state
    private boolean res;

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
                    // user credentials
                    EditText username = findViewById(R.id.username);
                    EditText password = findViewById(R.id.password);
                    String basicAuth = "Basic " + Base64.encodeToString((username.getText() + ":" + password.getText()).getBytes(), Base64.NO_WRAP);
                    urlConnection.setRequestProperty ("Authorization", basicAuth);
                    // get the content of the page
                    try {
                        InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                        String s = readStream(in);
                        Log.i("JFL", s);
                        // reinstantiate the string as a JSONObject
                        JSONObject jsonObject = new JSONObject(s);
                        res = jsonObject.getBoolean("authenticated");
                        // refresh the result
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView result = findViewById(R.id.result);
                                if (MainActivity.this.res) result.setText("Authenticated");
                                else result.setText("Not authenticated");
                            }
                        });
                    } finally {
                        urlConnection.disconnect();
                    }
                } catch (IOException | JSONException e) {
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