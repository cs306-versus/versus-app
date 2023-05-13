package com.github.versus;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.announcements.AnnouncementAdapter;
import com.github.versus.announcements.ChoosePostSportDialogFragment;
import com.github.versus.announcements.CreatePostTitleDialogFragment;
import com.github.versus.announcements.LocationPickerDialog;
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
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SearchFragment extends Fragment implements LocationPickerDialog.LocationListener,
        CreatePostTitleDialogFragment.TitleListener,
        ChoosePostSportDialogFragment.SportListener,
        MaxPlayerDialogFragment.MaxPlayerListener,
        PostDatePickerDialog.PickDateListener {


    protected RecyclerView recyclerView;
    protected Post newPost;
    protected VersusUser user = new VersusUser.Builder("fake").build();

    protected EditText searchBar;
    protected CreatePostTitleDialogFragment cpdf;
    protected ChoosePostSportDialogFragment cpsdf;
    protected MaxPlayerDialogFragment mpdf;
    protected PostDatePickerDialog pdpd;
    protected LocationPickerDialog locationPickerDialog;



    protected List<Post> posts = new ArrayList<>();
    protected List<Post> displayPosts = new ArrayList<>();
    protected String filter = "";

    protected AnnouncementAdapter aa;
    protected FsPostManager pm;
    protected Boolean isCalledSportsFrag=false;
    public String SearchTextSportsFrag="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        onCancel();
        System.out.println(getActivity()+"LELOLE");
        View rootView = inflater.inflate(R.layout.fragment_research,container,false);
        assignViews(rootView);

        Button addPost = (Button) rootView.findViewById(R.id.add_posts);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPost();
            }
        });
        FsUserManager db = null;
        if(FirebaseFirestore.getInstance() != null && FirebaseAuth.getInstance() != null && FirebaseAuth.getInstance().getUid() != null) {
            db = new FsUserManager(FirebaseFirestore.getInstance());
            ((CompletableFuture<User>)db.fetch(FirebaseAuth.getInstance().getUid()))
                    .thenAccept(this::setUser);
            user = new VersusUser.Builder(FirebaseAuth.getInstance().getUid()).build();
        }

        loadPosts();



        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LocationPickerDialog.AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                // Do something with the place here
               onLocationPositiveClick(place);
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.

            }
        }

    }


    private void setUser(User user){
        this.user = (VersusUser) user;
    }
    protected void assignViews(View rootView){
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        pm = new FsPostManager(FirebaseFirestore.getInstance());

        aa = new AnnouncementAdapter(displayPosts, user, pm);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(aa);
        cpdf = new CreatePostTitleDialogFragment();
        cpsdf = new ChoosePostSportDialogFragment();
        mpdf = new MaxPlayerDialogFragment();
        pdpd = new PostDatePickerDialog();
        locationPickerDialog=new LocationPickerDialog();
        searchBar = rootView.findViewById(R.id.search_posts);
        if(isCalledSportsFrag){ searchBar.setText(SearchTextSportsFrag);
        isCalledSportsFrag=false;

        }
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterPosts();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
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
        newPost = new Post("Casual Soccer", ts, unil, new ArrayList<>(), 24, Sport.SOCCER, "");
    }

    @Override
    public void onSportPositiveClick(Sport sport) {
        newPost.setSport(sport);
        mpdf.show(getChildFragmentManager(), "1");
    }
    @Override
    public void onLocationPositiveClick(Place place) {
        Location location = new Location(place.getName(), place.getLatLng().latitude, place.getLatLng().longitude);
        newPost.setLocation(location);
        ArrayList<VersusUser> users = new ArrayList<>();
        users.add(user);
        newPost.setPlayers(users);
        pm.insert(newPost);
        loadPosts();
    }


    protected void loadPosts(){
        CompletableFuture<List<Post>> postsFuture = (CompletableFuture<List<Post>>) pm.fetchAll("posts");
        postsFuture.thenApply(newPosts -> {
            posts.clear();
            posts.addAll(newPosts);
            filterPosts();
            return posts;
        });
    }

    protected void filterPosts(){
        filter = searchBar.getText().toString();
        displayPosts.clear();
        if(filter.length() == 0){
            displayPosts.addAll(posts);
        }
        else {
            displayPosts.addAll(
                    posts.stream().filter(post -> {
                        return post.getSport().name().toLowerCase().contains(filter.toLowerCase())
                                || post.getTitle().toLowerCase().contains(filter.toLowerCase());
                    }).collect(Collectors.toList())
            );
        }

        aa.notifyDataSetChanged();
    }

    protected void clearFilter(){
        searchBar.setText("");
        filterPosts();
    }

    @Override
    public void onMaxPlayerPositiveClick(int playerCount) {
        newPost.setPlayerLimit(playerCount);
        pdpd.show(getChildFragmentManager(), "1");

    }

    @Override
    public void onPickPostDate(Timestamp ts) {
        newPost.setDate(ts);
        locationPickerDialog.show(getChildFragmentManager(), "1");


    }
    /*
    * This method   takes as input a String that will be used to filter the posts , this method is only called
    * from the TopGamesFragment
    * */
    public void setSearchBarTextFromTradingSportsFrag(String text){
         isCalledSportsFrag=true;
         SearchTextSportsFrag=text;
    }
}
