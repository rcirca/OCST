package com.statstracker.something.ocst.Activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;
import com.statstracker.something.ocst.Player;
import com.statstracker.something.ocst.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by justin on 10/5/2016.
 */

public class DisplayProfileActivity extends BaseOCSTActivity {
    private Player mPlayer;

    @BindView(R.id.displayUsername) TextView userName;
    @BindView(R.id.displayRank) TextView rank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_profile_layout);
        ButterKnife.bind(this);

        mPlayer = getIntent().getParcelableExtra("profile");
        userName.setText(mPlayer.getUsername());
        rank.setText(mPlayer.getRank());
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
}
