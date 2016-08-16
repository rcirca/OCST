package com.statstracker.something.ocst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.BusProvider;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.API.UnofficialOverwatchAPI;
import com.statstracker.something.ocst.R;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects.ProfileData;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.Service.LoadProfileEvent;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.Service.ProfileFailedEvent;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.Service.ProfileLoadedEvent;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.Service.ProfileService;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Bus mBus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // retrofit call
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UnofficialOverwatchAPI.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        UnofficialOverwatchAPI api = retrofit.create(UnofficialOverwatchAPI.class);
        ProfileService service = new ProfileService(api, getBus());
        getBus().register(service);
        getBus().register(this);
        getBus().post(new LoadProfileEvent("pc", "us", "Circa-1414"));
    }

    @Subscribe
    public void onLoadProfileSuccess(ProfileLoadedEvent pEvent){
        Toast.makeText(this, "Succeeded", Toast.LENGTH_LONG).show();
    }

    @Subscribe
    public void onLoadProfileFail(ProfileFailedEvent pEvent){
        Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
    }

    private Bus getBus(){
        if(mBus == null)
            mBus = BusProvider.getInstance();

        return mBus;
    }

    private void setBus(Bus pBus){
        mBus = pBus;
    }
}
