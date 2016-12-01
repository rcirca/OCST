package com.statstracker.something.ocst.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Bus;
import com.statstracker.something.ocst.BusProvider;

import io.realm.Realm;

/**
 * Created by jlee on 11/30/16.
 */

public abstract class BaseOCSTActivity extends AppCompatActivity {
    protected Bus mBus;
    protected Realm mRealm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBus = BusProvider.getInstance();
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBus.register(this);
    }
}
