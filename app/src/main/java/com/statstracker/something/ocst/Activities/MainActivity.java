package com.statstracker.something.ocst.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.R;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseOCSTActivity {

    @BindView(R.id.battle_tag_field) EditText mBattleTagField;
    @BindView(R.id.region_spinner) Spinner mRegionSpinner;
    @BindView(R.id.platform_spinner) Spinner mPlatformSpinner;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        ButterKnife.bind(this);
        initializeMemeberVariables();
    }

    private void initializeMemeberVariables() {
        ArrayAdapter<CharSequence> platformAdapter = ArrayAdapter.createFromResource(this,
                R.array.platform_array, android.R.layout.simple_spinner_item);
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlatformSpinner.setAdapter(platformAdapter);

        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegionSpinner.setAdapter(regionAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Searching");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
    }

    @Subscribe
    public void handleLoadProfileResponseEvent(LoadProfileResponseEvent pEvent){
        if (pEvent.ismSuccess()) {
            Intent intent = new Intent(this, DisplayProfileActivity.class);
            intent.putExtra("profile", pEvent.getmPlayer());
            mProgressDialog.dismiss();
            startActivity(intent);
        }
        else {
            mProgressDialog.dismiss();
            Toast.makeText(this, "Failed: " + pEvent.getErrorMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R.id.search_button)
    public void searchProfile() {
        mProgressDialog.show();
        String battleTag = mBattleTagField.getText().toString().replace('#', '-');
        String platform = mPlatformSpinner.getSelectedItem().toString();
        String region = mRegionSpinner.getSelectedItem().toString();

        mBus.post(new LoadProfileCallEvent(platform, region, battleTag));
    }

    @OnClick(R.id.test_list_button)
    public void listProfiles() {
        Intent intent = new Intent(this, ListProfilesActivity.class);
        startActivity(intent);
    }
}
