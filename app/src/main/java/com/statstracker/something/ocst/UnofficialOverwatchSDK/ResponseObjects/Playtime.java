package com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class Playtime {
    public String getQuick() {
        return quick;
    }

    public void setQuick(String quick) {
        this.quick = quick;
    }

    public String getCompetitive() {
        return competitive;
    }

    public void setCompetitive(String competitive) {
        this.competitive = competitive;
    }

    String quick;
    String competitive;
}
