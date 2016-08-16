package com.statstracker.something.ocst.UnofficialOverwatchSDK.Service;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.API.UnofficialOverwatchAPI;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects.ProfileData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class ProfileService {
    private UnofficialOverwatchAPI mApi;
    private Bus mBus;

    public ProfileService(UnofficialOverwatchAPI pApi, Bus pBus){
        mApi = pApi;
        mBus = pBus;
    }

    @Subscribe
    public void onLoadProfile(LoadProfileEvent pEvent){
        Call<ProfileData> call = mApi.loadPlayerData(pEvent.getmPlatform(), pEvent.getmRegion(), pEvent.getmUsername());
        call.enqueue(new Callback<ProfileData>() {
            @Override
            public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                mBus.post(new ProfileLoadedEvent());
            }

            @Override
            public void onFailure(Call<ProfileData> call, Throwable t) {
                mBus.post(new ProfileFailedEvent());
            }
        });
    }


}
