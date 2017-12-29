package com.danielstone.binarytools;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by danielstone on 29/12/2017.
 */

public class BinaryTools extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

    }
}

