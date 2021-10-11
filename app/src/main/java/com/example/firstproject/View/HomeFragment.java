package com.example.firstproject.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Adaptors.HomeRecyclerViewAdaptor;
import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Models.Post;
import com.example.firstproject.R;
import com.example.firstproject.listeners.LikeListener;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {

    Executor executor = Executors.newSingleThreadExecutor();
    PostsDatabase database;
    Context context ;

    List<Post> posts ;
    String username ;

    public HomeFragment(String username) {
        this.username = username;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context ;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        database = PostsDatabase.getInstance(context);
        return inflater.inflate(R.layout.fragment_home , container , false);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final RecyclerView recyclerView = view.findViewById(R.id.HomeRecyclerView);
        final ProgressBar progressBar = view.findViewById(R.id.progressBar);

        executor.execute(new Runnable() {
            @Override
            public void run() {
                posts = database.PostDao().getAllPosts();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.INVISIBLE);
                        HomeRecyclerViewAdaptor h = new HomeRecyclerViewAdaptor(posts
                                , getActivity()
                                , username );

                        h.setLikeListener(new LikeListener() {
                            @Override
                            public void OnLikeListener() {
                                h.notifyDataSetChanged();
                            }
                        });

                        recyclerView.setAdapter(h);

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                });
            }
        });

    }
}
