package com.github.versus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class TrendingGames extends Fragment {

    public TrendingGames(){

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_trendingsports,container,false);
        ImageView imageView = view.findViewById(R.id.rectangle_22);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with TestFrag
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                SearchFragment search_fragment = new SearchFragment();
                fragmentTransaction.replace(R.id.fragment_container, search_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                search_fragment.setSearchBarTextFromTradingSportsFrag("Boxing");
            }
        });
        imageView.setOnHoverListener(new View.OnHoverListener() {
            @Override
            public boolean onHover(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_HOVER_ENTER:
                    case MotionEvent.ACTION_HOVER_MOVE:
                        imageView.setBackgroundResource(R.drawable.image_border_highlight);
                        break;
                    case MotionEvent.ACTION_HOVER_EXIT:
                        imageView.setBackgroundResource(R.drawable.image_border);
                        break;
                }
                return false;
            }
        });
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
