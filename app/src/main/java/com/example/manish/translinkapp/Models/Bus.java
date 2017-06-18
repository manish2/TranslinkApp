package com.example.manish.translinkapp.Models;

import android.text.style.BulletSpan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manish on 2017-04-14.
 */

public class Bus implements Serializable {
    private String routeNo_;
    private BusSchedule busSchedule_;
    public Bus() {

    }
    /**
     * Creates a new Bus object
     */
    public Bus(String routeNo, BusSchedule busSchedule) {
        routeNo_ = routeNo;
        busSchedule_ = busSchedule;
    }
    public String getRouteNo() {
        return routeNo_;
    }
    public void setRouteNo(String val) {
        routeNo_ = val;
    }
    public BusSchedule getBusSchedule() {
        return busSchedule_;
    }
    /*public ArrayList<BusSchedule> getBusSchedules() {
        return busSchedules_;
    }*/
    public void setBusSchedule(BusSchedule busSchedule){
        busSchedule_ = busSchedule;
    }
    /*public void setBusSchedules(ArrayList<BusSchedule> val) {
        busSchedules_ = val;
    }*/
}
