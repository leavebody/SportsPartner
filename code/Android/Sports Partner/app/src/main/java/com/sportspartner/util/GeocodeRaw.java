package com.sportspartner.util;

import java.util.ArrayList;

/**
 * @author Xiaochen Li
 */

public class GeocodeRaw {
    public ArrayList<GeocodeRaw.Result> results;
    public String status;

    @Override
    public String toString() {
        return "GeocodeRaw{" +
                "results=" + results +
                ", status='" + status + '\'' +
                '}';
    }

    public class Result {
        public ArrayList<Address_component> address_components;
        public String formatted_address;
        public String place_id;
        public ArrayList<String> types;


        @Override
        public String toString() {
            return "Result{" +
                    "address_components=" + address_components +
                    ", formatted_address='" + formatted_address + '\'' +
                    ", place_id='" + place_id + '\'' +
                    ", types='" + types + '\'' +
                    '}';
        }
        public class Address_component {
            public String long_name;
            public String short_name;
            public ArrayList<String> types;

            @Override
            public String toString() {
                return "Address_component{" +
                        "long_name='" + long_name + '\'' +
                        ", short_name='" + short_name + '\'' +
                        ", types=" + types +
                        '}';
            }
        }
    }
}
