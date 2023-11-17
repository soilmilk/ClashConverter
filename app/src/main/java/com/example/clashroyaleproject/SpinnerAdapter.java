package com.example.clashroyaleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;



public class SpinnerAdapter extends ArrayAdapter<Integer> {

    private String [] item, gem;
    private final String type;
    private final int [] colors;
    LayoutInflater layoutInflater;
    public SpinnerAdapter(@NonNull Context context, int resource, String [] item, String [] gem, String type, int [] colors) {
        super(context, resource);
        layoutInflater = LayoutInflater.from(context);
        this.gem = gem;
        this.colors = colors;
        this.item = item;
        this.type = type;
    }



    @Override
    public int getCount() {
        return item.length;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowview = layoutInflater.inflate(R.layout.item_to_gem_item, null, true);

        ImageView itemImageView = rowview.findViewById(R.id.iv_item);
        TextView shopItem = rowview.findViewById(R.id.tv_shopItem);
        TextView shopGem = rowview.findViewById(R.id.tv_shopGem);

        String string = item[position];
        switch(type){
            case "currencyToGem":
                shopItem.setTextColor(colors[1]);
                //shopItem.setTextColor(Color.parseColor("#F9FBF7"));
                itemImageView.setVisibility(View.GONE);
                break;
            case "goldToGem":
                shopItem.setTextColor(colors[0]);
                itemImageView.setImageResource(R.drawable.gold);
                break;
        }


        shopItem.setText(string);
        shopGem.setText(gem[position]);

        return rowview;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.item_to_gem_item, parent, false);
        }

        ImageView itemImageView = convertView.findViewById(R.id.iv_item);
        TextView shopItem = convertView.findViewById(R.id.tv_shopItem);
        TextView shopGem = convertView.findViewById(R.id.tv_shopGem);

        String string = item[position];
        switch(type){
            case "currencyToGem":
                shopItem.setTextColor(colors[1]);
                itemImageView.setVisibility(View.GONE);
                break;
            case "goldToGem":
                shopItem.setTextColor(colors[0]);
                itemImageView.setImageResource(R.drawable.gold);
                break;
        }
        shopItem.setText(string);
        shopGem.setText(gem[position]);


        return convertView;
    }

    public void changeCurrency(String [] newCurrency){
        item = newCurrency;
        notifyDataSetChanged();
    }
}
