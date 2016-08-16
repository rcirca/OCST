package com.statstracker.something.ocst.UnofficialOverwatchSDK.API;

import com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects.ProfileData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by FailQuality on 8/15/2016.
 */
public interface UnofficialOverwatchAPI {
    String ENDPOINT = "https://api.lootbox.eu";

    @GET("/{platform}/{region}/{tag}/profile")
    Call<ProfileData> loadPlayerData(@Path("platform") String platform,
                                     @Path("region") String region,
                                     @Path("tag") String tag);


}
