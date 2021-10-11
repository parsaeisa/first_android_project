package com.example.firstproject.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.firstproject.Models.Post;

@Database(entities = Post.class , version = 5 , exportSchema = false)
public abstract class PostsDatabase extends RoomDatabase {

    public abstract PostsDatabaseDao PostDao();

    public static final String DATABASE_NAME = "PostsDB";

    public static  PostsDatabase instance;

    public static PostsDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context,PostsDatabase.class,DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}
