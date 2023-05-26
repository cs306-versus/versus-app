package com.github.versus;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.core.view.GravityCompat;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.DrawerActions;
import androidx.test.espresso.contrib.DrawerMatchers;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.github.versus.utils.FirebaseEmulator;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class TopGamesTest {

    static {
        FirebaseFirestore db = FirebaseEmulator.FIREBASE_FIRESTORE;
    }

    //General rule that sets up the Activity Scenario Rule
    @Rule
    public ActivityScenarioRule<MainActivity> scenario = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * This method tests the first position of the trending sports by clicking on the appropriate picture and
     * showing the filtered posts of the sport
     */
    @Test
    public void testOnFirstPosition() {

    }

}