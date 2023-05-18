package com.github.versus;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.versus.offline.LocationConverter;
import com.github.versus.offline.TimeStampConverter;
import com.github.versus.posts.Location;
import com.github.versus.posts.Timestamp;
import com.github.versus.weather.Weather;
import com.github.versus.weather.WeatherService;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        //TODO : THIS IS HARDCODED BECAUSE THE LOCATION IN POST HAS NONEXISTENT COORDINATES THE STRING SHOULD BE CHANGED TO location WHEN IT'S FIXED
        Location position= LocationConverter.GameFragmentLocation("CHAVANNES (45.6, 4.4)");
        Timestamp timestamp= TimeStampConverter.GameFragmentTimestamp(date);
        TextView weather_txt= view.findViewById (R.id.weather);
        ImageView weather_icon= view.findViewById(R.id.weathericon);
        WeatherService.getWeatherAsynchronously(position,timestamp).thenAccept(
                (fetched)->{
                        String conditions= fetched.get("conditions");
                        weather_icon.setImageResource(toPicture(Weather.extractWeather(fetched)));
                        String displayed= String.format(Locale.ENGLISH,"%s \n  %s°C \n ",conditions,fetched.get("temperature"));
                        weather_txt.setText(conditions== null ? "Weather Unavailable": displayed );
                }
        );
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
    private int toPicture(Weather weather){
        switch (weather) {
            case rainy:
                return R.drawable.rainy;
            case snowy:
                return R.drawable.snowy;
            case cloudy:
                return R.drawable.cloudy;
            case clear_day:
                return R.drawable.clear_day;
            case clear_night:
                return R.drawable.clear_night;
            case foggy:
                return R.drawable.foggy;
            case windy:
                return R.drawable.windy;
            case cloudy_day:
                return R.drawable.cloudy_day;
            case cloudy_night:
                return R.drawable.cloudy_night;
            default:
                return R.drawable.weather_unavailable;
        }
    }
}
