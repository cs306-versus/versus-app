package com.github.versus.utils.auth;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Provides the mail address and password of users stored in the emulator database
 */
public class EmulatorUserProvider {

    private static final Random rnd = new Random();
    private static final AtomicInteger freeMailsCounter = new AtomicInteger(1);

    /**
     * ???
     * @return
     */
    public static String validMail(){
        // TODO HR : All all other missing user's to the database
        //return String.format("auth.user+%d@test.versus.ch", rnd.nextInt(9) + 1);
        return String.format("auth.user+%d@test.versus.ch", 1);
    }

    /**
     * ???
     * @return
     */
    public static String validPassword(){
        return "123456789";
    }

    /**
     * ???
     * @return
     */
    public static String nonValidMail(){
        return String.format("auth.user-%d@test.versus.ch", rnd.nextInt(9) + 1);
    }

    /**
     * ???
     * @return
     */
    public static String nonValidPassword(){
        return "ABCDEFGH";
    }

    /**
     * ???
     * @return
     */
    public static String freeMail(){
        return String.format("auth.user#%d@test.versus.ch", freeMailsCounter.getAndIncrement());
    }


}
