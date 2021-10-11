package com.example.firstproject.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.firstproject.Models.User;

@Database(entities = {User.class}, version = 7 , exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDatabaseDao userDao();

    public static final String DATABASE_NAME = "users";

    public static  UserDatabase instance;

    public static UserDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,UserDatabase.class,DATABASE_NAME)
                                .fallbackToDestructiveMigration()
                                .build();
        }

        return instance;
    }
}
