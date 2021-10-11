package com.example.firstproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.User;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AuthActivity extends AppCompatActivity {

    ImageView imageView ;
    UserDatabase database ;
    Executor executor = Executors.newSingleThreadExecutor();
    BackendlessUser backendlessUser = new BackendlessUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        database = UserDatabase.getInstance(this);


        Button buttonSingIn = findViewById(R.id.buttonSignIn);
        TextInputEditText textInputUsername = findViewById(R.id.textInputUsername);
        TextInputEditText textInputPassword = findViewById(R.id.textInputPassword);
        TextView textViewMakeNewAccount = findViewById(R.id.textViewMakeNewAccount);

        textViewMakeNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this , SignUpActivity.class);
                startActivity(intent);
            }
        });

        buttonSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Backendless.UserService.login(textInputUsername.getText().toString()
//                        , textInputPassword.getText().toString()
//                        , new AsyncCallback<BackendlessUser>() {
//                            @Override
//                            public void handleResponse(BackendlessUser response) {
//                                Toast.makeText(getApplicationContext(), "Logged In!", Toast.LENGTH_LONG).show();
//                                Intent i = new Intent(AuthActivity.this, MainActivity.class);
//                                startActivity(i);
//                                finish();
//                            }
//
//                            @Override
//                            public void handleFault(BackendlessFault fault) {
//                                Toast.makeText(getApplicationContext(), "No Name", Toast.LENGTH_LONG).show();
//                            }
//                        });

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        User user = database.userDao().getUserByUsername(textInputUsername.getText().toString());
                        try {
                            Log.d("tagx", "run: " + user.getPassword() + " " + textInputPassword.getText().toString());
                            if (user.getPassword().equals(textInputPassword.getText().toString())) {
                                user.setLoggedIn(true);
                                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                intent.putExtra("username", textInputUsername.getText().toString());
                                startActivity(intent);
                            }
                        }catch (Exception e){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(AuthActivity.this, "این حساب پیدا نشد .", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                });

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        /*final List<User>[] users = new List[]{new ArrayList<>()};

        executor.execute(new Runnable() {
            @Override
            public void run() {
                users[0] = database.userDao().getAllUsers();
            }
        });

        for (User user: users[0])
            if (user.isLoggedIn() == true)
            {
                Intent intent = new Intent(AuthActivity.this , MainActivity.class);
                intent.putExtra(user.getUsername() , "username");
                startActivity(intent);
                break;
            } */
    }

}