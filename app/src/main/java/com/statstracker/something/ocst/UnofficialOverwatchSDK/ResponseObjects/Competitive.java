package com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class Competitive {
    String wins;
    int lost;
    String played;

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public int getLost() {
        return lost;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }
}
