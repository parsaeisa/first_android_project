package com.example.firstproject.View;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Adaptors.HomeRecyclerViewAdaptor;
import com.example.firstproject.Adaptors.SearchRecyclerAdaptor;
import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.Post;
import com.example.firstproject.Models.User;
import com.example.firstproject.R;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView;
    List<User> users ;
    Executor executor = Executors.newSingleThreadExecutor();
    UserDatabase database = UserDatabase.getInstance(getContext());
    String username ;

    public SearchFragment(String username) {
        this.username = username;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText editTextSearch = view.findViewById(R.id.editTextSearch);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        if ( !editTextSearch.getText().equals("")) {
                            users = database.userDao().getUserBySearch(editTextSearch.getText().toString());
                            if (users.size() > 0) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("tagx", "run: ljllll");
                                        recyclerView = view.findViewById(R.id.RecyclelrViewSearch);
                                        recyclerView.setAdapter(new SearchRecyclerAdaptor(users, getActivity(), username));
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    }
                                });
                            }
                        }
                        else {
                            recyclerView = view.findViewById(R.id.RecyclelrViewSearch);
                            recyclerView.setAdapter(new SearchRecyclerAdaptor(null, getActivity(), username));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                        }
                    }
                });

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
}
