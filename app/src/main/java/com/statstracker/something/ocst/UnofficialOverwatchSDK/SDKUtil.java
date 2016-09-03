package com.statstracker.something.ocst.UnofficialOverwatchSDK;

/**
 * Created by wonkyulee on 8/30/16.
 */
public abstract class SDKUtil {
    private static String mBattleTag;
    private static String mRegion;
    private static String mPlatform;

    public static String getmBattleTag() {
        return mBattleTag;
    }

    public static void setmBattleTag(String mBattleTag) {
        SDKUtil.mBattleTag = mBattleTag;
    }

    public static String getmRegion() {
        return mRegion;
    }

    public static void setmRegion(String mRegion) {
        SDKUtil.mRegion = mRegion;
    }

    public static String getmPlatform() {
        return mPlatform;
    }

    public static void setmPlatform(String mPlatform) {
        SDKUtil.mPlatform = mPlatform;
    }

    public static void clear() {
        SDKUtil.mBattleTag = "";
        SDKUtil.mPlatform = "";
        SDKUtil.mRegion = "";
    }
}
