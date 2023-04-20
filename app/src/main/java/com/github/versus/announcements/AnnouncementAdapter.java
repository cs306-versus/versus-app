package com.github.versus.announcements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.R;
import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Post;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import org.w3c.dom.Text;

import java.util.List;
import java.util.stream.Collectors;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    private List<Post> posts;
    private VersusUser user;
    private FsPostManager fpm;
    public AnnouncementAdapter(List<Post> posts, VersusUser currentUser, FsPostManager fpm){
        if(posts == null) {
            throw new IllegalArgumentException("Posts must be non-null!");
        }
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

    private void setViewText(AnnouncementAdapter.ViewHolder viewHolder, Post currentPost){
        viewHolder.getTitleTextView().setText(currentPost.getTitle());
        viewHolder.getSportTextView().setText(currentPost.getSport().name);
        viewHolder.getMaxPlayerCountTextView().setText(currentPost.getPlayers().size() + "/" + currentPost.getPlayerLimit());
        viewHolder.getDateTextView().setText(currentPost.getDate().getDay() + "/" +currentPost.getDate().getMonth().getValue() + "/" + currentPost.getDate().getYear());
        viewHolder.getLocationTextView().setText(currentPost.getLocation().toString());
    }
    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder viewHolder, int position) {
        Post currentPost = posts.get(position);
        setViewText(viewHolder, currentPost);
        boolean joined = currentPost.getPlayers().stream().map(user -> user.getUID()).collect(Collectors.toList()).contains(user.getUID());
        if(joined){
            viewHolder.getJoinButton().setText("Joined");
            viewHolder.getJoinButton().setEnabled(false);
        } else {
            if(currentPost.getPlayers().size() < currentPost.getPlayerLimit()){
            viewHolder.getJoinButton().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fpm.joinPost(currentPost.getTitle(), user);
                    viewHolder.getJoinButton().setText("Joined");
                    viewHolder.getJoinButton().setEnabled(false);
                    currentPost.getPlayers().add(user);
                    notifyDataSetChanged();
                }
            });
            } else {
                viewHolder.getJoinButton().setText("Full");
                viewHolder.getJoinButton().setEnabled(false);
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
