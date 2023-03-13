package com.github.versus.utils;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import android.content.ComponentName;

/**
 * ???
 */
public final class VersusComponentName {

    private VersusComponentName(){}

    /**
     * ???
     * @param clz
     * @return
     */
    public static ComponentName of(Class<?> clz){
        return ComponentName.createRelative(getApplicationContext(), clz.getCanonicalName());
    }

}
