package com.example.manish.translinkapp.Logic;

import android.os.AsyncTask;
import android.util.Log;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by manish on 2017-04-14.
 */
public class RestOperation extends AsyncTask<String,String,BufferedReader> {

    private final String API_KEY = "P82ydVMVrEnYmvC471Wz";
    private BufferedReader br;
    private String urlFilter_;
    public RestOperation() {
        urlFilter_ = null;
    }
    public RestOperation(String filter) {
        urlFilter_ = filter;
    }

    @Override
    protected BufferedReader doInBackground(String... restUrl) {
        String fullURI = "";
        try {
            if(urlFilter_ != null) {
                fullURI = restUrl[0] + API_KEY + urlFilter_;
            }
            else {
                fullURI = restUrl[0] + API_KEY;
            }
            URL url = new URL(fullURI);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.connect();
            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            return br;
        } catch (Exception e) {
            Log.e("Exception Occured", e.toString());
            return null;
        }
    }
}
