package com.example.manish.translinkapp.Models;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by manish on 2017-04-15.
 */

public class BusSchedule implements Serializable {

    private String destination_;
    private ArrayList<String> departureTime_;
    public BusSchedule(String destination, ArrayList<String> departureTime) {
        destination_ = destination;
        departureTime_ = departureTime;
    }
    public String getDestination() {
        return destination_;
    }
    public void setDestination(String val) {
        destination_ = val;
    }
    public ArrayList<String> getDepartureTime() {
        return departureTime_;
    }
    public void setDepartureTime(ArrayList<String> val) {
        departureTime_ = val;
    }
    @Override
    public boolean equals(Object o) {
       String comparer = (String)o;
       if(comparer.equals(destination_)) {
           return true;
       }
       return false;
    }
}
