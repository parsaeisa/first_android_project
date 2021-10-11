package com.example.firstproject;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.Post;
import com.example.firstproject.Models.User;
import com.example.firstproject.View.PostFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SignUpActivity extends AppCompatActivity {

    ImageView UserPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextInputEditText usernameSignup = findViewById(R.id.usernameSignup);
        TextInputEditText passwordSignup = findViewById(R.id.passwordSignup);
        TextInputEditText nameSignup = findViewById(R.id.nameSignup);
        TextInputEditText emailSignup = findViewById(R.id.emailSignup);
        Button signUp = findViewById(R.id.buttonSignup);
        Button ChooseImage = findViewById(R.id.ChoosePicture);
        UserPhoto = findViewById(R.id.UserPhoto);

        UserDatabase database = UserDatabase.getInstance(this);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

        Executor executor = Executors.newSingleThreadExecutor();

        ref.child("details").child("password").setValue(passwordSignup.getText().toString());
        ref.child("details").child("username").setValue(usernameSignup.getText().toString());

        final User user = new User();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.setUsername(usernameSignup.getText().toString());
                user.setPassword(passwordSignup.getText().toString());
                user.setName(nameSignup.getText().toString());
                user.setEmail(emailSignup.getText().toString());
                user.setFollowers(0);
                user.setFollowings(0);
                user.setSavedPosts(new ArrayList<>());
                user.setLoggedIn(false);
                BitmapDrawable drawable = (BitmapDrawable) UserPhoto.getDrawable() ;
                user.setUserImage(drawable.getBitmap());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.userDao().userSignup(user);
                    }
                });
                Intent intent = new Intent(SignUpActivity.this , AuthActivity.class);
                startActivity(intent);
            }
        });

        ChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImagesFromGallery();
            }
        });
    }

    private void loadImagesFromGallery() {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        }

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("tagx", "onActivityResult: ");
        if ( resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {

                UserPhoto = findViewById(R.id.UserPhoto);

                InputStream is = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                UserPhoto.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            Log.d("tagx", "onActivityResult: on Else ");
        }
    }
}