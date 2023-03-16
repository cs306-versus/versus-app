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
import com.github.versus.posts.FakePost;
import com.github.versus.posts.Location;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.sports.Sport;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    protected RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_research,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        Post[] posts = new Post[]{
                new FakePost("Casual Soccer Game", Sport.SOCCER,new Timestamp(2023, Month.values()[4], 2, 1, 3, null), new Location("EPFL", 42, 42), new ArrayList<>(), 5),
                new FakePost("Quadruple Sculls", Sport.ROWING, new Timestamp(2023, Month.values()[4], 2, 10, 3, null), new Location("Grimper.ch", 42, 42), new ArrayList<>(), 5),
                new FakePost("Looking for belay partner", Sport.ClIMBING, new Timestamp(2023, Month.values()[4], 2, 10, 3, null), new Location("UNIL", 42, 42), new ArrayList<>(), 5),
                new FakePost("soccer", Sport.ClIMBING, new Timestamp(2023, Month.values()[3], 10, 10, 3, null), new Location("Ithaca", 42, 42), new ArrayList<>(), 5),
                new FakePost("soccer", Sport.SOCCER, new Timestamp(2023, Month.values()[5], 15, 10, 3, null), new Location("Hello", 42, 42), new ArrayList<>(), 5),
                new FakePost("boccer", Sport.ClIMBING, new Timestamp(2023, Month.values()[5], 28, 10, 3, null), new Location("UNIL", 42, 42), new ArrayList<>(), 5),
                new FakePost("doccer", Sport.ROWING, new Timestamp(2023, Month.values()[7], 31, 10, 3, null), new Location("UNIL", 42, 42), new ArrayList<>(), 5),
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
