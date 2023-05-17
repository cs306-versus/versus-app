package com.github.versus;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.github.versus.user.UserInfoFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
 private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tl = findViewById(R.id.ToolBar);
        setSupportActionBar(tl);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle tgl = new ActionBarDrawerToggle(this, drawer, tl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(tgl);
        tgl.syncState();
        NavigationView naviguationview = findViewById(R.id.nav_view);
        naviguationview.setNavigationItemSelectedListener(this);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ScheduleFragment()).commit();
        naviguationview.setCheckedItem(R.id.nav_schedule);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new LocationFragment()).commit();
                break;

            case R.id.nav_schedule:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ScheduleFragment()).commit();
                break;

            case R.id.nav_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SearchFragment()).commit();
                break;
            case R.id.nav_user_profil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new UserInfoFragment()).commit();
                break;
            case R.id.friend_search:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SearchFriendsFragment()).commit();
                break;
            case R.id.nav_chats:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new ChatsFragment()).commit();
                break;
            case R.id.nav_trending_sports:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new TrendingGamesFragment()).commit();
                break;
            case R.id.nav_sport_equipments:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SportsEquipmentsFragment()).commit();
                break;

        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}