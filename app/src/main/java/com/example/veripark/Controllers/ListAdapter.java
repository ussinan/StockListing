package com.example.veripark.Controllers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.veripark.Models.StockResponseModel;
import com.example.veripark.Models.Stocks;
import com.example.veripark.R;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    public StockResponseModel stockModel;
    StockListActivity activity;
    private Context context;


    public ListAdapter(StockListActivity activity, StockResponseModel stockList, Context context) {
        super();
        this.activity = activity;
        this.stockModel = stockList;
        this.context = context;
    }

    @Override
    public int getCount() {
        if(stockModel.stocks.size() == 0){
            return 0;
        }else{
            return stockModel.stocks.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return stockModel.stocks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        TextView tSymbol;
        TextView tPrice;
        TextView tDifference;
        TextView tVolume;
        TextView tBuy;
        TextView tSell;
        TextView tChange;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.table_view, null);
            holder = new ViewHolder();
            holder.tSymbol = (TextView) convertView.findViewById(R.id.symbol);
            holder.tPrice = (TextView) convertView.findViewById(R.id.price);
            holder.tDifference = (TextView) convertView
                    .findViewById(R.id.difference);
            holder.tVolume = (TextView) convertView.findViewById(R.id.volume);
            holder.tBuy = (TextView) convertView.findViewById(R.id.buy);
            holder.tSell = (TextView) convertView.findViewById(R.id.sell);
            holder.tChange = (TextView) convertView.findViewById(R.id.change);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Stocks stockItem = stockModel.stocks.get(position);

        String symbol = CryptionService.decrypt(stockItem.symbol);

        holder.tSymbol.setText(symbol);
        holder.tPrice.setText(String.valueOf(stockItem.price));
        holder.tDifference.setText(String.valueOf(stockItem.difference));
        holder.tVolume.setText(String.valueOf(stockItem.volume));
        holder.tBuy.setText(String.valueOf(stockItem.bid));
        holder.tSell.setText(String.valueOf(stockItem.offer));

        if(stockItem.isUp){
            holder.tChange.setText("^");
            holder.tChange.setTextColor(context.getResources().getColor(R.color.green));
        }else if(stockItem.isDown){
            holder.tChange.setText("v");
            holder.tChange.setTextColor(context.getResources().getColor(R.color.red));
        }else{
            holder.tChange.setText("-");
            holder.tChange.setTextColor(context.getResources().getColor(R.color.black));
        }

        return convertView;
    }
}
