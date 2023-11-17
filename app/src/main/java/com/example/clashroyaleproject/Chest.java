package com.example.clashroyaleproject;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;



@Entity(tableName = "chests")
public class Chest  {

    @PrimaryKey
    @NonNull
    @ColumnInfo
    public int id;


    @NonNull
    @ColumnInfo
    public String name;

    @NonNull
    @ColumnInfo
    public int arena;

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


