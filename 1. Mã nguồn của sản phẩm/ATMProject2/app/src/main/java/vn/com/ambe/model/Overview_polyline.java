package vn.com.ambe.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by duong on 12/04/2017.
 */

public class Overview_polyline {
    private String points;

    public Overview_polyline(String points) {
        this.points = points;
    }

    public Overview_polyline() {
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
