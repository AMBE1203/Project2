package vn.com.ambe.model;

/**
 * Created by duong on 10/05/2017.
 */

public class Result {
    private String formatted_address;

    public Result(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public Result() {
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }
}
