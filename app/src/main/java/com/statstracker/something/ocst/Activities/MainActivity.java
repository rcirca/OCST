package com.statstracker.something.ocst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.BusProvider;
import com.statstracker.something.ocst.R;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;

public class MainActivity extends AppCompatActivity {

    private Bus mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBus = BusProvider.getInstance();

        mBus.register(this);
        mBus.post(new LoadProfileCallEvent("pc", "us", "Circa-1414"));
    }

    @Subscribe
    public void onLoadProfileSuccess(LoadProfileResponseEvent pEvent){
        if (pEvent.ismSuccess())
            Toast.makeText(this, "Succeeded", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
    }
}
