package com.example.firstproject.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.firstproject.Models.LikeActivity;

import java.util.List;

@Dao
public interface LikeActivityDao {

    @Insert
    void InsertLikeActivity (LikeActivity likeActivity );

    @Query("select * from likes")
    List<LikeActivity> getAllLikes ();

    @Delete
    void deleteLike (LikeActivity likeActivity);
}
