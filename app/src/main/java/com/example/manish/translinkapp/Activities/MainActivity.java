package com.example.manish.translinkapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.manish.translinkapp.Logic.NextBus;
import com.example.manish.translinkapp.R;
import com.example.manish.translinkapp.ViewGenerators.TripPlanner;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void handleUserChoice(View v) {
        switch(v.getId())
        {
            case R.id.next_bus_btn:
                Intent nb = new Intent(this, NextBus.class);
                startActivity(nb);
                break;
            case R.id.plan_trip_btn:
                Intent tp = new Intent(this, TripPlanner.class);
                startActivity(tp);
                break;
            case R.id.quick_access_btn:
                Intent q = new Intent(this, QuickAccess.class);
                startActivity(q);
                break;
        }
    }
}
