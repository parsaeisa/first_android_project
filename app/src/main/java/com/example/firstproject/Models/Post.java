package com.example.firstproject.Models;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.firstproject.Converters.StringToArray;
import com.example.firstproject.Database.ImageBitmapString;
import com.example.firstproject.Database.UserDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;

@Entity(tableName = "PostsDB" )
        /*, foreignKeys = @ForeignKey(entity = User.class ,
                                                                            parentColumns = "User" ,
                                                                            childColumns = "Posts",
                                                                            onDelete = ForeignKey.CASCADE)) */
public class Post {
    @PrimaryKey(autoGenerate = true)
    long id ;

    String caption ;
    public String userId ;

    public String Date ;

    @TypeConverters(StringToArray.class)
    ArrayList<String> UsersThatLike ;

    @TypeConverters({ImageBitmapString.class})
    Bitmap Image ;

    @TypeConverters({ImageBitmapString.class})
    Bitmap UserImage ;

    //constructor

    public Post() {}

    public Post(String caption, String userId, Bitmap image , Bitmap UserImage) {

        Image = image;
        this.caption = caption;
        this.userId = userId;
        this.UserImage = UserImage ;

        this.UsersThatLike = new ArrayList<>();

        this.setDate();
    }
    // Methods
    public void AddLike (String likerUsername)
    {
        this.UsersThatLike.add(likerUsername);
    }

    // Getter and Setters

    public ArrayList<String> getUsersThatLike() {
        return UsersThatLike;
    }

    public void setUsersThatLike(ArrayList<String> usersThatLike) {
        UsersThatLike = usersThatLike;
    }

    public String getDate() {
        return Date;
    }

    public void setDate() {
        java.util.Date d = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        this.Date = dateFormat.format(d);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Bitmap getUserImage() {
        return UserImage;
    }

    public void setUserImage(Bitmap userImage) {
        UserImage = userImage;
    }
}
