package com.example.firstproject.Adaptors;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.User;
import com.example.firstproject.View.HomeFragment;
import com.example.firstproject.View.LikesFragment;
import com.example.firstproject.View.PostFragment;
import com.example.firstproject.View.ProfileFragment;
import com.example.firstproject.View.SearchFragment;

public class MainFragmentPagerAdaptor extends FragmentStateAdapter {

    String username ;

    public MainFragmentPagerAdaptor(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle , String username) {
        super(fragmentManager, lifecycle);
        this.username = username ;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0 :
                return new HomeFragment(username);
            case 1 :
                return new SearchFragment(username);
            case 2 :
                return new PostFragment(username);
            case 3 :
                return  new LikesFragment();
            case 4 :
                return  new ProfileFragment(username) ;
        }
        return new HomeFragment(username) ;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
