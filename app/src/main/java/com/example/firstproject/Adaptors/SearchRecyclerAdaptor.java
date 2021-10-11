package com.example.firstproject.Adaptors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstproject.Converters.StringToArray;
import com.example.firstproject.Models.User;
import com.example.firstproject.ProfileActivity;
import com.example.firstproject.R;

import java.util.List;

public class SearchRecyclerAdaptor extends  RecyclerView.Adapter<SearchRecyclerAdaptor.viewholder>{

    List<User> users ;
    Context context ;
    String username ;

    public SearchRecyclerAdaptor(List<User> users, Context context , String username) {
        this.users = users;
        this.context = context;
        this.username = username ;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  =LayoutInflater.from(context).inflate(R.layout.search_list_item , parent , false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        try {
            User user = users.get(position);
            holder.imageViewUser.setImageBitmap(user.getUserImage());
            holder.textViewUsernameSearch.setText(user.getUsername());
        }catch (Exception e){
            Log.d("tagx", "onBindViewHolder: SearchRecyclerView " + e);
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class viewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewUser ;
        TextView textViewUsernameSearch ;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            imageViewUser = itemView.findViewById(R.id.imageViewUserSearch);
            textViewUsernameSearch = itemView.findViewById(R.id.textViewUsernameSearch);

            imageViewUser.setOnClickListener(this);
            textViewUsernameSearch.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Intent i = new Intent(context , ProfileActivity.class);
            i.putExtra("username" , this.textViewUsernameSearch.getText());
            i.putExtra("LoggedInUsername" , username );
            context.startActivity(i);
        }
    }
}
