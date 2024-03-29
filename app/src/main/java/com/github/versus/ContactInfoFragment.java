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
import com.github.versus.user.User;
import com.github.versus.user.VersusUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContactInfoFragment extends Fragment {

    //User that should be contacted
    private User user;

    //Position of the post that was displayed
    private int actualPos;

    //Displayed posts
    private List<SportEquipmentPost> displayedSportEquipmentPostList;

    //all the available posts
    private List<SportEquipmentPost> sportEquipmentPostList;

    //String that was displayed  in the filtering box
    private String contenue_EditText;

    /**
     * Constructor of the ContactInfoFragment that takes all the state of previous SportEquipmentFragment and save it to display it after the user gets back to the previous fragment
     */
    public ContactInfoFragment(String contenue_EditText, int actualPos, List<SportEquipmentPost> displayedSportEquipmentPostList, List<SportEquipmentPost> sportEquipmentPostList
            , User user) {
        this.displayedSportEquipmentPostList = displayedSportEquipmentPostList;
        this.actualPos = actualPos;
        this.sportEquipmentPostList = sportEquipmentPostList;
        this.user = user;
        this.contenue_EditText = contenue_EditText;
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

        View view = inflater.inflate(R.layout.info_contact_fragment, container, false);
        //Setting the phone number
        TextView userPhoneNumber = view.findViewById(R.id.phone_number);
        //Setting the email of the user to contact
        TextView userMail = view.findViewById(R.id.my_email);
        userPhoneNumber.setText(user.getPhone());
        userMail.setText(user.getMail());
        //Extracting the back to posts button
        View container_of_post_back = view.findViewById(R.id.back_to_posts_container);
        //Setting the listener on the back to posts button
        container_of_post_back.setOnClickListener(new View.OnClickListener() {


                                                      @Override
                                                      public void onClick(View v) {
                                                          //Preparing the transaction and commiting it to restore the previous fragment at its previous state
                                                          FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                                          FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                          SportsEquipmentsFragment frag = new SportsEquipmentsFragment(contenue_EditText, actualPos, displayedSportEquipmentPostList, sportEquipmentPostList);
                                                          //Replace the current fragment with the previous one
                                                          fragmentTransaction.replace(
                                                                  R.id.fragment_container, frag);
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
