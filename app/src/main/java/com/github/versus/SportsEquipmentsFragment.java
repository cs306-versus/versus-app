package com.github.versus;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.versus.sports.Sport;
import com.github.versus.user.VersusUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents the Fragment where the Sport Equipments will be displayed
 */
public class SportsEquipmentsFragment extends Fragment {

    //Posts that are currently being displayed
    private List<SportEquipmentPost> displayedsportEquipmentPostList;

    //All the available posts
    private List<SportEquipmentPost> sportEquipmentPostList;

    //String representing the contenue of the filtering box
    private String contenue;

    //Actual position of the post that is displayed in the list
    private int actualPos;

    /**
     * Constructor  that takes the contenue of the EditText and the displayed posts and all the available Posts and display them.
     */

    public SportsEquipmentsFragment(String contenueEdit, int actualPos, List<SportEquipmentPost> displayedsportEquipmentPostList, List<SportEquipmentPost> sportEquipmentPostList) {
        this.actualPos = actualPos;
        this.contenue = contenueEdit;
        this.displayedsportEquipmentPostList = displayedsportEquipmentPostList;
        this.sportEquipmentPostList = sportEquipmentPostList;
    }


    /**
     * Constructor that takes no arguments and for now , creates dummy data to show some random posts, we will connect it to the DB in the final Sprint.
     */
    public SportsEquipmentsFragment() {
        //Define the actual position as the first post
        actualPos = 0;

        //Here we  create some random posts  that we display , we will connect  this to DB later.
        displayedsportEquipmentPostList = new ArrayList<>();
        this.contenue = "";
        //Adding dummy Posts waiting to connect to database
        VersusUser userofPosts = new VersusUser("1", "Aymane", "Lamyaghri", "thelam", "aymane.lamyaghri@epfl.ch", "078437822", 5, "Kenitra", 14000, List.of(),  List.of());

        displayedsportEquipmentPostList.add(new SportEquipmentPost("Nike Soccer Ball",
                Sport.SOCCER,
                32,
                "Nice Nike Ball from the edition X96 of 2006",
                "",
                "Nike_Football",
                userofPosts


        ));
        displayedsportEquipmentPostList.add(new SportEquipmentPost("Boxing Gloves,",
                Sport.SOCCER,
                20,
                "Nice Boxing gloves Nike",
                "",
                "Boxing_Gloves",
                userofPosts


        ));
        displayedsportEquipmentPostList.add(new SportEquipmentPost("BasketBall Adidas,",
                Sport.SOCCER,
                40,
                "BasketBall adidas from the edition 2003",
                "",
                "BasketBall",
                userofPosts


        ));


        sportEquipmentPostList = List.copyOf(displayedsportEquipmentPostList);
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
        //Here we inflate our layout of the SportEquipments fragment
        View view = inflater.inflate(R.layout.fragment_sports_equipments, container, false);
        //Extract the shortDescription textview
        TextView shortDesciprtion = view.findViewById(R.id.object_short_descrption);
        //Extract the item price textview
        TextView item_price = view.findViewById(R.id.price_of_item);
        //Extract the sport item TextView
        TextView item_sport = view.findViewById(R.id.sport_of_item);
        //Extract the itemdescirption textView and the image of the post
        TextView item_description = view.findViewById(R.id.the_description);
        ImageView item_image = view.findViewById(R.id.picture_of_item);
        //Get the current post
        SportEquipmentPost currentPost = displayedsportEquipmentPostList.get(actualPos);

        //Set all the previous descriptions of the post accordingly
        shortDesciprtion.setText(currentPost.getObject_name());
        item_price.setText(String.valueOf(currentPost.getPrice()));
        item_sport.setText(currentPost.getSport().name);
        item_description.setText(currentPost.getDescription());

        //Set the correct dummy picture for the post
        switch (currentPost.getPicture_local()) {
            case "Nike_Football":
                item_image.setImageResource(R.drawable.nikefootball);
                break;
            case "Boxing_Gloves":
                item_image.setImageResource(R.drawable.boxinggloves);

                break;
            default:
                item_image.setImageResource(R.drawable.adidasbasketball);

                break;
        }
        //Extracting the left and right arrow that switch posts
        ImageView right_arrow = view.findViewById(R.id.the_real_right_arrow);
        ImageView left_arrow = view.findViewById(R.id.the_real_left_arrow);

        //Setting the onClickLister for the right arrow, this commentary is similar to the leftArrow and will not be repeated
        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Returning if the actualPos is in the limit
                if (actualPos == displayedsportEquipmentPostList.size() - 1) return;

                //Increasing the actual position
                actualPos = actualPos + 1;

                //getting the new current post and setting all the descriptions accordingly
                SportEquipmentPost currentPost = displayedsportEquipmentPostList.get(actualPos);
                shortDesciprtion.setText(currentPost.getObject_name());
                item_price.setText(String.valueOf(currentPost.getPrice()));
                item_sport.setText(currentPost.getSport().name);
                item_description.setText(currentPost.getDescription());
                //Setting the correct dummy picture of the post
                switch (currentPost.getPicture_local()) {
                    case "Nike_Football":
                        item_image.setImageResource(R.drawable.nikefootball);
                        break;
                    case "Boxing_Gloves":
                        item_image.setImageResource(R.drawable.boxinggloves);

                        break;
                    default:
                        item_image.setImageResource(R.drawable.adidasbasketball);

                        break;
                }
            }
        });
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actualPos == 0) return;
                actualPos = actualPos - 1;
                SportEquipmentPost currentPost = displayedsportEquipmentPostList.get(actualPos);
                shortDesciprtion.setText(currentPost.getObject_name());
                item_price.setText(String.valueOf(currentPost.getPrice()));
                item_sport.setText(currentPost.getSport().name);
                item_description.setText(currentPost.getDescription());
                switch (currentPost.getPicture_local()) {
                    case "Nike_Football":
                        item_image.setImageResource(R.drawable.nikefootball);
                        break;
                    case "Boxing_Gloves":
                        item_image.setImageResource(R.drawable.boxinggloves);

                        break;
                    default:
                        item_image.setImageResource(R.drawable.adidasbasketball);

                        break;
                }
            }
        });

        //Extracting the EditText that is used to filter posts
        EditText searchBar = view.findViewById(R.id.search_for_post);
        //If is empty do nothing
        if (!contenue.isEmpty()) {
            searchBar.setText(contenue);
        }

        //Add  the onTextChanged listener
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Extracting the filtering String
                String filter = searchBar.getText().toString();
                //Clearing all the displayed Posts
                displayedsportEquipmentPostList.clear();
                //If no filter restore all the posts
                if (filter.length() == 0) {
                    displayedsportEquipmentPostList.addAll(sportEquipmentPostList);
                } else {
                    //Otherwise we filter the posts and keep only those who include the filtering keyword
                    displayedsportEquipmentPostList.addAll(
                            sportEquipmentPostList.stream().filter(post -> {
                                return post.getSport().name().toLowerCase().contains(filter.toLowerCase())
                                        || post.getObject_name().toLowerCase().contains(filter.toLowerCase())
                                        || post.getDescription().toLowerCase().contains(filter.toLowerCase())
                                        ;
                            }).collect(Collectors.toList())
                    );
                }
                //Here since the filtering changes we update all the descriptions of the displayed post which might change
                actualPos = 0;
                if (displayedsportEquipmentPostList.size() == 0) return;

                //Extracting the currentPost and setting all its attributes accordingly
                SportEquipmentPost currentPost = displayedsportEquipmentPostList.get(actualPos);
                shortDesciprtion.setText(currentPost.getObject_name());
                item_price.setText(String.valueOf(currentPost.getPrice()));
                item_sport.setText(currentPost.getSport().name);
                item_description.setText(currentPost.getDescription());
                switch (currentPost.getPicture_local()) {
                    case "Nike_Football":
                        item_image.setImageResource(R.drawable.nikefootball);
                        break;
                    case "Boxing_Gloves":
                        item_image.setImageResource(R.drawable.boxinggloves);

                        break;
                    default:
                        item_image.setImageResource(R.drawable.adidasbasketball);

                        break;
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //Here we extract the contact_person button and set its onclick listerner
        View contactView = view.findViewById(R.id.contact_person);
        contactView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //We prepare the transaction to go to a new fragment which is the contact  info fragment of the owner of the post.
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ContactInfoFragment contactInfo = new ContactInfoFragment(searchBar.getText().toString(), actualPos, displayedsportEquipmentPostList, sportEquipmentPostList, displayedsportEquipmentPostList.get(actualPos).getUser());
                        fragmentTransaction.replace(R.id.fragment_container, contactInfo);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                }
        );


        return view;
    }


    /**
     * Called after the view hierarchy of the fragment is inflated and ready for use.
     *
     * @param view               The inflated view of the fragment.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}