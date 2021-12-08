package com.example.veripark.Controllers;

import com.example.veripark.Models.HandshakeRequestModel;
import com.example.veripark.Models.HandshakeResponseModel;
import com.example.veripark.Models.StockRequestModel;
import com.example.veripark.Models.StockResponseModel;
import com.example.veripark.Models.Stocks;
import java.util.HashMap;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RestInterface {

    @Headers("Content-Type: application/json")
    @POST("/api/handshake/start")
    Call <HandshakeResponseModel> getHandShake(@Body HandshakeRequestModel body);

    @Headers("Content-Type: application/json")
    @POST("/api/stocks/list")
    Call <StockResponseModel> getStockList(@Body StockRequestModel body);

    @Headers("Content-Type: application/json")
    @POST("/api/stocks/detail")
    Call <Stocks> getStockDetail(@Body HashMap<String,String> map);

}

