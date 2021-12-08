package com.example.veripark.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.veripark.Models.HandshakeRequestModel;
import com.example.veripark.Models.HandshakeResponseModel;
import com.example.veripark.R;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LaunchActivity extends AppCompatActivity {

    RestInterface restInterface;

    private HandshakeRequestModel request;
    private Call<HandshakeResponseModel> call;
    private Boolean isSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        request = new HandshakeRequestModel();

        request.deviceId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        request.systemVersion = String.valueOf(Build.VERSION.SDK_INT);
        request.platformName = "Android";
        request.deviceModel = Build.MODEL;
        request.manifacturer = Build.MANUFACTURER;

        restInterface= ApiClient.getClient("").create(RestInterface.class);

        try {

            call = restInterface.getHandShake(request);

        } catch (Exception e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback<HandshakeResponseModel>() {
            @Override
            public void onResponse(Call<HandshakeResponseModel> call, Response<HandshakeResponseModel> response) {

                HandshakeResponseModel handshakeResponse = response.body();
                ApiClient.setToken(handshakeResponse.authorization);

                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                Gson gson = new Gson();
                String handshakeModel = gson.toJson(handshakeResponse);
                intent.putExtra("handshakeModel", handshakeModel);
                intent.putExtra("isSuccess", isSuccess);

                LaunchActivity.this.startActivity(intent);

            }

            @Override
            public void onFailure(Call<HandshakeResponseModel> call, Throwable t) {
                Log.d("handshake","response error");

                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                intent.putExtra("isSuccess", false);
            }

        });

    }
}