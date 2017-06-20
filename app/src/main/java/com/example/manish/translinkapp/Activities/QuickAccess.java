package com.example.manish.translinkapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.manish.translinkapp.Logic.Database.DBReader;
import com.example.manish.translinkapp.Logic.NextBus;
import com.example.manish.translinkapp.R;

import java.util.List;

public class QuickAccess extends AppCompatActivity {
    private Context _access;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_access);
        _access = getApplicationContext();
        populateStopNumbers();
    }
    private void populateStopNumbers() {
        ListView savedStops = (ListView)findViewById(R.id.stop_list);
        try {
            List<String> list = new DBReader(this).readDataFromDB();
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,list);
            savedStops.setAdapter(adapter);
            setClickListener(savedStops);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong getting your saved data, try again",Toast.LENGTH_LONG).show();
        }
    }
    private void setClickListener(final ListView listView) {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String stop = (String)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(_access, NextBus.class);
                intent.putExtra("StopNumber",stop);
                startActivity(intent);
            }
        });
    }
}
