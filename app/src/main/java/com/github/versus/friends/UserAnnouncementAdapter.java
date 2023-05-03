package com.github.versus.friends;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.R;
import com.github.versus.db.FsUserManager;
import com.github.versus.posts.Post;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.util.List;
import java.util.stream.Collectors;

public class UserAnnouncementAdapter extends RecyclerView.Adapter<UserAnnouncementAdapter.ViewHolder> {
    private List<User> users;
    private VersusUser user;
    private FsUserManager fum;
    public UserAnnouncementAdapter(List<User> users, VersusUser currentUser, FsUserManager fum){
        if(users == null) {
            throw new IllegalArgumentException("Users must be non-null!");
        }
        this.users = users;
        this.user = currentUser;
        this.fum = fum;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_users, viewGroup, false);

        return new ViewHolder(view);
    }

    private void setViewText(ViewHolder viewHolder, User currentUser){
        viewHolder.getFirstName().setText(currentUser.getFirstName());
        viewHolder.getLastName().setText(currentUser.getLastName());
        viewHolder.getUsername().setText(currentUser.getUserName());
        viewHolder.getCity().setText(currentUser.getCity());
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
        private final TextView ln;
        private final TextView un;
        private final TextView city;

        public ViewHolder(View view) {

            super(view);
//             Define click listener for the ViewHolder's View
            fn = (TextView) view.findViewById(R.id.user_fn);
            ln = (TextView) view.findViewById(R.id.user_ln);
            un = (TextView) view.findViewById(R.id.user_un);
            city = (TextView) view.findViewById(R.id.user_city);
        }

        public TextView getFirstName() {
            return fn;
        }
        public TextView getLastName() { return ln; }
        public TextView getUsername() { return un; }
        public TextView getCity() { return city; }

    }
}
