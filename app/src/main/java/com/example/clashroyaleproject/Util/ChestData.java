package com.example.clashroyaleproject.Util;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class ChestData {

    @NonNull
    @ColumnInfo
    public float commons;

    @NonNull
    @ColumnInfo
    public float rares;

    @NonNull
    @ColumnInfo
    public float epics;

    @NonNull
    @ColumnInfo
    public float legendaries;

    @NonNull
    @ColumnInfo
    public float champions;


    @NonNull
    @ColumnInfo
    public int gold;

    @NonNull
    @ColumnInfo
    public int total_gold;
}
