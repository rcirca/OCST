package com.statstracker.something.ocst.Events;

import com.statstracker.something.ocst.Player;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class LoadProfileResponseEvent {
    private boolean mSuccess;
    private Player mPlayer;

    public LoadProfileResponseEvent(boolean pSuccess) {
        mSuccess = pSuccess;
    }

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public Player getmPlayer() {
        return mPlayer;
    }

    public void setmPlayer(Player mPlayer) {
        this.mPlayer = mPlayer;
    }
}
