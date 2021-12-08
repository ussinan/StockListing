package com.example.veripark.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Stocks {

    public int getsNo() {
        return id;
    }

    @SerializedName("id")
    @Expose
    public int id = 0;

    @SerializedName("isDown")
    @Expose
    public Boolean isDown = false;

    @SerializedName("isUp")
    @Expose
    public Boolean isUp = false;

    @SerializedName("bid")
    @Expose
    public float bid = 0;

    @SerializedName("difference")
    @Expose
    public float difference = 0;

    @SerializedName("offer")
    @Expose
    public float offer = 0;


    @SerializedName("highest")
    @Expose
    public float highest = 0;


    @SerializedName("lowest")
    @Expose
    public float lowest = 0;


    @SerializedName("maximum")
    @Expose
    public float maximum = 0;


    @SerializedName("minimum")
    @Expose
    public float minimum = 0;

    @SerializedName("price")
    @Expose
    public float price = 0;

    @SerializedName("volume")
    @Expose
    public double volume = 0;

    @SerializedName("symbol")
    @Expose
    public String symbol = "";

    @SerializedName("graphicData")
    @Expose
    public List<GraphicData> graphicData = null;

}

