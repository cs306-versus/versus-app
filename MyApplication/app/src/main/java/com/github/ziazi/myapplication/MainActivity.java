package com.github.ziazi.myapplication;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private  Callback<BoredActivity> callback_handler;
    private ActivityDatabase db;

    private final ExecutorService db_executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callback_handler = new Callback<BoredActivity>() {
            @Override
            public void onResponse(Call<BoredActivity> call, Response<BoredActivity> response) {
                BoredActivity fetched= response.body();
                if(fetched!=null) {
                    fetched.link = "https://youtu.be/xvFZjo5PgG0";
                    ((TextView) findViewById(R.id.textView)).setText(textFormat(fetched));
                    ((TextView) findViewById(R.id.linkView)).setText(fetched.link);
                    Linkify.addLinks((TextView) findViewById(R.id.linkView), Linkify.WEB_URLS);
                    insertActivity(fetched);
                }

            }

            @Override
            public void onFailure(Call<BoredActivity> call, Throwable t) {

               String error_message = "Offline Mode: Cannot connect to BoredApi";
                ((TextView)findViewById(R.id.linkView)).setText(error_message);

                db_executor.submit(()-> {
                           CachedActivity loaded = db.activityDao().randomSelect();
                           if(loaded!= null) {
                               ((TextView) findViewById(R.id.textView)).setText(textFormat(loaded.revert()));
                           }
                       }
                       );
            }

        };

        this.db= Room.databaseBuilder(getApplicationContext(),
                ActivityDatabase.class, "cache").build();

    }

    public void getActivity(View view){
        RetrofitClient.getClient().getApi().getActivity().enqueue(callback_handler);
    }
    private void insertActivity(BoredActivity activity){
                db_executor.submit(()-> db.activityDao().insertAll(new CachedActivity().match(activity)));
    }
    private static String textFormat(BoredActivity fetched){
       return  String.format("activity: %s \n accessibility: %s \n type: %s \n participants: %s \n price: %s \n key: %s \n ",
                fetched.activity,fetched.accessibility,fetched.type,fetched.participants,fetched.price,fetched.key);
    }
}