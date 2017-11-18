package com.sportspartner.models;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class MapApiResult {
    private String zipcode;
    private ArrayList<String> addresses;

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public ArrayList<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "MapApiResult{" +
                "zipcode='" + zipcode + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}
