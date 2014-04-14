package com.mythicallogistics.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.mythicallogistics.app.api.ApiFactory;

/**
 * Created by ole on 25/03/14.
 */
public class MythicalApp extends Application {

    public static final String PREF_API_HOST = "API_HOST";
    public static final String PREF_ACCOUNT_NUMBER = "ACCOUNT_NR";

    private static MythicalApp instance;

    public static MythicalApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        Intent intent = new Intent();
        intent.setAction("com.smartbear.android.ram");
        intent.putExtra("UserExperienceData", "Hello Mythical World!");
        getApplicationContext().sendBroadcast(intent);
    }

    public SharedPreferences getPrefs()
    {
       return PreferenceManager.getDefaultSharedPreferences( getApplicationContext());
    }
}