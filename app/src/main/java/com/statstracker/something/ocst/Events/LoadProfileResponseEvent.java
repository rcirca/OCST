package com.statstracker.something.ocst.Events;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class LoadProfileResponseEvent {
    private boolean mSuccess;

    public LoadProfileResponseEvent(boolean pSuccess) {
        mSuccess = pSuccess;
    }

    public boolean ismSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
