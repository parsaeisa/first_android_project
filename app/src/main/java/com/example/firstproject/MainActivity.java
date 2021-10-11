package com.example.firstproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.firstproject.Adaptors.MainFragmentPagerAdaptor;
import com.example.firstproject.Database.ImageBitmapString;
import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Models.Post;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Post> Pheed ;
    PostsDatabase database;

    ImageView imageViewPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = PostsDatabase.getInstance(this);

        String LoggedInUsername = getIntent().getExtras().getString("username");
        Log.d("tagx", "onCreate: " + LoggedInUsername);

        MainFragmentPagerAdaptor mfpa = new MainFragmentPagerAdaptor(getSupportFragmentManager()
                        , this.getLifecycle() , LoggedInUsername );
        final ViewPager2 viewPagerMain = findViewById(R.id.ViewPager);

        viewPagerMain.setUserInputEnabled(false);
        viewPagerMain.setAdapter(mfpa);

        BottomNavigationView bottomNavigationMenuView = findViewById(R.id.bottom_navigation);
        bottomNavigationMenuView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.actionHome :
                        viewPagerMain.setCurrentItem(0);
                        break;

                    case R.id.actionSearch :
                        viewPagerMain.setCurrentItem(1);
                        break;

                    case R.id.actionPost :
                        viewPagerMain.setCurrentItem(2);
                        break;

                    case R.id.actionLikes :
                        viewPagerMain.setCurrentItem(3);
                        break;

                    case R.id.actionProfile :
                        viewPagerMain.setCurrentItem(4);
                        break;
                }
                return false;
            }
        });

        imageViewPost = findViewById(R.id.imageViewChoosedImage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("tagx", "onActivityResult: ");
        if ( resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            try {

                imageViewPost = findViewById(R.id.imageViewChoosedImage);

                InputStream is = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                imageViewPost.setImageBitmap(bitmap);
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