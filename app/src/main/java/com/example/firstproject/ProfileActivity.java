package com.example.firstproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstproject.Adaptors.ProfileReyclerViewAdaptor;
import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.Post;
import com.example.firstproject.Models.User;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import weborb.types.WebORBArrayCollection;

public class ProfileActivity extends AppCompatActivity {

    User user;
    User LoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String LoggedInUser = getIntent().getExtras().getString("LoggedInUsername");
        String username = getIntent().getExtras().getString("username");

        UserDatabase database = UserDatabase.getInstance(this);
        Executor executor = Executors.newSingleThreadExecutor();
        Executor executor1 = Executors.newSingleThreadExecutor();

        Button buttonFollow = findViewById(R.id.buttonFollow);
        TextView numberofFollowers2 = findViewById(R.id.numberofFollowersAcc);
        TextView numberofPosts2 = findViewById(R.id.numberofPostsAcc);
        TextView numberofFollowings2 = findViewById(R.id.numberofFollowingsAcc);
        TextView textViewUsernameAcc = findViewById(R.id.textViewUsernameAcc);
        RecyclerView recyclerView = findViewById(R.id.RecyclerViewProfileAcc);
        ImageView imageView = findViewById(R.id.imageViewProfileAcc);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                user = database.userDao().getUserByUsername(username);
                LoggedIn = database.userDao().getUserByUsername(LoggedInUser);
                List<Post> posts = PostsDatabase.getInstance(ProfileActivity.this).
                                        PostDao().getPostByUser(username);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        numberofFollowers2.setText(user.getFollowers() + "");
                        numberofFollowings2.setText(user.getFollowings() + "");
                        numberofPosts2.setText(user.getPostsCount() + "");
                        imageView.setImageBitmap(user.getUserImage());
                        textViewUsernameAcc.setText(user.getUsername());


                        ProfileReyclerViewAdaptor profileReyclerViewAdaptor = new ProfileReyclerViewAdaptor(ProfileActivity.this , posts );
                        recyclerView.setAdapter(profileReyclerViewAdaptor);
                        recyclerView.setLayoutManager(new GridLayoutManager(ProfileActivity.this , 3));
                    }
                });
            }
        });

        buttonFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ( !LoggedIn.getFollowingIds().contains(user.getUsername()) ) {
                    buttonFollow.setText("Unfollow");
                    user.AcceptFollow(LoggedInUser);
                    LoggedIn.follow(username);
                    numberofFollowers2.setText(user.getFollowers() + "");
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            database.userDao().UpdateUser(LoggedIn);
                            database.userDao().UpdateUser(user);
                        }
                    });
                } else {
                    buttonFollow.setText("Follow");
                    user.RemoveFollower(LoggedInUser);
                    LoggedIn.unFollow(username);
                    numberofFollowers2.setText(user.getFollowers() + "");

                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            database.userDao().UpdateUser(LoggedIn);
                            database.userDao().UpdateUser(user);
                        }
                    });
                }
            }
        });
    }
}
