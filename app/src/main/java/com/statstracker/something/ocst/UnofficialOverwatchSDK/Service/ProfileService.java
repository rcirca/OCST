package com.statstracker.something.ocst.UnofficialOverwatchSDK.Service;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;
import com.statstracker.something.ocst.Player;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.API.UnofficialOverwatchAPI;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects.PlayerData;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects.ProfileData;
import com.statstracker.something.ocst.UnofficialOverwatchSDK.SDKUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class ProfileService {
    private UnofficialOverwatchAPI mApi;
    private Bus mBus;
    private Realm mRealm;
    private DateFormat mDateFormat = new SimpleDateFormat("yyMMdd");
    private Date mDate = new Date();

    public ProfileService(UnofficialOverwatchAPI pApi, Bus pBus){
        mApi = pApi;
        mBus = pBus;
    }

    @Subscribe
    public void onLoadProfile(LoadProfileCallEvent pEvent){
        mRealm = Realm.getDefaultInstance();
        Call<ProfileData> call = mApi.loadPlayerData(pEvent.getmPlatform(), pEvent.getmRegion(), pEvent.getmUsername());

        RealmResults<Player> results = mRealm.where(Player.class)
                .equalTo("battleTag", pEvent.getmUsername())
                .findAll();

        boolean queryAgain = checkQueryAgain(results);

        if (queryAgain) {
            SDKUtil.setmBattleTag(pEvent.getmUsername());
            SDKUtil.setmRegion(pEvent.getmRegion());
            SDKUtil.setmPlatform(pEvent.getmPlatform());

            call.enqueue(new Callback<ProfileData>() {
                @Override
                public void onResponse(Call<ProfileData> call, Response<ProfileData> response) {
                    PlayerData playerData = response.body().getData();
                    LoadProfileResponseEvent responseEvent = new LoadProfileResponseEvent(true);

                    mRealm.beginTransaction();

                    Player player = mRealm.createObject(Player.class);

                    player.setBattleTag(SDKUtil.getmBattleTag());
                    player.setRegion(SDKUtil.getmRegion());
                    player.setPlatform(SDKUtil.getmRegion());
                    SDKUtil.clear();

                    player.setUsername(playerData.getUsername());
                    player.setRank(playerData.getCompetitive().getRank());
                    player.setLost(Integer.toString(playerData.getGames().getCompetitive().getLost()));
                    player.setWins(playerData.getGames().getCompetitive().getWins());
                    player.setPlayed(playerData.getGames().getCompetitive().getPlayed());
                    player.setPlaytime(playerData.getPlaytime().getCompetitive());
                    player.setRankImg(playerData.getCompetitive().getRank_img());

                    player.setLastQueried(mDateFormat.format(mDate));

                    mRealm.commitTransaction();

                    responseEvent.setmPlayer(player);
                    mBus.post(responseEvent);
                    mRealm.close();
                }

                @Override
                public void onFailure(Call<ProfileData> call, Throwable t) {
                    SDKUtil.clear();
                    mBus.post(new LoadProfileResponseEvent(false));
                    mRealm.close();
                }
            });
        }
        else {
            LoadProfileResponseEvent responseEvent = new LoadProfileResponseEvent(true);
            responseEvent.setmPlayer(results.first());
            mBus.post(responseEvent);
            mRealm.close();
        }
    }

    private boolean checkQueryAgain(RealmResults<Player> results) {
        int resultSize = results.size();
        Player savedPlayer = null;
        boolean queryAgain = true;

        if (resultSize > 0) {
            savedPlayer = results.first();
            int currentDate = Integer.parseInt(mDateFormat.format(mDate));
            int lastQueried = Integer.parseInt(savedPlayer.getLastQueried());
            queryAgain = currentDate > lastQueried;
            if (queryAgain) {
                mRealm.beginTransaction();
                results.deleteAllFromRealm();
                mRealm.commitTransaction();
            }
        }
        return queryAgain;
    }
}
