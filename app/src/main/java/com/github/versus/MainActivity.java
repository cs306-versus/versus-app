package com.github.versus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
 private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tl= findViewById(R.id.ToolBar);
        setSupportActionBar(tl);
        drawer=findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle  tgl= new ActionBarDrawerToggle(this,drawer,tl,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(tgl);
        tgl.syncState();
        NavigationView naviguationview= findViewById(R.id.nav_view);
        naviguationview.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ScheduleFragment()).commit();
   naviguationview.setCheckedItem(R.id.nav_schedule);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.current_place_menu, menu);
        return true;
    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LocationFragment()).commit();

                /*Intent intent = new Intent(this, MapsActivityCurrentPlace.class);
                startActivity(intent);*/
                break;

            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ScheduleFragment()).commit();
                break;

            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}