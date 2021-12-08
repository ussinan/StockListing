package com.example.veripark.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;


public class HandshakeRequestModel {

    public String deviceId = "";
    public String systemVersion = "";
    public String platformName = "";
    public String deviceModel = "";
    public String manifacturer = "";

}
