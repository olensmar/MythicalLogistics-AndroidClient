package com.mythicallogistics.app.api;

import android.content.SharedPreferences;

import com.mythicallogistics.app.MythicalApp;

import java.util.HashMap;
import java.util.Map;

import retrofit.RestAdapter;

public class ApiFactory {

    public static final String DEFAULT_ENDPOINT = "http://mythical.cloudhub.io";
    private static ApiFactory instance;
    private Map<String,MythicalLogisticsIncShippingAPI> apiMap = new HashMap<String, MythicalLogisticsIncShippingAPI>();

    public static ApiFactory get()
    {
        if( instance == null )
            instance = new ApiFactory();

        return instance;
    }

    public MythicalLogisticsIncShippingAPI getApi()
    {
        SharedPreferences prefs = MythicalApp.getInstance().getPrefs();
        String host = prefs.getString( MythicalApp.PREF_API_HOST, DEFAULT_ENDPOINT );
        
        return getApi( host );
    }

    public MythicalLogisticsIncShippingAPI getApi( String url )
    {
        if( !apiMap.containsKey( url ))
        {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(url)
                    .build();

            apiMap.put( url, restAdapter.create(MythicalLogisticsIncShippingAPI.class));
        }

        return apiMap.get( url );
    }
}
