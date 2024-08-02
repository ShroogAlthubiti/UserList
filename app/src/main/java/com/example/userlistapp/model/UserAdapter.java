package com.example.userlistapp.model;
import androidx.recyclerview.widget.RecyclerView;
import com.example.userlistapp.R;
import com.example.userlistapp.model.data.User;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.List;
import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> users = new ArrayList<>();
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("UserAdapter", "onCreateViewHolder called");
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.nameTextView.setText(user.getName());
        holder.emailTextView.setText(user.getEmail());
        holder.statusTextView.setText(user.getStatus());
        holder.genderTextView.setText(user.getGender());
        Log.d("UserAdapter", "onBindViewHolder: " + user.getName());

    }
    @Override
    public int getItemCount() {
        Log.d("UserAdapter", "getItemCount: " + users.size());
        return users.size();
    }
    public void setUsers(List<User> users) {
        Log.d("UserAdapter", "setUsers: " + (users != null ? users.size() : "null"));

        this.users = users;
        notifyDataSetChanged();
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView statusTextView;
        TextView genderTextView;
        TextView nameTextView;
        TextView emailTextView;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            genderTextView =  itemView.findViewById(R.id.genderTextView);
        }
    }
    }