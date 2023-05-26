package com.github.versus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.user.User;

public class AddFriendFragment extends Fragment {

    User user;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.user = (User) getArguments().get("user");
        View root = inflater.inflate(R.layout.fragment_add_friend, container, false);
        TextView real_name = root.findViewById(R.id.user_realname);
        TextView username = root.findViewById(R.id.username);
        real_name.setText(user.getFirstName() + " " + user.getLastName());
        username.setText(user.getUserName());

        return root;
    }


}