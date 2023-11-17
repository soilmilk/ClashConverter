
package com.example.clashroyaleproject;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;


@Database(entities = {Chest.class}, version = 1)
public abstract class ChestDataBase extends RoomDatabase {

    public abstract ChestDAO chestDao();
    private static volatile ChestDataBase INSTANCE;

    //singleton
    public static ChestDataBase getInstance(Context context){
        if (INSTANCE == null){
            synchronized (ChestDataBase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ChestDataBase.class, "ChestDataBase").createFromAsset("database/chests.db").build();
                }
            }
        }
        return INSTANCE;
    }


}

