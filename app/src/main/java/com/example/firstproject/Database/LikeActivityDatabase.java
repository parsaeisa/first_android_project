package com.example.firstproject.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.firstproject.Models.LikeActivity;


@Database(entities = LikeActivity.class , version = 3 , exportSchema = false)
public abstract class LikeActivityDatabase extends RoomDatabase {
    public abstract LikeActivityDao LikeDao();

    public static final String DATABASE_NAME = "Likes";

    public static  LikeActivityDatabase instance;

    public static LikeActivityDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,LikeActivityDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
