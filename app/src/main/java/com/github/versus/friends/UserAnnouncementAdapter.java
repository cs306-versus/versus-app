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
import com.github.versus.chats.Chat;
import com.github.versus.db.FsChatManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.makeramen.roundedimageview.RoundedImageView;

import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class UserAnnouncementAdapter extends RecyclerView.Adapter<UserAnnouncementAdapter.ViewHolder> {
    private List<User> users;
    private VersusUser user;
    private Context context;
    private FsUserManager fum;
    public UserAnnouncementAdapter(List<User> users, VersusUser currentUser, FsUserManager fum, Context context){
        if(users == null) {
            throw new IllegalArgumentException("Users must be non-null!");
        }
        this.users = users;
        this.user = currentUser;
        this.fum = fum;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_users, viewGroup, false);

        return new ViewHolder(view);
    }

    private void setViewText(ViewHolder viewHolder, User currentUser){
        viewHolder.getName().setText(currentUser.getFirstName()+ " "+ currentUser.getLastName());
        viewHolder.getRating().setText(Integer.toString((currentUser.getRating())));
        //if the user and the currUser are already friends
        VersusUser vCurrUser = (VersusUser)currentUser;
        String appUserUid = FirebaseAuth.getInstance().getUid();
        if(vCurrUser.getFriends().contains(appUserUid)) {
            viewHolder.getAddFriend().setVisibility(View.INVISIBLE);
            viewHolder.getFriend_addded_true().setVisibility(View.VISIBLE);
        }else{
            viewHolder.getAddFriend().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //adding the user as a friend
                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                    FsUserManager uman = new FsUserManager(db);
                    uman.addFriend(FirebaseAuth.getInstance().getUid(), currentUser.getUID());
                    CompletableFuture<Boolean> friendship = uman.createFriendship(currentUser.getUID(), appUserUid);
                    //changing the icon
                    friendship.thenAccept(res -> {
                                if(res){
                                    //Adding a chat between the 2 friends
                                    FsChatManager fcm = new FsChatManager(db);
                                    String friend1 = currentUser.getUID();
                                    String friend2 = appUserUid;
                                    Chat newChat = new Chat(friend1, friend2, Chat.computeChatId(friend1, friend2));
                                    fcm.insert(newChat);
                                    //updating the friendship icon
                                    viewHolder.getAddFriend().setVisibility(View.INVISIBLE);
                                    viewHolder.getFriend_addded_true().setVisibility(View.VISIBLE);
                                }
                            }

                    );

                }
            });
        }

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
        private  TextView userName;
        private  TextView rating;
        private RoundedImageView friend_addded;
        private RoundedImageView friend_addded_true;


        public ViewHolder(View view) {

            super(view);
            // Define click listener for the ViewHolder's View
            userName = (TextView) view.findViewById(R.id.announcement_user_name);
            rating = (TextView) view.findViewById(R.id.announcement_user_rating);
            friend_addded = (RoundedImageView) view.findViewById(R.id.friend_added);
            friend_addded_true = (RoundedImageView) view.findViewById(R.id.friend_added_true);
        }

        public TextView getName() {
            return userName;
        }
        public TextView getRating() {
            return rating;
        }
        public RoundedImageView getAddFriend() {return friend_addded;}
        public RoundedImageView getFriend_addded_true() {return friend_addded_true;}

    }
}
