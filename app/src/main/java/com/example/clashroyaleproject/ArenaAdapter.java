package com.example.clashroyaleproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class ArenaAdapter extends BaseAdapter {

    private final Context context;
    private final int [] arenaIcons;
    public ArenaAdapter(int [] arenaIcons, Context context) {
        this.arenaIcons = arenaIcons;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arenaIcons.length;
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
        View rootview = LayoutInflater.from(context).inflate(R.layout.item_arenas, viewGroup, false);

        TextView arenaNumber = rootview.findViewById(R.id.arenaNumber);
        ImageView arenaIcon = rootview.findViewById(R.id.arenaImage);

        arenaNumber.setText(String.valueOf(i + 1));
        arenaIcon.setImageResource(arenaIcons[i]);

        return rootview;

    }


}

