package com.example.firstproject.Adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firstproject.Models.Post;
import com.example.firstproject.R;

import java.util.List;

public class ProfileReyclerViewAdaptor extends  RecyclerView.Adapter<ProfileReyclerViewAdaptor.viewHolder>{

    Context context ;
    List<Post> posts ;

    public ProfileReyclerViewAdaptor(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_profile , parent , false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Post post = posts.get(position);
        holder.imageViewPost.setImageBitmap(post.getImage());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class viewHolder extends RecyclerView.ViewHolder{

        ImageView imageViewPost ;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPost = itemView.findViewById(R.id.imageViewPost_profile);
        }
    }
}
