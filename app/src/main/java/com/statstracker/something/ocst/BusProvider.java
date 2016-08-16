package com.statstracker.something.ocst;

import com.squareup.otto.Bus;

/**
 * Created by FailQuality on 8/15/2016.
 */
public class BusProvider {

    private static Bus mBus;
    public static Bus getInstance(){
        if(mBus == null){
            mBus = new Bus();
        }

        return mBus;
    }
}
