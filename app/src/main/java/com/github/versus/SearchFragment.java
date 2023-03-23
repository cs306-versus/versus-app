package com.github.versus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.announcements.AnnouncementAdapter;
import com.github.versus.announcements.CreatePostDialogFragment;
import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SearchFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected CreatePostDialogFragment cpdf;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_research,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        cpdf = new CreatePostDialogFragment();
        FsPostManager pm = new FsPostManager(FirebaseFirestore.getInstance());
        Button addPost = (Button) rootView.findViewById(R.id.add_posts);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost(view);
            }
        });
        List<Post> posts = new ArrayList<>();

        CompletableFuture<List<Post>> postsFuture = (CompletableFuture<List<Post>>) pm.fetchAll("   posts");

        AnnouncementAdapter aa = new AnnouncementAdapter(posts);
        postsFuture.thenApply(newPosts -> {
            posts.addAll(newPosts);
            aa.notifyDataSetChanged();
            return posts;
        });
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(aa);
        return rootView;
    }

    public void createPost(View view){
//        FragmentManager fm = new FragmentManage   r();
        cpdf.show(getParentFragmentManager(), "1");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        recyclerView = null;
    }
}
