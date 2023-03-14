package com.github.versus.announcements;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.R;
import com.github.versus.posts.Post;

import org.w3c.dom.Text;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.ViewHolder> {
    private Post[] posts;
    public AnnouncementAdapter(Post[] posts){
        this.posts = posts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.announcement_row_element, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementAdapter.ViewHolder viewHolder, int position) {
        viewHolder.getTitleTextView().setText(posts[position].getTitle());
        viewHolder.getSportTextView().setText("Placeholder Sport");
        viewHolder.getDateTextView().setText(posts[position].getDate().toString());
        viewHolder.getLocationTextView().setText(posts[position].getLocation().toString());
    }

    @Override
    public int getItemCount() {
        return posts.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView sport;
        private final TextView maxPlayerCount;
        private final TextView location;
        private final TextView date;
        public ViewHolder(View view) {

            super(view);
//             Define click listener for the ViewHolder's View
            title = (TextView) view.findViewById(R.id.announcement_title);
            sport = (TextView) view.findViewById(R.id.announcement_sport);
            maxPlayerCount = (TextView) view.findViewById(R.id.announcement_players);
            location = (TextView) view.findViewById(R.id.announcement_date);
            date = (TextView) view.findViewById(R.id.announcement_date);

        }

        public TextView getTitleTextView() {
            return title;
        }
        public TextView getSportTextView() { return sport; }
        public TextView getMaxPlayerCountTextView() { return maxPlayerCount; }
        public TextView getLocationTextView() { return location; }
        public TextView getDateTextView() { return date; }
    }
}
