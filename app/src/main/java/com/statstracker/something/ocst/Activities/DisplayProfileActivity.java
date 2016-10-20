package com.statstracker.something.ocst.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.BusProvider;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;
import com.statstracker.something.ocst.Player;
import com.statstracker.something.ocst.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

/**
 * Created by justin on 10/5/2016.
 */

public class DisplayProfileActivity extends AppCompatActivity {
    private Bus mBus;
    private Realm mRealm;
    private Player mPlayer;

    @BindView(R.id.displayUsername) TextView userName;
    @BindView(R.id.displayRank) TextView rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_profile_layout);

        mPlayer = (Player)getIntent().getParcelableExtra("profile");
        mRealm = Realm.getDefaultInstance();

        mBus = BusProvider.getInstance();

        ButterKnife.bind(this);
        userName.setText(mPlayer.getUsername());
        rank.setText(mPlayer.getRank());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @OnClick(R.id.refresh)
    public void refreshProfile() {
        String battleTag = mPlayer.getBattleTag();
        String platform = mPlayer.getPlatform();
        String region = mPlayer.getRegion();

        mRealm.beginTransaction();
        mRealm.where(Player.class).contains("battleTag", mPlayer.getBattleTag()).findFirst().deleteFromRealm();
        mRealm.commitTransaction();

        mBus.post(new LoadProfileCallEvent(platform, region, battleTag));
    }

    @Subscribe
    public void onLoadProfileSuccess(LoadProfileResponseEvent pEvent){
        if (pEvent.ismSuccess()) {
            mPlayer = pEvent.getmPlayer();
            userName.setText(mPlayer.getUsername());
            rank.setText(mPlayer.getRank());
            Toast.makeText(this, "Refresh successful", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Failed to refresh", Toast.LENGTH_LONG).show();
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
