package com.example.veripark.Controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.example.veripark.Models.Stocks;
import com.example.veripark.R;
import com.google.gson.Gson;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Arrays;
import retrofit2.Call;

public class StockDetailActivity extends AppCompatActivity implements View.OnClickListener{

    private int stockId = 0;
    private Call<Stocks> call;
    private Stocks stockModel;
    private XYPlot plot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        findViewById(R.id.back).setOnClickListener(this);

        Gson gson = new Gson();
        stockModel = gson.fromJson(getIntent().getStringExtra("detailModel"), Stocks.class);

        plot = findViewById(R.id.plot);
        setViewItems();
        findViewById(R.id.root).invalidate();
    }

    public void setViewItems(){

        String symbol = CryptionService.decrypt(stockModel.symbol);

        DecimalFormat df = new DecimalFormat("0.000");
        String vol = df.format(stockModel.volume);


        ((TextView)findViewById(R.id.symbol)).setText("Sembol : "+symbol);
        ((TextView)findViewById(R.id.price)).setText("Fiyat : " + stockModel.price);
        ((TextView)findViewById(R.id.difference)).setText("%Fark : " + stockModel.difference);
        ((TextView)findViewById(R.id.volume)).setText("Hacim : " + vol);
        ((TextView)findViewById(R.id.buy)).setText("Alış : " +stockModel.bid);
        ((TextView)findViewById(R.id.sell)).setText("Satış : " +stockModel.offer);
        ((TextView)findViewById(R.id.lowest)).setText("Günlük Düşük : " +stockModel.lowest);
        ((TextView)findViewById(R.id.highest)).setText("Günlük Yüksek : " +stockModel.highest);
        ((TextView)findViewById(R.id.peak)).setText("Tavan : " +stockModel.maximum);
        ((TextView)findViewById(R.id.bottom)).setText("Taban : " +stockModel.highest);

        if(stockModel.isUp){
            ((TextView)findViewById(R.id.changeIcon)).setText("^");
            ((TextView)findViewById(R.id.changeIcon)).setTextColor(getResources().getColor(R.color.green));
        }else if(stockModel.isDown){
            ((TextView)findViewById(R.id.changeIcon)).setText("v");
            ((TextView)findViewById(R.id.changeIcon)).setTextColor(getResources().getColor(R.color.red));
        }else{
            ((TextView)findViewById(R.id.changeIcon)).setText("-");
            ((TextView)findViewById(R.id.changeIcon)).setTextColor(getResources().getColor(R.color.black));
        }

        final Number[] values = new Number[stockModel.graphicData.size()];
        final Number[] days = new Number[stockModel.graphicData.size()];

        for(int i = 0;i<stockModel.graphicData.size();i++){
            values[i]=Math.round(stockModel.graphicData.get(i).value*100.0)/100.0;
        }

        XYSeries seriesVal = new SimpleXYSeries(Arrays.asList(values),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY,null);
        LineAndPointFormatter valFormat = new LineAndPointFormatter(Color.BLACK,Color.BLACK,getResources().getColor(R.color.light_red),null);

        plot.addSeries(seriesVal,valFormat);

        plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
            @Override
            public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                int i = Math.round( ((Number)obj).floatValue());
                return toAppendTo.append(values[i]);
            }

            @Override
            public Object parseObject(String source, ParsePosition pos) {
                return null;
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

        if(v.getId() == R.id.back){
            onBackPressed();
        }

    }
}