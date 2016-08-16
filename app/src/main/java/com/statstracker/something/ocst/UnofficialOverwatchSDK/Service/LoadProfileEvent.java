package com.statstracker.something.ocst.UnofficialOverwatchSDK.Service;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class LoadProfileEvent{

    private String mUsername;
    private String mPlatform;
    private String mRegion;

    public LoadProfileEvent(String pPlatform, String pRegion, String pUsername){
        mPlatform = pPlatform;
        mRegion = pRegion;
        mUsername = pUsername;
    }
    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPlatform() {
        return mPlatform;
    }

    public void setmPlatform(String mPlatform) {
        this.mPlatform = mPlatform;
    }

    public String getmRegion() {
        return mRegion;
    }

    public void setmRegion(String mRegion) {
        this.mRegion = mRegion;
    }
}
