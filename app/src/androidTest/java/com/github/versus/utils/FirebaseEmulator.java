package com.github.versus.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public final class FirebaseEmulator {

    private FirebaseEmulator(){}

    /**
     * {@link FirebaseAuth} emulator instance
     */
    public static final FirebaseAuth FIREBASE_AUTH = FirebaseAuth.getInstance();

    public static final FirebaseFirestore FIREBASE_FIRESTORE = FirebaseFirestore.getInstance();

    /*
     * Configure the emulators
     */
    static {
        FIREBASE_AUTH.useEmulator("10.0.2.2", 9099);
        FIREBASE_FIRESTORE.useEmulator("10.0.2.2", 8080);
    }

}
