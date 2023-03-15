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
        TextView dateText=view.findViewById(R.id._7_march___2023);
        dateText.setText("12 March , 2023");
        List a =new ArrayList<String>();
        a.add("Football Game");
        a.add("Unil-Sport");
       a.add("10:00 AM");
        List b =new ArrayList<String>();
        b.add("BasketBall Game");
        b.add("Echandens");
        b.add("14:00 PM");
        List c=new ArrayList<List<String>>();
                c.add(a);
        c.add(b);
       MyAdapter  adapter = new MyAdapter(getContext(), c);

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
                List a =new ArrayList<String>();
                a.add("Tennis Match");
                a.add("Unil-Sport");
                a.add("11:00 AM");
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("16 March , 2023");

                List c=new ArrayList<List<String>>();
                c.add(a);
                MyAdapter  adapter = new MyAdapter(getContext(), c);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle15Button = view.findViewById(R.id.rectangle_15);

        rectangle15Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List a =new ArrayList<String>();
                a.add("HandBall Game");
                a.add("Chavannes-Epenex");
                a.add("02:00 PM");
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("17 March , 2023");

                List c=new ArrayList<List<String>>();
                c.add(a);
                MyAdapter  adapter = new MyAdapter(getContext(), c);

                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle16Button = view.findViewById(R.id.rectangle_16);

        rectangle16Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List a =new ArrayList<String>();
                a.add("Football Game");
                a.add("Unil-Sport");
                a.add("05:00 PM");
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("18 March , 2023");
                List c=new ArrayList<List<String>>();
                c.add(a);
                MyAdapter  adapter = new MyAdapter(getContext(), c);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle11Button = view.findViewById(R.id.rectangle_11);

        rectangle11Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("15 March , 2023");

                List c=new ArrayList<List<String>>();
                MyAdapter  adapter = new MyAdapter(getContext(), c);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle18Button = view.findViewById(R.id.rectangle_18);

        rectangle18Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView) view.findViewById(R.id.list_view);

                List a =new ArrayList<String>();
                a.add("Football Game");
                a.add("Unil-Sport");
                a.add("10:00 AM");
                List b =new ArrayList<String>();
                b.add("BasketBall Game");
                b.add("Echandens");
                b.add("14:00 PM");
                List c=new ArrayList<List<String>>();
                c.add(a);
                c.add(b);
                MyAdapter  adapter = new MyAdapter(getContext(), c);
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("12 March , 2023");
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle3Button = view.findViewById(R.id.rectangle_3);

        rectangle3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView) view.findViewById(R.id.list_view);

                List a =new ArrayList<String>();
                a.add("Football Game");
                a.add("Unil-Sport");
                a.add("07:00 PM");
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("14 March , 2023");
                List c=new ArrayList<List<String>>();
                c.add(a);
                MyAdapter  adapter = new MyAdapter(getContext(), c);

                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle17Button = view.findViewById(R.id.rectangle_17);

        rectangle17Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                TextView dateText=view.findViewById(R.id._7_march___2023);
                dateText.setText("13 March , 2023");


                List c=new ArrayList<List<String>>();
                MyAdapter  adapter = new MyAdapter(getContext(), c);

                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
    }
}
