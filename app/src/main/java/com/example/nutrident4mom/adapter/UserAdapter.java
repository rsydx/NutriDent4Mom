package com.example.nutrident4mom.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrident4mom.R;
import com.example.nutrident4mom.login.User;
import com.example.nutrident4mom.login.UserChat;
import com.example.nutrident4mom.main.MessageActivity;
import com.example.nutrident4mom.model.Chat;
import com.example.nutrident4mom.model.Model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.userViewHolder> {
    private Context mContext;
    private List<UserChat> mUsers;
    public UserAdapter(Context mContext, List<UserChat> mUsers) {

        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UserAdapter.userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
        UserChat user = mUsers.get(position);
        holder.email.setText(user.getEmail());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MessageActivity.class);
                intent.putExtra("userId", user.getUserId());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class userViewHolder extends RecyclerView.ViewHolder{
        public TextView email;
        public ImageView profile_img;
        public userViewHolder(@NonNull View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.email);
            profile_img = itemView.findViewById(R.id.profile_img);
        }
    }
}
