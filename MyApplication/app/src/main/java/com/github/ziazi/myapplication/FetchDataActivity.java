package com.github.ziazi.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FetchDataActivity extends AppCompatActivity {

    private Callback<BoredActivity> callback_handler;
    private ActivityDatabase db;

    private final ExecutorService db_executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_data);
        callback_handler = new Callback<BoredActivity>() {
            @Override
            public void onResponse(Call<BoredActivity> call, Response<BoredActivity> response) {
                BoredActivity fetched= response.body();
                if(fetched!=null) {
                    ((TextView) findViewById(R.id.activityText)).setText(textFormat(fetched));
                    ((TextView) findViewById(R.id.mode)).setText("Online Mode");
                    Linkify.addLinks((TextView) findViewById(R.id.activityText), Linkify.WEB_URLS);
                    insertActivity(fetched);
                }

            }

            @Override
            public void onFailure(Call<BoredActivity> call, Throwable t) {
                getCached();
            }

        };

        this.db= Room.databaseBuilder(getApplicationContext(),
                ActivityDatabase.class, "cache").build();
    }
    public void getActivity(View view){
        if(!isNetworkAvailable()) {
            getCached();
            return;
        }
        RetrofitClient.getClient().getApi().getActivity().enqueue(callback_handler);
    }
    private void insertActivity(BoredActivity activity){
        db_executor.submit(()-> db.activityDao().insertAll(new CachedActivity().match(activity)));
    }
    private static String textFormat(BoredActivity fetched){
        StringBuilder text= new StringBuilder()
                .append("    activity: %s\n")
                .append("    accessibility: %s\n ")
                .append("    type: %s\n")
                .append("    participants: %s\n")
                .append("    price: %s\n")
                .append("    key: %s\n")
                .append("    link: %s");

        return  String.format(text.toString(), fetched.activity,fetched.accessibility,fetched.type,fetched.participants,fetched.price,fetched.key,fetched.link);
    }

    private void getCached(){
        String mode = "Offline Mode: Cannot connect to BoredApi";
        ((TextView)findViewById(R.id.mode)).setText(mode);

        db_executor.submit(()-> {
                    CachedActivity loaded = db.activityDao().randomSelect();
                    if(loaded!= null) {
                        ((TextView) findViewById(R.id.activityText)).setText(textFormat(loaded.revert()));
                    }
                }
        );
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }
}