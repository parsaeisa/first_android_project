package com.example.firstproject.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.firstproject.Models.User;

import java.util.List;

@Dao
public interface UserDatabaseDao {

    @Insert
    void userSignup (User user);

    @Query("select * from users where username = :Username ")
    User getUserByUsername (String Username);

    @Query("select * from users")
    List<User> getAllUsers();

    @Update
    void UpdateUser(User user);

    @Query("select * from users where username like :search")
    List<User> getUserBySearch(String search);
}
