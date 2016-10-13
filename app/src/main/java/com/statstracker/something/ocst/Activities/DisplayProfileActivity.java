package com.statstracker.something.ocst.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.statstracker.something.ocst.BusProvider;
import com.statstracker.something.ocst.Player;
import com.statstracker.something.ocst.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by justin on 10/5/2016.
 */

public class DisplayProfileActivity extends AppCompatActivity {
    private Bus mBus;
    private Realm mRealm;
    private Player mPlayer;

    @BindView(R.id.displayUsername) TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_profile_layout);

        mPlayer = (Player)getIntent().getParcelableExtra("profile");
        mRealm = Realm.getDefaultInstance();

        mBus = BusProvider.getInstance();
        mBus.register(this);

        ButterKnife.bind(this);
        userName.setText(mPlayer.getUsername());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
