package com.github.versus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.versus.announcements.AnnouncementAdapter;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;

import java.time.Month;
import java.util.ArrayList;

public class SearchFragment extends Fragment {
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_research,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        Timestamp ts =  new Timestamp(2023, Month.APRIL, 4, 5, 1, Timestamp.Meridiem.AM);
        Timestamp ts2 =  new Timestamp(2024, Month.MAY, 5, 5, 1, Timestamp.Meridiem.PM);
        Location unil = new Location("UNIL", 42, 42);
        Location epfl = new Location("EPFL", 105, 15);
        Post[] posts = new Post[]{
                new Post("Casual Soccer", ts, unil, new ArrayList<>(), 24, Sport.SOCCER),
                new Post("Belay me?", ts2, epfl, new ArrayList<>(), 2, Sport.ClIMBING),
                new Post("Quadruple Sculls", ts2, unil, new ArrayList<>(), 4, Sport.ROWING),
                new Post("Competitive Soccer", ts, epfl, new ArrayList<>(), 12, Sport.SOCCER),
                new Post("Test post", ts, unil, new ArrayList<>(), 55, Sport.ClIMBING),
        };
        AnnouncementAdapter aa = new AnnouncementAdapter(posts);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(aa);
        return rootView;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        recyclerView = null;
    }
}
