package com.waaztech.mycar;

import android.app.Application;

import com.waaztech.mycar.util.Stash;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stash.init(this);
    }
}
