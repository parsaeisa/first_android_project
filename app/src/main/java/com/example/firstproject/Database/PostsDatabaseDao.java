package com.example.firstproject.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.firstproject.Models.Post;

import java.util.List;

@Dao
public interface PostsDatabaseDao {

    @Query("select * from postsdb")
    List<Post> getAllPosts();

    @Update
    void UpdatePost(Post post);

    @Insert
    void AddPost(Post post);

    @Query("select * from postsdb where id = :Id")
    Post getPostById (long Id);

    @Query("select * from postsdb where userId = :UserId")
    List<Post> getPostByUser (String UserId);
}
