package com.example.firstproject.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.Post;
import com.example.firstproject.Models.User;
import com.example.firstproject.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PostFragment extends Fragment {

    User user ;
    String username ;
    PostsDatabase database = PostsDatabase.getInstance(getActivity());
    UserDatabase userDatabase = UserDatabase.getInstance(getActivity());

    public PostFragment(String LoggedInUserName) {
        UserDatabase database = UserDatabase.getInstance(getActivity());
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                user = database.userDao().getUserByUsername(LoggedInUserName);
            }
        });
        this.username = LoggedInUserName ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container
                    , @Nullable Bundle savedInstanceState ) {
        return inflater.inflate(R.layout.fragment_post , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageViewChoosedImage = view.findViewById(R.id.imageViewChoosedImage);
        Button buttonPost = view.findViewById(R.id.buttonPost);
        Button ChooseImage = view.findViewById(R.id.ChooseImage);
        TextInputEditText textInputCaption = view.findViewById(R.id.textInputCaption);

        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagesFromGallery();
            }
        });

        buttonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BitmapDrawable drawable = (BitmapDrawable) imageViewChoosedImage.getDrawable() ;
                Bitmap bitmap = drawable.getBitmap();

                Executors.newSingleThreadExecutor().execute(new Runnable() {
                    @Override
                    public void run() {
                        User user = userDatabase.userDao().getUserByUsername(username);
                        Post post = new Post(textInputCaption.getText().toString() ,
                                              username ,  bitmap , user.getUserImage());
                        database.PostDao().AddPost(post);
                    }
                });
            }
        });
    }

    private void loadImagesFromGallery() {

        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);

    }
}
