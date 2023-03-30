package com.github.versus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class GameFragment extends Fragment {
   private  String title;
    private String sport;
   private  String location;
   private  String date;
     private int number_of_players;
    private int max_number;
    private List<PlayerToBeRated> players;
     public GameFragment(String title,String sport,String location,String date,int max_number,List<PlayerToBeRated> players){
         this.title=title;
         this.sport=sport;
         this.location=location;
         this.date=date;

         this.max_number=max_number;
         this.players=players;
     }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_game,container,false);

        ListView listView = (ListView) view.findViewById(R.id.list_players_view);

        // Create an array of items
         TextView title=(TextView)view.findViewById(R.id.title);
        TextView date=(TextView)view.findViewById(R.id.date1);
        date.setText(this.date);

        TextView sport=(TextView)view.findViewById(R.id.sport);
        TextView location=(TextView)view.findViewById(R.id.location);
        TextView num_players=(TextView)view.findViewById(R.id.number_of_players_over_max);
        title.setText(this.title);
         sport.setText(this.sport);
         location.setText(this.location);
         num_players.setText(String.valueOf(players.size()).concat("/").concat(String.valueOf(max_number)));



        PlayersListViewAdapter adapter = new PlayersListViewAdapter(getContext(), players);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);






        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
