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

public class SportsEquipmentsFragment  extends Fragment {
    private List<SportEquipmentPost> displayedsportEquipmentPostList;
    private List<SportEquipmentPost> sportEquipmentPostList;
    private String contenue ;
    private  int actualPos;
    public SportsEquipmentsFragment(String contenueEdit,int actualPos, List<SportEquipmentPost> displayedsportEquipmentPostList, List<SportEquipmentPost> sportEquipmentPostList){
        this.actualPos=actualPos;
        this.contenue=contenueEdit;
        this.displayedsportEquipmentPostList=displayedsportEquipmentPostList;
        this.sportEquipmentPostList=sportEquipmentPostList;
    }
    public SportsEquipmentsFragment(){
        actualPos=0;
        displayedsportEquipmentPostList =new ArrayList<>();
        this.contenue="";
        //Adding dummy Posts waiting to connect to database
        VersusUser userofPosts=    new VersusUser("1", "Aymane","Lamyaghri","thelam","aymane.lamyaghri@epfl.ch", "078437822" , 5 ,"Kenitra", 14000,List.of());

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
                

        sportEquipmentPostList=List.copyOf(displayedsportEquipmentPostList);
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

        View view = inflater.inflate(R.layout.fragment_sports_equipments, container, false);
        TextView shortDesciprtion = view.findViewById(R.id.object_short_descrption);
        TextView item_price = view.findViewById(R.id.price_of_item);
        TextView item_sport = view.findViewById(R.id.sport_of_item);
        TextView item_description = view.findViewById(R.id.the_description);
        ImageView item_image = view.findViewById(R.id.picture_of_item);
       SportEquipmentPost currentPost= displayedsportEquipmentPostList.get(actualPos);
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
        ImageView right_arrow = view.findViewById(R.id.the_real_right_arrow);
        ImageView left_arrow = view.findViewById(R.id.the_real_left_arrow);


        right_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(actualPos== displayedsportEquipmentPostList.size()-1) return ;
               actualPos=actualPos+1;
                SportEquipmentPost currentPost= displayedsportEquipmentPostList.get(actualPos);
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
        left_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actualPos==0) return ;
                actualPos=actualPos-1;
                SportEquipmentPost currentPost= displayedsportEquipmentPostList.get(actualPos);
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
        EditText searchBar = view.findViewById(R.id.search_for_post);
        if(!contenue.isEmpty()){ searchBar.setText(contenue); }
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String  filter = searchBar.getText().toString();
                displayedsportEquipmentPostList.clear();
                if(filter.length() == 0){
                    displayedsportEquipmentPostList.addAll(sportEquipmentPostList);
                }
                else {
                    displayedsportEquipmentPostList.addAll(
                            sportEquipmentPostList.stream().filter(post -> {
                                return post.getSport().name().toLowerCase().contains(filter.toLowerCase())
                                        || post.getObject_name().toLowerCase().contains(filter.toLowerCase())
                                        || post.getDescription().toLowerCase().contains(filter.toLowerCase())
                                        ;
                            }).collect(Collectors.toList())
                    );
                }
                actualPos=0;
                if(displayedsportEquipmentPostList.size()==0) return ;

                SportEquipmentPost currentPost= displayedsportEquipmentPostList.get(actualPos);
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
            public void afterTextChanged(Editable editable) {}
        });

        View contactView= view.findViewById(R.id.contact_person);
        contactView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ContactInfoFragment contactInfo = new ContactInfoFragment(searchBar.getText().toString(),actualPos,displayedsportEquipmentPostList,sportEquipmentPostList,displayedsportEquipmentPostList.get(actualPos).getUser());
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
