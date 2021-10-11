package com.example.firstproject.Models;


import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.firstproject.Database.ImageBitmapString;

@Entity(tableName = "Likes")
public class LikeActivity {

    @PrimaryKey
    @NonNull
    String Id ;

    @TypeConverters(ImageBitmapString.class)
    Bitmap likedPostImage ;

    String likedPostId ;

    String LikerUsername ;
    String LikedPostOwner ;

//    constructors

    public LikeActivity() {}

    public LikeActivity(Bitmap likedPostImage, String likerUsername, String likedPostOwner , String likedPostId) {
        this.likedPostImage = likedPostImage ;
        LikerUsername = likerUsername;
        LikedPostOwner = likedPostOwner;
        this.likedPostId = likedPostId ;
        Id = likedPostId + LikerUsername + LikedPostOwner;
    }

    //    Getters and Setters

    public String getLikedPostId() {
        return likedPostId;
    }

    public void setLikedPostId(String likedPostId) {
        this.likedPostId = likedPostId;
    }

    public String getId() {
        return likedPostId + LikerUsername + LikedPostOwner ;
    }

    public void setId(String id) {
        Id = id;
    }

    public Bitmap getLikedPostImage() {
        return likedPostImage;
    }

    public void setLikedPostImage(Bitmap likedPostImage) {
        this.likedPostImage = likedPostImage;
    }

    public String getLikerUsername() {
        return LikerUsername;
    }

    public void setLikerUsername(String likerUsername) {
        LikerUsername = likerUsername;
    }

    public String getLikedPostOwner() {
        return LikedPostOwner;
    }

    public void setLikedPostOwner(String likedPostOwner) {
        LikedPostOwner = likedPostOwner;
    }

}
