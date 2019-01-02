package vn.com.ambe.model;

import java.util.ArrayList;

/**
 * Created by duong on 10/05/2017.
 */

public class Data {
    ArrayList<Result> results;

    public Data(ArrayList<Result> results) {
        this.results = results;
    }

    public Data() {
    }

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}
