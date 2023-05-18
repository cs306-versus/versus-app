package com.github.versus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Post;
import com.github.versus.sports.Sport;
import com.github.versus.user.User;

import java.util.Arrays;
import java.util.stream.Collectors;

public class EditPostFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private Post post;
    private Post newPost;
    private FsPostManager fpm;
    public EditPostFragment(FsPostManager fpm){
        this.fpm = fpm;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.edit_post, container, false);
        this.post = (Post) getArguments().get("post");
        newPost = this.post;
        Button update = (Button) view.findViewById(R.id.update_post);
        EditText title = (EditText) view.findViewById(R.id.post_name);
        Spinner sport = (Spinner) view.findViewById(R.id.edit_sport);
        sport.setOnItemSelectedListener(this);
        sport.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.edit_sport_spinner,
                Arrays.stream(Sport.values()).map(sn -> sn.name()).collect(Collectors.toList())));

        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence.toString());
                newPost.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fpm.update(post.getUid(), newPost);
            }
        });
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Sport sport = Sport.valueOf((String) adapterView.getItemAtPosition(i));
        post.setSport(sport);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
