package com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects;

/**
 * Created by FailQuality on 8/15/2016.
 */


public class ProfileData {
    PlayerData data;
    String statusCode;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    String error;

    public PlayerData getData() {
        return data;
    }

    public void setData(PlayerData data) {
        this.data = data;
    }
}