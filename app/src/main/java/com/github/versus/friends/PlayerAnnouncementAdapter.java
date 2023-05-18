package com.github.versus.friends;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.AddFriendFragment;
import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.posts.Post;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class PlayerAnnouncementAdapter extends RecyclerView.Adapter<PlayerAnnouncementAdapter.ViewHolder> {
    private List<VersusUser> users;
    private Post post;
    private VersusUser user;
    private Context context;
    private FsPostManager fpm;
    public PlayerAnnouncementAdapter(List<VersusUser> users, FsPostManager fum, Post post){
        if(users == null) {
            throw new IllegalArgumentException("Users must be non-null!");
        }
        this.users = users;
        this.fpm = fum;
        this.context = context;
        this.post = post;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_users, viewGroup, false);

        return new ViewHolder(view);
    }

    private void setViewText(ViewHolder viewHolder, User currentUser){
        System.out.println(currentUser.getFirstName());
        viewHolder.getFirstName().setText(currentUser.getFirstName()+ " "+ currentUser.getLastName());
        viewHolder.getUsername().setText(currentUser.getUserName());
        viewHolder.getKick().setText("Kick");
        viewHolder.getKick().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               fpm.leavePost(currentUser, post.getUid());
               users.remove(currentUser);
               notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        User currentUser = users.get(position);
        setViewText(viewHolder, currentUser);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fn;
        private final TextView un;
        private final TextView city;
        private final Button button;

        public ViewHolder(View view) {

            super(view);
//             Define click listener for the ViewHolder's View
            fn = (TextView) view.findViewById(R.id.user_fn);
            un = (TextView) view.findViewById(R.id.user_un);
            city = (TextView) view.findViewById(R.id.user_city);
            button = (Button) view.findViewById(R.id.view_profile);
        }

        public TextView getFirstName() {
            return fn;
        }
        public TextView getUsername() { return un; }
        public Button getKick() {return button;}

    }
}
