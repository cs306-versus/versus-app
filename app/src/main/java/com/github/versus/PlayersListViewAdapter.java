package com.github.versus;


import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

public class PlayersListViewAdapter extends ArrayAdapter<PlayerToBeRated> {
    private Context context;
    private List<PlayerToBeRated> playersToBeRated;

    public PlayersListViewAdapter(Context context, List< PlayerToBeRated> playersToBeRated) {
        super(context, R.layout.list_players, playersToBeRated);
        this.context = context;
        this.playersToBeRated = playersToBeRated;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_players, parent, false);
        PlayerToBeRated player = playersToBeRated.get(position);
        TextView player_rate= (TextView) view.findViewById(R.id.rating_of_player);
        TextView player_name= (TextView) view.findViewById(R.id.player_pseudoname);
        player_name.setText(player.get_pseudo_name());



        if(player.isPlayerRated()) {
          TextView rateText= (TextView) view.findViewById(R.id.litteral_rate);
            rateText.setVisibility(View.GONE);
            Button button = (Button)view.findViewById(R.id.rate_button);
            button.setVisibility(View.GONE);
            player_rate.setText(player.get_rate().concat("/5"));
            View viewButton =  view.findViewById(R.id.support_button_for_color);
            viewButton.setVisibility(View.GONE);



        }
        else {
            Button button = (Button)view.findViewById(R.id.rate_button);
            Spinner spinner = (Spinner)view.findViewById(R.id.spinner_rate);
            View contour = view.findViewById(R.id.rating_contour);
            TextView rateText= (TextView) view.findViewById(R.id.litteral_rate);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = spinner.getSelectedItem().toString();
                    player_rate.setText(text.concat("/5"));
                   spinner.setVisibility(View.GONE);
                    button.setVisibility(View.GONE);
                    View viewButton =  view.findViewById(R.id.support_button_for_color);
                    viewButton.setVisibility(View.GONE);
                    player_rate.setVisibility(View.VISIBLE);
                    contour.setVisibility(View.VISIBLE);
                    rateText.setVisibility(View.GONE);

                }
            });
            contour.setVisibility(View.INVISIBLE);
            player_rate.setVisibility(View.INVISIBLE);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,R.array.rating_values,android.R.layout.simple_spinner_item);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


            spinner.setAdapter(adapter);
           spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
               @Override
               public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


               }

               @Override
               public void onNothingSelected(AdapterView<?> parent) {

               }
           });




                }



return view;
        }





    }

