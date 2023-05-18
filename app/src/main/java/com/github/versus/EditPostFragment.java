package com.github.versus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.db.FsPostManager;
import com.github.versus.posts.Post;
import com.github.versus.user.User;

public class EditPostFragment extends Fragment {
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
        update.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                newPost.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditText title = (EditText) view.findViewById(R.id.post_name);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fpm.update(post.getUid(), newPost);
            }
        });
        return view;
    }
}
