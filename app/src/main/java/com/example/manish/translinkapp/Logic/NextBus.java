package com.example.manish.translinkapp.Logic;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manish.translinkapp.Models.Bus;
import com.example.manish.translinkapp.Models.BusSchedule;
import com.example.manish.translinkapp.R;
import com.example.manish.translinkapp.ViewGenerators.ScheduleDisplay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class NextBus extends AppCompatActivity {

    private EditText stopNoField_;
    private int busCount_;
    private final String EMPTY_STOPNO_FIELD = "Please enter a stop number";
    private final String INCORRECT_STOPNO_FIELD = "There was an error getting schedules for this stop";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_bus);
        addSeekBarListener();
        addEnterKeyListener();
    }

    public void onEnterClick(View v) {
        listBusTimes();
    }
    private void addEnterKeyListener() {
        stopNoField_  = (EditText)findViewById(R.id.stop_no_field);
        stopNoField_.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    //trigger our list bus function on enter press
                    listBusTimes();
                    return true;
                }
                return false;
            }
        });
    }
    private void addSeekBarListener() {
        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        final TextView busNoDisplay = (TextView)findViewById(R.id.bus_no_display);
        busCount_ = seekBar.getProgress();
        busNoDisplay.setText("Next " + busCount_ + " schedule(s)");
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                if (progress < 1) {
                    seekBar.setProgress(1);
                    progress = 1;
                }
                busCount_ = progress;
                busNoDisplay.setText("Next " + busCount_ + " schedule(s)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private void listBusTimes() {
        RestOperation ro = new RestOperation("&count=" + busCount_);
        String stopNo = stopNoField_.getText().toString();
        if(!stopNo.isEmpty()) {
            try {
                BufferedReader br = ro.execute("https://api.translink.ca/rttiapi/v1/stops/" + stopNo + "/estimates?apikey=").get();
                if(br != null) {
                    ListBuses busList = new ListBuses(getApplicationContext(), busCount_);
                    ArrayList<Bus> buses = busList.execute(br).get();
                    Intent i = new Intent(this, ScheduleDisplay.class);
                    Bundle args = new Bundle();
                    args.putSerializable("busList",buses);
                    i.putExtra("bundle",args);
                    startActivity(i);
                }
                else {
                    Toast.makeText(getApplicationContext(), INCORRECT_STOPNO_FIELD, Toast.LENGTH_SHORT).show();
                }
            }
            catch(Exception e) {
                Log.e("Exception in Nextbus", e.toString());
            }
        }
        else {
            Toast.makeText(getApplicationContext(),EMPTY_STOPNO_FIELD, Toast.LENGTH_SHORT).show();
        }

    }
}
class ListBuses extends AsyncTask<BufferedReader,String, ArrayList<Bus> > {
    private String data;
    private Context context_;
    private int busCount_;
    public ListBuses(Context context, int busCount) {
        context_ = context;
        busCount_ = busCount;
    }
    @Override
    protected ArrayList<Bus> doInBackground(BufferedReader... readers) {
        BufferedReader rd = readers[0];
        ArrayList<Bus> busList = null;
        try {
            while((data = rd.readLine()) != null) {
                busList = processPayload();
            }
            return busList;
        }
        catch(Exception e) {
            Log.e("Exception in ListBuses",e.toString());
            return null;
        }
    }
    @Override
    public void onProgressUpdate(String...data) {
       Toast.makeText(context_, data[0], Toast.LENGTH_LONG).show();
    }

    private ArrayList<Bus> processPayload() throws JSONException {
        ArrayList busList = new ArrayList<>();
        //deserialize and handle payload
        JSONArray array = new JSONArray(data);
        for(int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            String routeNo = obj.getString("RouteNo");
            JSONArray schedules = obj.getJSONArray("Schedules");
            HashMap<String,BusSchedule> departureTimes = new HashMap<>();
            for(int j = 0; j < schedules.length(); j++) {
                JSONObject busSchedule = schedules.getJSONObject(j);
                String dest = busSchedule.getString("Destination");
                try {
                    String formattedTime = convertStringToDateTime(busSchedule.getString("ExpectedLeaveTime"));
                    if(!departureTimes.containsKey(dest)) {
                        ArrayList<String> times = new ArrayList<>();
                        times.add(formattedTime);
                        departureTimes.put(dest,new BusSchedule(dest,times));
                    }
                    else {
                        departureTimes.get(dest).getDepartureTime().add(formattedTime);
                    }
                } catch (ParseException e) {
                    Log.e("Exception in processing",e.toString());
                }
            }
            for(Map.Entry<String,BusSchedule> sc : departureTimes.entrySet()) {
                busList.add(new Bus(routeNo,sc.getValue()));
            }
        }
        return busList;
    }

    private String convertStringToDateTime(String date) throws ParseException {
        SimpleDateFormat smf = new SimpleDateFormat("hh:mmaa", Locale.CANADA);
        Date d = smf.parse(date);
        String parsedDate = smf.format(d);
        //Strip leading 0 from hour if it exists
        parsedDate = parsedDate.replaceFirst("^0*", "");
        return parsedDate;
    }
}
