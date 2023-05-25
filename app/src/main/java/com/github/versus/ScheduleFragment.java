package com.github.versus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.github.versus.db.FsScheduleManager;
import com.github.versus.posts.Post;
import com.github.versus.posts.Timestamp;
import com.github.versus.schedule.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScheduleFragment extends Fragment {
    private List<Integer> months;
    private List<Integer> years;
    private List<Post> posts;
    private FsScheduleManager  schedulermanager;

    public ScheduleFragment(){
        posts=new ArrayList<>();
        schedulermanager= new FsScheduleManager(FirebaseFirestore.getInstance()) ;

        months= new ArrayList<>();
        years=new ArrayList<>();






    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewFrag =inflater.inflate(R.layout.fragment_schedule,container,false);

        ListView listView = (ListView) viewFrag.findViewById(R.id.list_view);



        TextView dateText=viewFrag.findViewById(R.id.date);
        LocalDate today = LocalDate.now();
        int dayOfMonth = today.getDayOfMonth();
        Month month = today.getMonth();
        int year = today.getYear();

        DayOfWeek dayOfWeek = today.getDayOfWeek();

        List c=new ArrayList<List<String>>();

        try {schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(),new Timestamp(year,month,dayOfMonth,0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
            posts=sched.getPosts();
            sched.getPosts().forEach(post -> {
                List a =new ArrayList<String>();
                a.add(post.getSport().toString());
                a.add(post.getLocation().toString());
                a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                c.add(a);

            });
            dateText.setText( dayOfMonth+" "+month.toString()+" , "+year);

            ListViewAdapter adapter = new ListViewAdapter(getContext(), c);

            // Set the adapter to the ListView
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TextView sportText = (TextView)(view.findViewById(R.id.sport_text));
                    TextView locationText = (TextView)(view.findViewById(R.id.Location_text));
                    TextView date = (TextView)(viewFrag.findViewById(R.id.date));

                    Post p  = posts.stream().filter(post -> {


                        return  post.getSport().name().equals(sportText.getText().toString() );
                    }).collect(Collectors.toList()).get(0);


                    //Dummy List Of players waiting to connect this to the concept of user in the DB
                    List<PlayerToBeRated> fakeList = new ArrayList<>();
                    fakeList.add(new PlayerToBeRated(true,"Abdess_piquant","4"));
                    fakeList.add(new PlayerToBeRated(true,"Aymane_lam","5"));
                    fakeList.add(new PlayerToBeRated(true,"Mernissi_Adam","4"));
                    fakeList.add(new PlayerToBeRated(true,"Si-Ziazi","5"));

                   // Create the new GameFragment  and transit to this new fragment
                    GameFragment gameFragment = new GameFragment(p.getTitle(),sportText.getText().toString(),locationText.getText().toString(),date.getText().toString(),p.getPlayerLimit(),fakeList);
                    fragmentTransaction.replace(R.id.fragment_container, gameFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        });


        }
        catch (Exception e ) {}






        return viewFrag;
    }
    private String convertMonthIndexToNameMonth(int month_index){
        if(month_index==1) return "January";
        else if(month_index==2) return "February";
        else if(month_index==3) return "March";
        else if(month_index==4) return "April";
        else if(month_index==5) return "May";
        else if(month_index==6) return "June";
        else if(month_index==7) return "July";
        else if(month_index==8) return "August";
        else if(month_index==9) return "September";
        else if(month_index==10) return "October";
        else if(month_index==11) return "November";
        else return "December";
    }
    private void  decrease_date(TextView date,int month_index){
        int number_date= Integer.parseInt(date.getText().toString());
        if(months.get(month_index)==5 ||months.get(month_index)==7  ||months.get(month_index)==10 ||months.get(month_index)==12 ){
            if(number_date-7>0){
                date.setText(String.valueOf((number_date-7)));

            }
            else {

                date.setText(String.valueOf(30+number_date-7));
                if(months.get(month_index)-1>=1)
                    months.set(month_index,months.get(month_index)-1);
                else {
                    months.set(month_index,12);
                    years.set(month_index,years.get(month_index)-1);

                }

            }
        }
        else if(months.get(month_index)!=3  ){
            if(number_date-7>0){
                date.setText(String.valueOf((number_date-7)));

            }
            else {

                date.setText(String.valueOf(31+number_date-7));
                if(months.get(month_index)-1>=1)
                    months.set(month_index,months.get(month_index)-1);
                else {
                    months.set(month_index,12);
                    years.set(month_index,years.get(month_index)-1);

                }

            }
        }
        else {
            if(number_date-7>0){
                date.setText(String.valueOf((number_date-7)));

            }
            else {

                date.setText(String.valueOf(28+number_date-7));
                if(months.get(month_index)-1>=1)
                    months.set(month_index,months.get(month_index)-1);
                else {
                    months.set(month_index,12);
                    years.set(month_index,years.get(month_index)-1);

                }

            }
        }}

    private void  increase_date(TextView date,int month_index){
        int number_date= Integer.parseInt(date.getText().toString());
        if(months.get(month_index)==1 || months.get(month_index)==3 ||months.get(month_index)==5 ||months.get(month_index)==7 ||months.get(month_index)==8 ||months.get(month_index)==10 ||months.get(month_index)==12 ){
            if(number_date+7<=31){
                date.setText(String.valueOf((number_date+7)));

            }
            else {

                date.setText(String.valueOf(7-(31-number_date)));
                if(months.get(month_index)+1<=12)
                    months.set(month_index,months.get(month_index)+1);
                else {
                    months.set(month_index,1);
                    years.set(month_index,years.get(month_index)+1);

                }

            }
        }
        else if(months.get(month_index)!=2  ){
            if(number_date+7<=30){
                date.setText(String.valueOf((number_date+7)));

            }
            else {

                date.setText(String.valueOf(7-(30-number_date)));
                if(months.get(month_index)+1<=12)
                    months.set(month_index,months.get(month_index)+1);
                else {
                    months.set(month_index,1);
                    years.set(month_index,years.get(month_index)+1);

                }

            }
        }
        else {
            if(number_date+7<=28){
                date.setText(String.valueOf((number_date+7)));

            }
            else {

                date.setText(String.valueOf(7-(28-number_date)));
                months.set(month_index,months.get(month_index)+1);

            }
        }}
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get the current date
        LocalDate today = LocalDate.now();

        // Subtract days until we find the first Monday
        while (today.getDayOfWeek() != DayOfWeek.MONDAY) {
            today = today.minusDays(1);
        }
        months.add(today.getMonthValue());
        years.add(today.getYear());

        today=today.plusDays(1);

        months.add(today.getMonthValue());
        years.add(today.getYear());

        today=today.plusDays(1);

        months.add(today.getMonthValue());
        years.add(today.getYear());

        today=today.plusDays(1);

        months.add(today.getMonthValue());
        years.add(today.getYear());

        today=today.plusDays(1);

        months.add(today.getMonthValue());
        years.add(today.getYear());

        today=today.plusDays(1);

        months.add(today.getMonthValue());
        years.add(today.getYear());

        today=today.plusDays(1);

        months.add(today.getMonthValue());
        years.add(today.getYear());



        ImageView myImageView = view.findViewById(R.id.arrow_image_2);
        myImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView date_monday =(TextView)view.findViewById(R.id.Monday_date);
                increase_date(date_monday,0);
                TextView date_tuesday =(TextView)view.findViewById(R.id.Tuesday_date);
                increase_date(date_tuesday,1);
                TextView date_wednesday =(TextView)view.findViewById(R.id.Wednesday_date);
                increase_date(date_wednesday,2);
                TextView date_thursday =(TextView)view.findViewById(R.id.Thursday_date);
                increase_date(date_thursday,3);
                TextView date_friday =(TextView)view.findViewById(R.id.Friday_date);
                increase_date(date_friday,4);
                TextView date_saturday =(TextView)view.findViewById(R.id.Saturday_date);
                increase_date(date_saturday,5);
                TextView date_sunday =(TextView)view.findViewById(R.id.Sunday_date);
                increase_date(date_sunday,6);
                TextView dateText=view.findViewById(R.id.date);

                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(6)).concat(" , "  ).concat(String.valueOf(years.get(6))))));
                ListViewAdapter adapter = new ListViewAdapter(getContext(), new ArrayList<>());
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
                Button button= view.findViewById(R.id.Sunday_button);
                button.performClick();


            }
        });
        ImageView myImageView2 = view.findViewById(R.id.arrow_11);
        myImageView2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextView date_monday =(TextView)view.findViewById(R.id.Monday_date);
                decrease_date(date_monday,0);

                TextView date_tuesday =(TextView)view.findViewById(R.id.Tuesday_date);
                decrease_date(date_tuesday,1);
                TextView date_wednesday =(TextView)view.findViewById(R.id.Wednesday_date);
                decrease_date(date_wednesday,2);
                TextView date_thursday =(TextView)view.findViewById(R.id.Thursday_date);
                decrease_date(date_thursday,3);
                TextView date_friday =(TextView)view.findViewById(R.id.Friday_date);
                decrease_date(date_friday,4);
                TextView date_saturday =(TextView)view.findViewById(R.id.Saturday_date);
                decrease_date(date_saturday,5);
                TextView date_sunday =(TextView)view.findViewById(R.id.Sunday_date);
                decrease_date(date_sunday,6);
                TextView dateText=view.findViewById(R.id.date);

                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(6)).concat(" , "  ).concat(String.valueOf(years.get(6))))));
                ListViewAdapter adapter = new ListViewAdapter(getContext(), new ArrayList<>());
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
                Button button= view.findViewById(R.id.Sunday_button);
                button.performClick();

            }
        });








        Button mondayButton = view.findViewById(R.id.Monday_button);

        mondayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Monday_date);


                List c=new ArrayList<List<String>>();

                try {  schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(),new Timestamp(years.get(0),Month.of(months.get(0)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {

                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Monday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(0)).concat(" , "  ).concat(String.valueOf(years.get(0))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });}
                catch (Exception e ) {}

            }
        });
        Button tuesdayButton = view.findViewById(R.id.Tuesday_button);

        tuesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Tuesday_date);

                List c=new ArrayList<List<String>>();

                try { schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(),new Timestamp(years.get(1),Month.of(months.get(1)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {
                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Tuesday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(1)).concat(" , "  ).concat(String.valueOf(years.get(1))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });}
                catch (Exception e ) {}

            }
        });
        Button wednesdayButton = view.findViewById(R.id.Wednesday_button);

        wednesdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Wednesday_date);

                List c=new ArrayList<List<String>>();

                try {  schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(), new Timestamp(years.get(2),Month.of(months.get(2)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {
                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Wednesday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(2)).concat(" , "  ).concat(String.valueOf(years.get(2))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });}
                catch (Exception e ) {}

            }
        });
        Button thursdayButton = view.findViewById(R.id.Thursday_button);

        thursdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Thursday_date);

                List c=new ArrayList<List<String>>();

                try { schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(),new Timestamp(years.get(3),Month.of(months.get(3)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {
                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Thursday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(3)).concat(" , "  ).concat(String.valueOf(years.get(3))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });}
                catch (Exception e ) {}

            }
        });
        Button fridayButton = view.findViewById(R.id.Friday_button);

        fridayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Friday_date);

                List c=new ArrayList<List<String>>();

                try { schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(),new Timestamp(years.get(4),Month.of(months.get(4)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {
                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Friday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(4)).concat(" , "  ).concat(String.valueOf(years.get(4))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });}
                catch (Exception e ) {}

            }
        });
        Button saturdayButton = view.findViewById(R.id.Saturday_button);

        saturdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Saturday_date);

                List c=new ArrayList<List<String>>();

                try { schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(),new Timestamp(years.get(5),Month.of(months.get(5)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {
                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Saturday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(5)).concat(" , "  ).concat(String.valueOf(years.get(5))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });}
                catch (Exception e ) {}

            }
        });
        Button sundayButton = view.findViewById(R.id.Sunday_button);

        sundayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView MondayText=(TextView)view.findViewById(R.id.Sunday_date);

                List c=new ArrayList<List<String>>();

                try { schedulermanager.getScheduleOnDate(FirebaseAuth.getInstance().getUid(), new Timestamp(years.get(6),Month.of(months.get(6)),Integer.parseInt(MondayText.getText().toString()),0,0, Timestamp.Meridiem.AM)).thenAccept(sched -> {
                    posts=sched.getPosts();
                    sched.getPosts().forEach(post -> {
                        List a =new ArrayList<String>();
                        a.add(post.getSport().toString());
                        a.add(post.getLocation().toString());
                        a.add(post.getDate().getHour()+":00 "+post.getDate().getMeridiem().toString());
                        c.add(a);

                    });
                    TextView dateText=view.findViewById(R.id.date);
                    TextView date_sunday =(TextView)view.findViewById(R.id.Sunday_date);
                    dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(6)).concat(" , "  ).concat(String.valueOf(years.get(6))))));

                    ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                    ListView listView = (ListView) view.findViewById(R.id.list_view);
                    // Set the adapter to the ListView
                    listView.setAdapter(adapter);
                });

                }
                catch (Exception e ) {}

            }
        });}}

