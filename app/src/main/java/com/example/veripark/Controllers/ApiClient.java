package com.example.veripark.Controllers;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient{

    private static Retrofit retrofit = null;
    private static String Base_Url = "https://mobilechallenge.veripark.com/";
    private static String token = "";

    public static Retrofit getClient(String auth) {

        retrofit = null;
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if(token == null || token == "" && auth != null && auth != ""){
            token = auth;
        }

        if (token != null && token != ""){

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().addHeader("X-VP-Authorization", token).build();
                    return chain.proceed(request);
                }
            });

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(Base_Url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(httpClient.build())
                        .build();
                return retrofit;
            }

        }
        else{

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(Base_Url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(new OkHttpClient())
                        .build();
                return retrofit;
            }

        }
        return retrofit;

    }

    public static void setToken(String tok){
        token = tok;
    }

}