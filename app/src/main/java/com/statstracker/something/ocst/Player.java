package com.statstracker.something.ocst;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by wonkyulee on 8/24/16.
 */
public class Player extends RealmObject implements Parcelable {
    // only competitive data
    String username;

    String playtime;

    String wins;
    String lost;
    String played;

    String rank;
    String rankImg;

    // query info
    String battleTag;
    String region;
    String platform;

    public String getBattleTag() {
        return battleTag;
    }

    public void setBattleTag(String battleTag) {
        this.battleTag = battleTag;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Player() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlaytime() {
        return playtime;
    }

    public void setPlaytime(String playtime) {
        this.playtime = playtime;
    }

    public String getWins() {
        return wins;
    }

    public void setWins(String wins) {
        this.wins = wins;
    }

    public String getLost() {
        return lost;
    }

    public void setLost(String lost) {
        this.lost = lost;
    }

    public String getPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getRankImg() {
        return rankImg;
    }

    public void setRankImg(String rankImg) {
        this.rankImg = rankImg;
    }

    protected Player(Parcel in) {
        username = in.readString();
        playtime = in.readString();
        wins = in.readString();
        lost = in.readString();
        played = in.readString();
        rank = in.readString();
        rankImg = in.readString();
        battleTag = in.readString();
        region = in.readString();
        platform = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(playtime);
        dest.writeString(wins);
        dest.writeString(lost);
        dest.writeString(played);
        dest.writeString(rank);
        dest.writeString(rankImg);
        dest.writeString(battleTag);
        dest.writeString(region);
        dest.writeString(platform);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Player> CREATOR = new Parcelable.Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
