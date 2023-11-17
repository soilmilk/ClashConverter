package com.example.clashroyaleproject;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.clashroyaleproject.Util.ChestData;


@Dao
public interface ChestDAO {



    @Query("SELECT commons, rares, epics, legendaries, champions, gold, total_gold from chests where arena == :arena and name == :name ") //where chest_name == :chest_name
    public ChestData getChestData(String name, int arena);


    @Query("SELECT total_gold from chests where arena == :arena and name == :name ")
    public int getTotalChestGold(String name, int arena);






}


