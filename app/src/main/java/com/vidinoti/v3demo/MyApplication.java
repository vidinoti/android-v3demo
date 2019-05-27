package com.vidinoti.v3demo;

import android.app.Application;

import com.vidinoti.library3d.Vidinoti3D;

/**
 * Custom {@link Application} class that initializes the Vidinoti 3D SDK when the application
 * starts.
 */
public class MyApplication extends Application {

    // Use your license key that you can retrieve from V-Director. Here we use a demo license key.
    private static final String VIDINOTI_LICENSE_KEY = "qv8db1pcnzc444ysnqtj";

    @Override
    public void onCreate() {
        super.onCreate();
        Vidinoti3D.init(this, VIDINOTI_LICENSE_KEY);
    }
}
