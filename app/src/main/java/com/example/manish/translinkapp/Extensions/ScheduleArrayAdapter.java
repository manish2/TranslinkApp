package com.example.manish.translinkapp.Extensions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.manish.translinkapp.Models.Bus;
import com.example.manish.translinkapp.R;

import java.util.ArrayList;

/**
 * Created by manish on 2017-04-16.
 */

public class ScheduleArrayAdapter extends BaseExpandableListAdapter {
    private Context callerContext_;
    private ArrayList<Bus> busList_;
    public ScheduleArrayAdapter(Context context, ArrayList<Bus> busList) {
        callerContext_ = context;
        busList_ = busList;
    }
    @Override
    public int getGroupCount() {
        return busList_.size();
    }

    @Override
    public int getChildrenCount(int i) {
        ArrayList<String> busTimes = busList_.get(i).getBusSchedule().getDepartureTime();
        return busTimes.size();
    }

    @Override
    public Object getGroup(int i) {
        return busList_.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        ArrayList<String> busTimes = busList_.get(i).getBusSchedule().getDepartureTime();
        return busTimes.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        Bus bus = (Bus)getGroup(i);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)callerContext_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_bus, null);
        }
        TextView tv = (TextView)view.findViewById(R.id.route_no);
        tv.setText(bus.getRouteNo() + " " + bus.getBusSchedule().getDestination());
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        String timing = (String)getChild(i,i1);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater)callerContext_.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.bus_time, null);
        }
        TextView arrivalTime = (TextView)view.findViewById(R.id.bus_arrival_time);
        arrivalTime.setText(timing);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
