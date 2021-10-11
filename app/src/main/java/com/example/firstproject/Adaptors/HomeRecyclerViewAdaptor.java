package com.example.firstproject.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Database.LikeActivityDatabase;
import com.example.firstproject.Database.PostsDatabase;
import com.example.firstproject.Database.UserDatabase;
import com.example.firstproject.Models.LikeActivity;
import com.example.firstproject.Models.Post;
import com.example.firstproject.Models.User;
import com.example.firstproject.ProfileActivity;
import com.example.firstproject.R;
import com.example.firstproject.listeners.LikeListener;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeRecyclerViewAdaptor extends RecyclerView.Adapter<HomeRecyclerViewAdaptor.ViewHolder> {

    List<Post> posts ;
    Context context ;
    String username ;
    Executor executor = Executors.newSingleThreadExecutor();
    User user ;
    UserDatabase userDatabase ;
    PostsDatabase database;
    LikeActivityDatabase likeActivityDatabase ;
    LikeListener likeListener ;

    public HomeRecyclerViewAdaptor(List<Post> posts, Context context , String username ) {
        this.posts = posts;
        this.context = context;
        database = PostsDatabase.getInstance(context);
        likeActivityDatabase = LikeActivityDatabase.getInstance(context);
        userDatabase = UserDatabase.getInstance(context);
        this.username = username ;
        executor.execute(new Runnable() {
            @Override
            public void run() {
                user = userDatabase.userDao().getUserByUsername(username);
            }
        });
    }

    public void setLikeListener(LikeListener likeListener) {
        this.likeListener = likeListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_layout , parent , false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = posts.get(posts.size() - position -1);

        holder.textViewLikesCount.setText(post.getUsersThatLike().size() + "");
        holder.textViewCommentsCount.setText("0");
        holder.textViewUsername.setText(post.getUserId());
        holder.textViewCaption.setText(post.getUserId() + " : " + post.getCaption());
        holder.textViewDate.setText("posted on " + post.getDate());
        holder.imageViewPostContent.setImageBitmap(post.getImage());
        holder.imageViewUserProfile.setImageBitmap(post.getUserImage());

        try {
            Thread.sleep(100);
            if ( user.getSavedPosts().contains(Long.toString(post.getId())) )
                holder.imageViewSavePost.setImageResource(R.drawable.ic_post_saved);
            else
                holder.imageViewSavePost.setImageResource(R.drawable.ic_bookmark);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imageViewUserProfile ;
        ImageView imageViewPostContent ;
        ImageView imageViewLike ;
        ImageView imageViewComment ;
        ImageView imageViewSavePost ;
        TextView textViewUsername;
        TextView textViewLikesCount;
        TextView textViewCommentsCount;
        TextView textViewCaption;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewUserProfile = itemView.findViewById(R.id.userImage);
            imageViewPostContent = itemView.findViewById(R.id.imageViewPostContent);
            imageViewLike = itemView.findViewById(R.id.imageViewLike);
            imageViewComment = itemView.findViewById(R.id.imageViewComment);
            imageViewSavePost = itemView.findViewById(R.id.imageViewSavedPosts);

            textViewUsername = itemView.findViewById(R.id.textViewUsername);
            textViewLikesCount = itemView.findViewById(R.id.textViewLikesCount);
            textViewCommentsCount = itemView.findViewById(R.id.textViewCommentsCount);
            textViewCaption = itemView.findViewById(R.id.textViewCaption);
            textViewDate = itemView.findViewById(R.id.textViewDate);

            textViewUsername.setOnClickListener(this);
            imageViewUserProfile.setOnClickListener(this);
            imageViewLike.setOnClickListener(this);
            imageViewSavePost.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            // Getting to users profile
            if (view.getId() == R.id.textViewUsername || view.getId() == R.id.userImage  ){
                Intent i = new Intent(context , ProfileActivity.class);
                i.putExtra("username" , this.textViewUsername.getText());
                i.putExtra("LoggedInUsername" , username );
                context.startActivity(i);
            }

            // Like post
            if (view.getId() == R.id.imageViewLike) {

                int a = getAdapterPosition() + posts.size() - 1;
                Post post = posts.get(a);
                LikeActivity likeActivity = new LikeActivity(
                        post.getImage(),
                        username, post.getUserId()
                        , Long.toString(post.getId()));

                if( !posts.get(getAdapterPosition()).getUsersThatLike().contains(username)) {
                    Like(getAdapterPosition());
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            likeActivityDatabase.LikeDao().InsertLikeActivity(likeActivity);
                        }
                    });
                } else {
                    posts.get(a).getUsersThatLike().remove(username);
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            database.PostDao().UpdatePost(posts.get(a));
                            likeActivityDatabase.LikeDao().deleteLike(likeActivity);
                        }
                    });
                    likeListener.OnLikeListener();
                }

            }

            // Save post
            if (view.getId() == R.id.imageViewSavedPosts){
                Post post = posts.get(getAdapterPosition());
                if ( !user.getSavedPosts().contains(Long.toString(post.getId())) ) {
                    user.SavePost(post.getId());
                    imageViewSavePost.setImageResource(R.drawable.ic_post_saved);
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            userDatabase.userDao().UpdateUser(user);
                        }
                    });
                    likeListener.OnLikeListener();
                }
                else {
                    user.removeFromSaved(post.getId());
                    imageViewSavePost.setImageResource(R.drawable.ic_bookmark);
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            userDatabase.userDao().UpdateUser(user);
                        }
                    });
                    likeListener.OnLikeListener();
                }
            }

        }
    }

    private void Like(int adapterPosition) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Post post = posts.get(adapterPosition);
                post.AddLike(username);
                database.PostDao().UpdatePost(post);
            }
        });
        likeListener.OnLikeListener();
    }

//    something for comments
}
