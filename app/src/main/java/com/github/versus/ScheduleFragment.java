package com.github.versus;

import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class ScheduleFragment extends Fragment {
    private List<Integer> months;
    private List<Integer> years;

    public ScheduleFragment(){
       months= new ArrayList<>();
       months.add(3);months.add(3);months.add(3);months.add(3);months.add(3);months.add(4);months.add(3);
       years=new ArrayList<>();
       years.add(2023);years.add(2023);years.add(2023);years.add(2023);years.add(2023);years.add(2023);years.add(2023);
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
   View viewFrag =inflater.inflate(R.layout.fragment_schedule,container,false);

       ListView listView = (ListView) viewFrag.findViewById(R.id.list_view);

        // Create an array of items
        String[] items = {"Item 1", "Item 2", "Item 3"};


        TextView dateText=viewFrag.findViewById(R.id.date);
        dateText.setText("31 March , 2023");
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
               int max=2;
               if(!sportText.getText().toString().contains("Tennis")) max=8;
               List<PlayerToBeRated> listPlayers=new ArrayList<>();
                listPlayers.add(new PlayerToBeRated(false,"Derouich","3"));
                listPlayers.add(new PlayerToBeRated(true,"Aymane_Lam","5"));
                listPlayers.add(new PlayerToBeRated(true,"Mernissi","2"));
                if(sportText.getText().toString().contains("Tennis")) listPlayers=listPlayers.subList(0,1);
                GameFragment gameFragment = new GameFragment("Casual Game",sportText.getText().toString(),locationText.getText().toString(),date.getText().toString(),max,listPlayers);
                fragmentTransaction.replace(R.id.fragment_container, gameFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });





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








        Button rectangle14Button = view.findViewById(R.id.Monday_button);

        rectangle14Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
                List a =new ArrayList<String>();
                a.add("Tennis Match");
                a.add("Unil-Sport");
                a.add("11:00 AM");
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Monday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(0)).concat(" , "  ).concat(String.valueOf(years.get(0))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;

                List c=new ArrayList<List<String>>();
                c.add(a);
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle15Button = view.findViewById(R.id.Tuesday_button);

        rectangle15Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
                List a =new ArrayList<String>();
                a.add("HandBall Game");
                a.add("Chavannes-Epenex");
                a.add("02:00 PM");
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Tuesday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(1)).concat(" , "  ).concat(String.valueOf(years.get(1))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;


                List c=new ArrayList<List<String>>();
                c.add(a);
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);

                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle16Button = view.findViewById(R.id.Wednesday_button);

        rectangle16Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
                List a =new ArrayList<String>();
                a.add("Football Game");
                a.add("Unil-Sport");
                a.add("05:00 PM");
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Wednesday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(2)).concat(" , "  ).concat(String.valueOf(years.get(2))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;

                List c=new ArrayList<List<String>>();
                c.add(a);
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle11Button = view.findViewById(R.id.Thursday_button);

        rectangle11Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Thursday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(3)).concat(" , "  ).concat(String.valueOf(years.get(3))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;


                List c=new ArrayList<List<String>>();
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle18Button = view.findViewById(R.id.Friday_button);

        rectangle18Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
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
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Friday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(4)).concat(" , "  ).concat(String.valueOf(years.get(4))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;

                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle3Button = view.findViewById(R.id.Saturday_button);

        rectangle3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
                ListView listView = (ListView) view.findViewById(R.id.list_view);

                List a =new ArrayList<String>();
                a.add("Football Game");
                a.add("Unil-Sport");
                a.add("07:00 PM");
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Saturday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(5)).concat(" , "  ).concat(String.valueOf(years.get(5))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;

                List c=new ArrayList<List<String>>();
                c.add(a);
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);

                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
        Button rectangle17Button = view.findViewById(R.id.Sunday_button);

        rectangle17Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 TextView SundayText=(TextView)view.findViewById(R.id.Sunday_date);
                ListView listView = (ListView) view.findViewById(R.id.list_view);
                TextView dateText=view.findViewById(R.id.date);
                TextView date_sunday =(TextView)view.findViewById(R.id.Sunday_date);
                dateText.setText(String.valueOf(date_sunday.getText().toString().concat(" ").concat(convertMonthIndexToNameMonth(months.get(6)).concat(" , "  ).concat(String.valueOf(years.get(6))))));
                if(months.get(6)!=3  ||  years.get(6)!=2023 || Integer.parseInt(SundayText.getText().toString())!=26) return ;



                List a =new ArrayList<String>();
                a.add("Tennis Match");
                a.add("Unil-Sport");
                a.add("10:00 PM");



                List c=new ArrayList<List<String>>();
                c.add(a);
                ListViewAdapter adapter = new ListViewAdapter(getContext(), c);

                // Set the adapter to the ListView
                listView.setAdapter(adapter);
            }
        });
    }
}
