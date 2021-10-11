package com.example.firstproject.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Adaptors.LikesRecyclerViewAdaptor;
import com.example.firstproject.Database.LikeActivityDatabase;
import com.example.firstproject.Models.LikeActivity;
import com.example.firstproject.R;

import java.util.List;
import java.util.concurrent.Executors;

public class LikesFragment extends Fragment {

    LikeActivityDatabase database = LikeActivityDatabase.getInstance(getActivity());

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_likes , container , false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.RecyclelrViewLikes);

        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                List<LikeActivity> activities = database.LikeDao().getAllLikes();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LikesRecyclerViewAdaptor likesAdaptor = new LikesRecyclerViewAdaptor(activities , getActivity());
                        recyclerView.setAdapter(likesAdaptor);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                });
            }
        });

    }
}
