package com.example.veripark.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.veripark.Models.HandshakeResponseModel;
import com.example.veripark.Models.StockRequestModel;
import com.example.veripark.Models.StockResponseModel;
import com.example.veripark.Models.Stocks;
import com.example.veripark.R;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockListActivity extends AppCompatActivity implements View.OnClickListener {

    private String type = "";
    private RestInterface restInterface;

    private StockRequestModel req = new StockRequestModel();
    private StockResponseModel stockResponse = new StockResponseModel();
    private Stocks stockModel = new Stocks();


    private Call<StockResponseModel> call;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_stock_list);
        findViewById(R.id.back).setOnClickListener(this);

        type = getIntent().getStringExtra("type");

        listView = (ListView) findViewById(R.id.listview);

        try {
            connect();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setAdapter(){
        ListAdapter adapter = new ListAdapter(this, stockResponse,getBaseContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Call<Stocks> newCall;


                String encryptedId = CryptionService.encrypt(String.valueOf(stockResponse.stocks.get(position).id));

                HashMap<String,String> map = new HashMap<>();
                map.put("id",encryptedId);

                newCall = restInterface.getStockDetail(map);
                newCall.enqueue(new Callback<Stocks>() {
                    @Override
                    public void onResponse(Call<Stocks> call, Response<Stocks> response) {

                        stockModel = response.body();

                        Intent intent = new Intent(StockListActivity.this, StockDetailActivity.class);
                        Gson gson = new Gson();
                        String detailModel = gson.toJson(stockModel);
                        intent.putExtra("detailModel", detailModel);
                        StockListActivity.this.startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<Stocks> call, Throwable t) { }

                });
            }
        });
    }


    private void connect(){
        restInterface= ApiClient.getClient(null).create(RestInterface.class);

        req.period = type;

        try {
            call = restInterface.getStockList(req);
        } catch (Exception e) {
            e.printStackTrace();
        }

        call.enqueue(new Callback<StockResponseModel>() {
            @Override
            public void onResponse(Call<StockResponseModel> call, Response<StockResponseModel> response) {
                stockResponse = response.body();
                if(stockResponse != null){
                    setAdapter();
                }
            }

            @Override
            public void onFailure(Call<StockResponseModel> call, Throwable t) {
            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.back){
            onBackPressed();
        }

    }
}