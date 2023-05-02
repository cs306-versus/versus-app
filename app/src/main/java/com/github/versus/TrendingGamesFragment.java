package com.github.versus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class TrendingGamesFragment extends Fragment {
    private int  currentPositionInRating;
    private TrendingGamesManager  gamesManager ;

    public TrendingGamesFragment(){
        currentPositionInRating=1;
        gamesManager=new TrendingGamesManager();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_trendingsports,container,false);
        ImageView imageView = view.findViewById(R.id.rectangle_22);
        ImageView gold_medal=view.findViewById(R.id.medalgold_champion_award_winner_olympic_icon_1);
        ImageView silver_medal=view.findViewById(R.id.medalsilver_champion_award_winner_olympic_icon_1);
        ImageView bronze_medal=view.findViewById(R.id.medalbronze_champion_award_winner_olympic_icon_1);
        gold_medal.setVisibility(View.VISIBLE);
        silver_medal.setVisibility(View.INVISIBLE);
        bronze_medal.setVisibility(View.INVISIBLE);



        ImageView left_arrow=view.findViewById(R.id.arrow_left);
        ImageView right_arrow=view.findViewById(R.id.arrow_right);
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPositionInRating==1) return ;
               currentPositionInRating=currentPositionInRating-1;
               if(currentPositionInRating==1) {
                   gold_medal.setVisibility(View.VISIBLE);
                   silver_medal.setVisibility(View.INVISIBLE);

               }
                else if(currentPositionInRating==2) {
                    silver_medal.setVisibility(View.VISIBLE);
                    bronze_medal.setVisibility(View.INVISIBLE);

                }
                else if(currentPositionInRating==3){
                    bronze_medal.setVisibility(View.VISIBLE);
               }
                imageView.setImageResource(gamesManager.getSportImageAtPos(currentPositionInRating));
                TextView sport_text =(TextView)view.findViewById(R.id.sport_text);
                sport_text.setText(gamesManager.getSportNameAtPosition(currentPositionInRating));

            }


        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPositionInRating==5) return ;
                currentPositionInRating=currentPositionInRating+1;
                if(currentPositionInRating==2) {
                    gold_medal.setVisibility(View.INVISIBLE);
                    silver_medal.setVisibility(View.VISIBLE);

                }
                else if(currentPositionInRating==3) {
                    silver_medal.setVisibility(View.INVISIBLE);
                    bronze_medal.setVisibility(View.VISIBLE);

                }
                else if(currentPositionInRating==4){
                    bronze_medal.setVisibility(View.INVISIBLE);
                }
                imageView.setImageResource(gamesManager.getSportImageAtPos(currentPositionInRating));

                imageView.invalidate();
                TextView sport_text =(TextView)view.findViewById(R.id.sport_text);
                sport_text.setText(gamesManager.getSportNameAtPosition(currentPositionInRating));

            }
        });
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

                search_fragment.setSearchBarTextFromTradingSportsFrag(gamesManager.getSportNameAtPosition(currentPositionInRating));
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
        right_arrow.performClick();
        left_arrow.performClick();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
