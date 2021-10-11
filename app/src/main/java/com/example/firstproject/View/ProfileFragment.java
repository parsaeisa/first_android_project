package com.example.firstproject.View;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Adaptors.ProfileReyclerViewAdaptor;
import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.Post;
import com.example.firstproject.Models.User;
import com.example.firstproject.R;
import com.qintong.library.InsLoadingView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class ProfileFragment extends Fragment {

    User user ;
    UserDatabase database;
    PostsDatabase postsDatabase ;
    String username ;

    public ProfileFragment(String username) {
        database = UserDatabase.getInstance(getActivity());
        postsDatabase = PostsDatabase.getInstance(getActivity());
        this.username = username ;
    }

    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile ,container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final InsLoadingView insLoadingView = view.findViewById(R.id.imageViewProfile);
        recyclerView = view.findViewById(R.id.RecyclerViewProfile);
        TextView numberofFollowers = view.findViewById(R.id.numberofFollowers2);
        TextView numberofPosts = view.findViewById(R.id.numberofPosts2);
        TextView numberofFollowings = view.findViewById(R.id.numberofFollowings2);
        TextView textViewUsernameFrag = view.findViewById(R.id.textViewUsernameFrag);
        ImageView userImage = view.findViewById(R.id.imageViewProfile);
        RecyclerView recyclerView = view.findViewById(R.id.RecyclerViewProfile);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                user = database.userDao().getUserByUsername(username);
                List<Post> posts = postsDatabase.PostDao().getPostByUser(username);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textViewUsernameFrag.setText(user.getUsername());
                        numberofFollowers.setText(user.getFollowers()+ "");
                        numberofFollowings.setText(user.getFollowings()+ "");
                        numberofPosts.setText(user.getPostsCount() + "");
                        userImage.setImageBitmap(user.getUserImage());

                        recyclerView.setAdapter(new ProfileReyclerViewAdaptor(getActivity() , posts ));
                        recyclerView.setLayoutManager(new GridLayoutManager(getActivity() , 3));
                    }
                });
            }
        });

        insLoadingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(insLoadingView.getStatus() == InsLoadingView.Status.LOADING)
                    insLoadingView.setStatus(InsLoadingView.Status.UNCLICKED);
                else
                    insLoadingView.setStatus(InsLoadingView.Status.LOADING);
            }
        });
    }

}
