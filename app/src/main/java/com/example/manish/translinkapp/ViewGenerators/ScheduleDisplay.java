package com.example.manish.translinkapp.ViewGenerators;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.manish.translinkapp.Extensions.ScheduleArrayAdapter;
import com.example.manish.translinkapp.Models.Bus;
import com.example.manish.translinkapp.R;

import java.util.ArrayList;

public class ScheduleDisplay extends AppCompatActivity {
    private ScheduleArrayAdapter adapter;
    ArrayList<Bus> busList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_display);
        Bundle bundle = getIntent().getBundleExtra("bundle");
        busList = (ArrayList<Bus>)bundle.getSerializable("busList");
        populateSchedules();
    }
    private void populateSchedules() {
        adapter = new ScheduleArrayAdapter(this, busList);
        ExpandableListView lv = (ExpandableListView) findViewById(R.id.bus_schedule_list);
        lv.setAdapter(adapter);
    }
}
