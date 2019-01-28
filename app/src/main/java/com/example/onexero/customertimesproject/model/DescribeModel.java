package com.example.onexero.customertimesproject.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DescribeModel {

    @SerializedName("fields")
    @Expose
    private ArrayList<NameModel> names = new ArrayList<NameModel>();

    public ArrayList<NameModel> getNames() {
        return names;
    }

    public void setNames(ArrayList<NameModel> names) {
        this.names = names;
    }

}
