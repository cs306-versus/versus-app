package com.github.versus;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.announcements.AnnouncementAdapter;
import com.github.versus.announcements.ChoosePostSportDialogFragment;
import com.github.versus.announcements.CreatePostTitleDialogFragment;
import com.github.versus.announcements.MaxPlayerDialogFragment;
import com.github.versus.announcements.PostDatePickerDialog;
import com.github.versus.db.FsPostManager;
import com.github.versus.db.FsUserManager;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class SearchFragment extends Fragment implements
        CreatePostTitleDialogFragment.TitleListener,
        ChoosePostSportDialogFragment.SportListener,
        MaxPlayerDialogFragment.MaxPlayerListener,
        PostDatePickerDialog.PickDateListener {


    protected RecyclerView recyclerView;
    protected Post newPost;
    protected VersusUser user = new VersusUser.Builder(FirebaseAuth.getInstance().getUid()).build();
    protected CreatePostTitleDialogFragment cpdf;
    protected ChoosePostSportDialogFragment cpsdf;
    protected MaxPlayerDialogFragment mpdf;
    protected PostDatePickerDialog pdpd;

    protected List<Post> posts = new ArrayList<>();

    protected AnnouncementAdapter aa;
    protected FsPostManager pm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCancel();
        View rootView = inflater.inflate(R.layout.fragment_research,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        createFragments();
        FsUserManager db = new FsUserManager(FirebaseFirestore.getInstance());
        ((CompletableFuture<User>)db.fetch(FirebaseAuth.getInstance().getUid()))
                .thenAccept(this::setUser);

        Button addPost = (Button) rootView.findViewById(R.id.add_posts);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });

        aa = new AnnouncementAdapter(posts, user, pm);
        loadPosts();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(aa);

        return rootView;
    }

    private void createFragments(){
        cpdf = new CreatePostTitleDialogFragment();
        cpsdf = new ChoosePostSportDialogFragment();
        mpdf = new MaxPlayerDialogFragment();
        pdpd = new PostDatePickerDialog();
        pm = new FsPostManager(FirebaseFirestore.getInstance());
    }
    private void setUser(User user){
        this.user = (VersusUser) user;
    }
    public void createPost(){

        cpdf.show(getChildFragmentManager(), "1");
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        recyclerView = null;
    }

    @Override
    public void onTitlePositiveClick(String title) {
        newPost.setTitle(title);
        cpsdf.show(getChildFragmentManager(), "1");
    }



    @Override
    public void onCancel() {
        Timestamp ts =  new Timestamp(2023, Month.APRIL, 4, 5, 1, Timestamp.Meridiem.AM);
        Location unil = new Location("UNIL", 42, 42);
        newPost = new Post("Casual Soccer", ts, unil, new ArrayList<>(), 24, Sport.SOCCER);
    }

    @Override
    public void onSportPositiveClick(Sport sport) {
        newPost.setSport(sport);
        mpdf.show(getChildFragmentManager(), "1");
    }

    protected void loadPosts(){
        CompletableFuture<List<Post>> postsFuture = (CompletableFuture<List<Post>>) pm.fetchAll("posts");
        postsFuture.thenApply(newPosts -> {
            posts.clear();
            posts.addAll(newPosts);
            System.out.println(posts.size());
            aa.notifyDataSetChanged();
            return posts;
        });
    }

    @Override
    public void onMaxPlayerPositiveClick(int playerCount) {
        newPost.setPlayerLimit(playerCount);
        pdpd.show(getChildFragmentManager(), "1");
    }

    @Override
    public void onPickPostDate(Timestamp ts) {
        newPost.setDate(ts);
        ArrayList<VersusUser> users = new ArrayList<>();
        users.add(user);
        newPost.setPlayers(users);
        pm.insert(newPost);
        loadPosts();
    }
}
