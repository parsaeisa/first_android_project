package com.example.firstproject.Adaptors;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Models.LikeActivity;
import com.example.firstproject.Models.Post;
import com.example.firstproject.R;

import java.util.List;
import java.util.concurrent.Executors;

public class LikesRecyclerViewAdaptor extends RecyclerView.Adapter<LikesRecyclerViewAdaptor.viewholder> {

    List<LikeActivity> likes ;
    Context context ;
    PostsDatabase database ;

    public LikesRecyclerViewAdaptor(List<LikeActivity> likes, Context context) {
        this.likes = likes;
        this.context = context;
        database = PostsDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_likes , parent , false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        LikeActivity likeActivity = likes.get(position);
        holder.textViewLikedUsername.setText(likeActivity.getLikedPostOwner());
        holder.textViewLikerUsername.setText(likeActivity.getLikerUsername());
        holder.imageViewLikeActivity.setImageBitmap(likeActivity.getLikedPostImage());
    }

    @Override
    public int getItemCount() {
        return likes.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        ImageView imageViewLikeActivity ;
        TextView textViewLikedUsername ;
        TextView textViewLikerUsername ;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            imageViewLikeActivity = itemView.findViewById(R.id.imageViewLikeActivity);
            textViewLikedUsername = itemView.findViewById(R.id.textViewLikedUsername);
            textViewLikerUsername = itemView.findViewById(R.id.textViewLikerUsername);
        }
    }
}
