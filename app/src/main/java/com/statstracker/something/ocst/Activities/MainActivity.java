package com.statstracker.something.ocst.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.BusProvider;
import com.statstracker.something.ocst.Player;
import com.statstracker.something.ocst.R;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Bus mBus;
    private Realm mRealm;

    @BindView(R.id.battle_tag_field) EditText mBattleTagField;
    @BindView(R.id.region_spinner) Spinner mRegionSpinner;
    @BindView(R.id.platform_spinner) Spinner mPlatformSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        mBus = BusProvider.getInstance();
        mBus.register(this);

        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> platformAdapter = ArrayAdapter.createFromResource(this,
                R.array.platform_array, android.R.layout.simple_spinner_item);
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlatformSpinner.setAdapter(platformAdapter);

        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegionSpinner.setAdapter(regionAdapter);
    }

    @Subscribe
    public void onLoadProfileSuccess(LoadProfileResponseEvent pEvent){
        if (pEvent.ismSuccess()) {
            Player player = pEvent.getmPlayer();
            String username = player.getUsername();
            String rank = player.getRank();
            String wins = player.getWins();
            String lost = player.getLost();
            String played = player.getPlayed();
            String playTime = player.getPlaytime();
            Toast.makeText(this, username + " " + rank + " " + wins + " " + lost + " " + played + " " + playTime + " ", Toast.LENGTH_LONG).show();
        }
        else
            Toast.makeText(this, "Failed", Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.search_button)
    public void searchProfile() {
        String battleTag = mBattleTagField.getText().toString().replace('#', '-');
        String platform = mPlatformSpinner.getSelectedItem().toString();
        String region = mRegionSpinner.getSelectedItem().toString();

        mBus.post(new LoadProfileCallEvent(platform, region, battleTag));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }
}
