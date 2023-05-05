package com.github.versus.utils;

import com.google.firebase.auth.FirebaseAuth;

public final class FirebaseEmulator {

    private FirebaseEmulator(){}

    /**
     * {@link FirebaseAuth} emulator instance
     */
    public static final FirebaseAuth FIREBASE_AUTH = FirebaseAuth.getInstance();

    /*
     * Configure the emulators
     */
    static {
        FIREBASE_AUTH.useEmulator("10.0.2.2", 9099);
    }

}
