package com.statstracker.something.ocst.UnofficialOverwatchSDK.ResponseObjects;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class PlayerData {
    String username;
    int level;
    Games games;
    Playtime playtime;
    String avatar;
    Rank competitive;
    String levelFrame;
    String star;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Games getGames() {
        return games;
    }

    public void setGames(Games games) {
        this.games = games;
    }

    public Playtime getPlaytime() {
        return playtime;
    }

    public void setPlaytime(Playtime playtime) {
        this.playtime = playtime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Rank getCompetitive() {
        return competitive;
    }

    public void setCompetitive(Rank competitive) {
        this.competitive = competitive;
    }

    public String getLevelFrame() {
        return levelFrame;
    }

    public void setLevelFrame(String levelFrame) {
        this.levelFrame = levelFrame;
    }

    public String getStar() {
        return star;
    }

    public void setStar(String star) {
        this.star = star;
    }
}
