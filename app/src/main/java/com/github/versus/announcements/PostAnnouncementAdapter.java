package com.github.versus.announcements;

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
import com.github.versus.EditPostFragment;
import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsScheduleManager;
import com.github.versus.posts.Post;
import com.github.versus.user.VersusUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.stream.Collectors;

public class PostAnnouncementAdapter extends RecyclerView.Adapter<PostAnnouncementAdapter.ViewHolder> {
    private List<Post> posts;
    private VersusUser user;
    private FsPostManager fpm;
    private Context context;
    public PostAnnouncementAdapter(List<Post> posts, VersusUser currentUser, FsPostManager fpm, Context context){
        if(posts == null) {
            throw new IllegalArgumentException("Posts must be non-null!");
        }
        this.context = context;
        this.posts = posts;
        user = currentUser;
        this.fpm = fpm;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.announcement_row_element, viewGroup, false);

        return new ViewHolder(view);
    }

    private void setViewText(PostAnnouncementAdapter.ViewHolder viewHolder, Post currentPost){
        viewHolder.getTitleTextView().setText(currentPost.getTitle());
        viewHolder.getSportTextView().setText(currentPost.getSport().name);
        viewHolder.getMaxPlayerCountTextView().setText(currentPost.getPlayers().size() + "/" + currentPost.getPlayerLimit());
        viewHolder.getDateTextView().setText(currentPost.getDate().getDay() + "/" +currentPost.getDate().getMonth().getValue() + "/" + currentPost.getDate().getYear());
        viewHolder.getLocationTextView().setText(currentPost.getLocation().toString());
    }
    @Override
    public void onBindViewHolder(@NonNull PostAnnouncementAdapter.ViewHolder viewHolder, int position) {
        Post currentPost = posts.get(position);
        setViewText(viewHolder, currentPost);
        boolean owner = false;
        if(currentPost.getPlayers().size() > 0){
            owner = currentPost.getPlayers().get(0).getUID().equals(user.getUID());
        }
        if(owner){
            viewHolder.getJoinButton().setText("Edit Post");
            viewHolder.getJoinButton().setEnabled(true);
            viewHolder.getJoinButton().setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                           // Fragment f = new EditPostFragment(fpm);
                            Bundle b = new Bundle();
                            b.putSerializable("post", (Post) currentPost);
                           // f.setArguments(b);
                            //manager.beginTransaction().replace(R.id.fragment_container, f).commit();
                        }
                    }
            );
        } else {
            boolean joined = currentPost.getPlayers().stream().map(user -> user.getUID()).collect(Collectors.toList()).contains(user.getUID());
            if (joined) {
                viewHolder.getJoinButton().setText("Leave");
                viewHolder.getJoinButton().setEnabled(true);
                viewHolder.getJoinButton().setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view){
                        viewHolder.getJoinButton().setText("Left Post");
                        viewHolder.getJoinButton().setEnabled(false);
                        fpm.leavePost(user, currentPost.getUid());
                        currentPost.getPlayers().remove(user);
                        notifyDataSetChanged();
                    }
                });
            } else {
                if(currentPost.getPlayers().size() >= currentPost.getPlayerLimit()) {
                    viewHolder.getJoinButton().setText("Full");
                    viewHolder.getJoinButton().setEnabled(false);
                } else {
                    viewHolder.getJoinButton().setText("Join");
                    viewHolder.getJoinButton().setEnabled(true);
                    viewHolder.getJoinButton().setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    viewHolder.getJoinButton().setText("Joined");
                                    viewHolder.getJoinButton().setEnabled(false);
                                    currentPost.getPlayers().add(user);
                                    fpm.joinPost(user, currentPost.getUid());
                                    FsScheduleManager sman = new FsScheduleManager(FirebaseFirestore.getInstance());
                                    sman.addPostToSchedule(user.getUID(), currentPost);
                                    notifyDataSetChanged();
                                }
                            }
                    );
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView sport;
        private final TextView maxPlayerCount;
        private final TextView location;
        private final TextView date;
        private final Button join;
        public ViewHolder(View view) {

            super(view);
//             Define click listener for the ViewHolder's View
            title = (TextView) view.findViewById(R.id.announcement_title);
            sport = (TextView) view.findViewById(R.id.announcement_sport);
            maxPlayerCount = (TextView) view.findViewById(R.id.announcement_players);
            location = (TextView) view.findViewById(R.id.announcement_location);
            date = (TextView) view.findViewById(R.id.announcement_date);
            join = (Button) view.findViewById(R.id.join_button);
        }

        public TextView getTitleTextView() {
            return title;
        }
        public TextView getSportTextView() { return sport; }
        public TextView getMaxPlayerCountTextView() { return maxPlayerCount; }
        public TextView getLocationTextView() { return location; }
        public TextView getDateTextView() { return date; }
        public Button getJoinButton() {return join; }
    }
}
