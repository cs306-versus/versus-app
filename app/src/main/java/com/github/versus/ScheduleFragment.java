package com.github.versus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
   View view =inflater.inflate(R.layout.fragment_schedule,container,false);

       ListView listView = (ListView) view.findViewById(R.id.list_view);

        // Create an array of items
        String[] items = {"Item 1", "Item 2", "Item 3"};

        // Create an ArrayAdapter to populate the ListView
        List a =new ArrayList<String>();
        a.add("haha");
        a.add("hehe");
       a.add("this");
       MyAdapter  adapter = new MyAdapter(getContext(), a);

        // Set the adapter to the ListView
        listView.setAdapter(adapter);





        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button rectangle14Button = view.findViewById(R.id.rectangle_14);

        rectangle14Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) getActivity();
                if (mainActivity != null) {
                  //  TextView myTextView = mainActivity.findViewById(R.id.football_game);
                    //myTextView.setText("New text");
                }
            }
        });
    }
}
