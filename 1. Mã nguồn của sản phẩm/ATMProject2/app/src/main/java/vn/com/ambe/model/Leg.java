package vn.com.ambe.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by duong on 11/04/2017.
 */

public class Leg {
    private Distance distance;
    private Duration duration;
    private String end_address;
    private String start_address;
    private LatLng end_location;
    private LatLng start_location;


    public Leg(Distance distance, Duration duration, String end_address, String start_address, LatLng end_location, LatLng start_location, ArrayList<LatLng> points) {
        this.distance = distance;
        this.duration = duration;
        this.end_address = end_address;
        this.start_address = start_address;
        this.end_location = end_location;
        this.start_location = start_location;
    }

    public Leg() {
    }

    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public LatLng getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LatLng end_location) {
        this.end_location = end_location;
    }

    public LatLng getStart_location() {
        return start_location;
    }

    public void setStart_location(LatLng start_location) {
        this.start_location = start_location;
    }


}
