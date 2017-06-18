package com.example.manish.translinkapp.ViewGenerators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.example.manish.translinkapp.R;

public class TripPlanner extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_planner);
        linkToTranslinkTP();
    }
    private void linkToTranslinkTP() {
        WebView wv = (WebView)findViewById(R.id.trip_planner);
        String iframeHtml = "<iframe src=\"http://www.translink.ca/Utilities/Embedded Trip Planner.aspx\" scrolling=\"no\" width=\"340\" height=\"400\">" +
                "</iframe>";
        CookieManager.getInstance().setAcceptCookie(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setVisibility(View.VISIBLE);
        wv.loadData(iframeHtml,"text/html",null);

    }
}
