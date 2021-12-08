package com.example.veripark.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GraphicData {

    @SerializedName("day")
    @Expose
    public int day = 0;

    @SerializedName("value")
    @Expose
    public float value = 0;

}
