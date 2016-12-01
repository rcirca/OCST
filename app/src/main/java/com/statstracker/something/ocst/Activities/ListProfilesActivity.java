package com.statstracker.something.ocst.Activities;

import android.os.Bundle;

import com.statstracker.something.ocst.R;

import butterknife.ButterKnife;

/**
 * Created by justin on 11/16/2016.
 */

public class ListProfilesActivity extends BaseOCSTActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_profiles);
        ButterKnife.bind(this);
    }
}
