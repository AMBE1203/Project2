package vn.com.ambe.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by duong on 09/04/2017.
 */

public class Route {
    private ArrayList<Leg> legs;
    private Overview_polyline overview_polyline;

    public Route(ArrayList<Leg> legs, Overview_polyline overview_polyline) {
        this.legs = legs;
        this.overview_polyline = overview_polyline;
    }

    public Route() {
    }

    public ArrayList<Leg> getLegs() {
        return legs;
    }

    public void setLegs(ArrayList<Leg> legs) {
        this.legs = legs;
    }

    public Overview_polyline getOverview_polyline() {
        return overview_polyline;
    }

    public void setOverview_polyline(Overview_polyline overview_polyline) {
        this.overview_polyline = overview_polyline;
    }
}
