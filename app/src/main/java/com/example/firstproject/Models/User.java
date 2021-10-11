package com.example.firstproject.Models;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.firstproject.Converters.StringToArray;
import com.example.firstproject.Database.ImageBitmapString;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    String username ;
    String password ;

    String Name ;
    String email ;

    @TypeConverters(StringToArray.class)
    ArrayList<String> SavedPosts ;

    @TypeConverters(StringToArray.class)
    ArrayList<String> FollowingIds ;

    @TypeConverters(StringToArray.class)
    ArrayList<String> FollowersIds ;

    @TypeConverters({ImageBitmapString.class})
    Bitmap UserImage ;

    boolean isLoggedIn ;

    int PostsCount ;
    int followings ;
    int followers ;

    // Constructors

    public User() {
        FollowersIds = new ArrayList<>();
        FollowingIds = new ArrayList<>();
        SavedPosts = new ArrayList<>();
    }


    // Methods

    public void RemoveFollower (String username) { this.FollowersIds.remove(username); }

    public void AcceptFollow (String username) { this.FollowersIds.add(username); }

    public void follow (String username){
        this.FollowingIds.add(username);
    }

    public void unFollow (String username) { this.FollowingIds.remove(username); }

    public void Addpost (Long postId) {
        this.PostsCount += 1 ;
    }

    public void SavePost (Long postId) {
        this.SavedPosts.add(Long.toString(postId));
    }

    public void  removeFromSaved (Long postId){
        this.SavedPosts.remove(Long.toString(postId));
    }

    //    Getters and setters ---------------------------------------------------------------------------------------------

    public ArrayList<String> getFollowersIds() {
        return FollowersIds;
    }

    public void setFollowersIds(ArrayList<String> followersIds) {
        FollowersIds = followersIds;
    }

    public ArrayList<String> getFollowingIds() {
        return FollowingIds;
    }

    public void setFollowingIds(ArrayList<String> followingIds) {
        FollowingIds = followingIds;
    }

    public int getPostsCount() {
        return PostsCount;
    }

    public void setPostsCount(int postsCount) {
        PostsCount = postsCount;
    }

    public Bitmap getUserImage() {
        return UserImage;
    }

    public void setUserImage(Bitmap userImage) {
        UserImage = userImage;
    }

    public ArrayList<String> getSavedPosts() {
        return SavedPosts;
    }

    public void setSavedPosts(ArrayList<String> savedPosts) {
        SavedPosts = savedPosts;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFollowings() {
        return this.FollowingIds.size();
    }

    public void setFollowings(int followings) {
        this.followings = followings;
    }

    public int getFollowers() {
        return FollowersIds.size();
    }

    public void setFollowers(int follwers) {
        this.followers = follwers;
    }
}
