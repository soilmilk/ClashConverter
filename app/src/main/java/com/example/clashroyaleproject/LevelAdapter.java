package com.example.clashroyaleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LevelAdapter extends BaseAdapter {

    private int [] items;
    private Context context;
    private int image;


    public LevelAdapter(int [] items, Context context, int image) {
        this.items = items;
        this.context = context;
        this.image = image;
    }

    @Override
    public int getCount() {
        return items != null ? items.length: 0;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rootview = LayoutInflater.from(context).inflate(R.layout.item_level, viewGroup, false);

        int padding = 0;
        if (image == R.drawable.wc_rare) {
            padding += 2;
        } else if (image == R.drawable.wc_epic) {
            padding += 5;
        } else if (image == R.drawable.wc_legendary) {
            padding += 8;
        } else if (image == R.drawable.wc_champion) {
            padding += 10;
        }
        TextView levelStart = rootview.findViewById(R.id.tv_level_start);
        TextView levelEnd = rootview.findViewById(R.id.tv_level_end);
        TextView amount = rootview.findViewById(R.id.tv_amount);

        levelStart.setText(String.valueOf(items.length - i+padding));
        levelEnd.setText(String.valueOf(items.length - i+1+padding));
        amount.setText(String.valueOf( items[items.length-i-1]));



        ImageView cardOrGold = rootview.findViewById(R.id.iv_card_or_gold);
        cardOrGold.setImageResource(image);

        return rootview;
    }
}
