package com.statstracker.something.ocst;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.API.UnofficialOverwatchAPI;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.Service.ProfileService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wonkyulee on 8/16/16.
 */
public class OCSTApplication extends Application {
    private ProfileService mService;
    private Bus mBus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();

        mService = new ProfileService(createApi(), mBus);
        mBus.register(mService);
    }

    private UnofficialOverwatchAPI createApi() {
        Gson gson = new GsonBuilder().create();

        return new Retrofit.Builder()
                .baseUrl(UnofficialOverwatchAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(UnofficialOverwatchAPI.class);
    }
}
