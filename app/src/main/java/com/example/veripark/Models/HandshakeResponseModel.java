package com.example.veripark.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class HandshakeResponseModel {

    @SerializedName("aesKey")
    @Expose
    public String aesKey = "";

    @SerializedName("aesIV")
    @Expose
    public String aesIV = "";

    @SerializedName("authorization")
    @Expose
    public String authorization = "";

    @SerializedName("lifeTime")
    @Expose
    public String lifeTime;

    @SerializedName("status")
    @Expose
    public Status status = new Status();

    public Boolean getIsSuccess(){
        return status.isSuccess;
    }

}

class Status {

    @SerializedName("isSuccess")
    @Expose
    public Boolean isSuccess = false;

    @SerializedName("error")
    @Expose
    public Error error = new Error();


}

class Error {

    @SerializedName("code")
    @Expose
    public int code = 0;

    @SerializedName("message")
    @Expose
    public String message = "" ;

}

