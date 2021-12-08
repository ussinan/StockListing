package com.example.veripark.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class StockResponseModel {


    @SerializedName("status")
    @Expose
    public Status status = new Status();

    @SerializedName("stocks")
    @Expose
    public List<Stocks> stocks = null;

}

