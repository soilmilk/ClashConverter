package com.example.clashroyaleproject;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;


import com.example.clashroyaleproject.Sprite;

import java.util.ArrayList;

public class MyDiffUtilCallBack extends DiffUtil.Callback {

    private final ArrayList<Sprite> oldSS;
    private final ArrayList<Sprite> newSS;
    private final ArrayList<Integer> oldSSA;
    private final ArrayList<Integer> newSSA;

    public MyDiffUtilCallBack(ArrayList<Sprite> oldSS, ArrayList<Sprite> newSS, ArrayList<Integer> oldSSA, ArrayList<Integer> newSSA) {
        this.oldSS = oldSS;
        this.newSS = newSS;
        this.oldSSA = oldSSA;
        this.newSSA = newSSA;
    }

    @Override
    public int getOldListSize() {
        return this.oldSS.size();
    }

    @Override
    public int getNewListSize() {
        return this.newSS.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        //Checks if old/new sprites are the same or not
        return oldSS.get(oldItemPosition) == newSS.get(
                newItemPosition)  ;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        //checks if old/new sprites are the same amount
        //ex. 6 giant chest -> 5 giant chest
        return oldSSA.get(oldItemPosition) == newSSA.get(
                newItemPosition);
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        Bundle diff = new Bundle();
        diff.putInt("newValue", newSSA.get(newItemPosition));
        return diff;
    }
}
