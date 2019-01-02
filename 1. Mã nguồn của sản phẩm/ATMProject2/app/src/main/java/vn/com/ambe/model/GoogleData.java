package vn.com.ambe.model;

import java.util.ArrayList;

/**
 * Created by duong on 11/04/2017.
 */

public class GoogleData {
    private ArrayList<Route> routes;

    public GoogleData(ArrayList<Route> routes) {
        this.routes = routes;
    }

    public GoogleData() {
    }

    public ArrayList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(ArrayList<Route> routes) {
        this.routes = routes;
    }
}
