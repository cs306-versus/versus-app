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


    // Represents the current position in the rating of trending games
    private int currentPositionInRating;

    // Manages the trending games data, such as sport names and images
    private TrendingGamesManager gamesManager;


    /**
     * The constructor initializes the currentPositionInRating to 1 and creates a new instance of TrendingGamesManager.
     */
    public TrendingGamesFragment() {
        //Initialise the Position to the first position, this will be shown by default.
        currentPositionInRating = 1;
        //Initialize the current TrendingGamesManager
        gamesManager = new TrendingGamesManager();

    }

    /**
     * The onCreateView method inflates the view from the fragment_trendingsports.xml layout file.
     * It sets the visibility of the gold medal to visible and the visibility of the silver and bronze medals to invisible.
     * It also sets click listeners for the left and right arrows and the image view.
     *
     * @param inflater           - The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          - The parent view that the fragment's UI should be attached to
     * @param savedInstanceState - This fragment is being re-constructed from a previous saved state as given here.
     * @return - the inflated view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trendingsports, container, false);

        //ImageView that contains the picture of the sport at the current position
        ImageView imageView = view.findViewById(R.id.rectangle_22);

        //Gold medal for the Top1 sport, only visible when the Top1 sport is visible
        ImageView gold_medal = view.findViewById(R.id.medalgold_champion_award_winner_olympic_icon_1);

        //Silver medal for the Top2 sport, only visible when the Top1 sport is visible
        ImageView silver_medal = view.findViewById(R.id.medalsilver_champion_award_winner_olympic_icon_1);

        //Bronze medal for the Top3 sport, only visible when the Top1 sport is visible
        ImageView bronze_medal = view.findViewById(R.id.medalbronze_champion_award_winner_olympic_icon_1);

        //SetVisiblity of the golden medal by default to visible
        gold_medal.setVisibility(View.VISIBLE);

        //SetVisiblity of the silver medal by default to visible
        silver_medal.setVisibility(View.INVISIBLE);

        //SetVisiblity of the bronze medal by default to visible
        bronze_medal.setVisibility(View.INVISIBLE);

        //ImageView of the left arrow to switch to next trending sport
        ImageView left_arrow = view.findViewById(R.id.arrow_left);

        //ImageView of the left arrow to switch to next trending sport
        ImageView right_arrow = view.findViewById(R.id.arrow_right);

        //Add the OnClickLister to the leftarrow to switch to next trending sport
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPositionInRating == 1) return;
                currentPositionInRating = currentPositionInRating - 1;
                if (currentPositionInRating == 1) {
                    gold_medal.setVisibility(View.VISIBLE);
                    silver_medal.setVisibility(View.INVISIBLE);

                } else if (currentPositionInRating == 2) {
                    silver_medal.setVisibility(View.VISIBLE);
                    bronze_medal.setVisibility(View.INVISIBLE);

                } else if (currentPositionInRating == 3) {
                    bronze_medal.setVisibility(View.VISIBLE);
                }
                imageView.setImageResource(gamesManager.getSportImageAtPos(currentPositionInRating));
                TextView sport_text = (TextView) view.findViewById(R.id.sport_text);
                sport_text.setText(gamesManager.getSportNameAtPosition(currentPositionInRating));

            }


        });
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPositionInRating == 5) return;
                currentPositionInRating = currentPositionInRating + 1;
                if (currentPositionInRating == 2) {
                    gold_medal.setVisibility(View.INVISIBLE);
                    silver_medal.setVisibility(View.VISIBLE);

                } else if (currentPositionInRating == 3) {
                    silver_medal.setVisibility(View.INVISIBLE);
                    bronze_medal.setVisibility(View.VISIBLE);

                } else if (currentPositionInRating == 4) {
                    bronze_medal.setVisibility(View.INVISIBLE);
                }
                imageView.setImageResource(gamesManager.getSportImageAtPos(currentPositionInRating));

                imageView.invalidate();
                TextView sport_text = (TextView) view.findViewById(R.id.sport_text);
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


    /**
     * Called after the view hierarchy of the fragment is inflated and ready for use.
     *
     * @param view The inflated view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
