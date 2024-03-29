package com.github.versus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.auth.VersusAuthenticator;
import com.github.versus.db.FsUserManager;
import com.github.versus.friends.UserAnnouncementAdapter;
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class SearchFriendsFragment extends Fragment{


    protected RecyclerView recyclerView;
    protected User user;

    protected EditText searchBar;

    protected List<User> users = new ArrayList<>();
    protected List<User> displayUsers = new ArrayList<>();
    protected String filter = "";

    protected UserAnnouncementAdapter aa;
    protected FsUserManager pm;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_users_fragment,container,false);
        assignViews(rootView);

        user = VersusAuthenticator.getInstance(FirebaseAuth.getInstance()).currentUser();

        loadUsers();



        return rootView;
    }

    protected void assignViews(View rootView){

        recyclerView = rootView.findViewById(R.id.search_users_recycler_view);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        pm = new FsUserManager(FirebaseFirestore.getInstance());

        aa = new UserAnnouncementAdapter(displayUsers, user, pm, getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(aa);

        searchBar = rootView.findViewById(R.id.search_users);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filterUsers();
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
    }



    @Override
    public void onDestroyView(){
        super.onDestroyView();
        recyclerView = null;
    }




    protected void loadUsers(){
        ((CompletableFuture<List<User>>) pm.fetchAll("users")).thenApply(newUsers -> {
            users.clear();
            users.addAll(newUsers);
            filterUsers();
            return users;
        });
    }

    protected void filterUsers(){
        filter = searchBar.getText().toString();
        displayUsers.clear();
        if(filter.length() == 0){
            displayUsers.addAll(users);
        }
        else {
            displayUsers.addAll(
                    users.stream().filter(user -> {
                        return user.getFirstName().toLowerCase().contains(filter.toLowerCase())
                                || user.getLastName().toLowerCase().contains(filter.toLowerCase())
                                || user.getUID().toLowerCase().contains(filter.toLowerCase());
                    }).collect(Collectors.toList())
            );
        }

        aa.notifyDataSetChanged();
    }






}
