package com.statstracker.something.ocst.Activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.statstracker.something.ocst.BusProvider;
import com.statstracker.something.ocst.R;
import com.statstracker.something.ocst.Events.LoadProfileCallEvent;
import com.statstracker.something.ocst.Events.LoadProfileResponseEvent;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private Bus mBus;
    private Realm mRealm;
    private ArrayAdapter<String> mNavAdapter;

    @BindView(R.id.battle_tag_field) EditText mBattleTagField;
    @BindView(R.id.region_spinner) Spinner mRegionSpinner;
    @BindView(R.id.platform_spinner) Spinner mPlatformSpinner;
//    @BindView(R.id.navList) ListView mNavList;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());

        ButterKnife.bind(this);
        initializeMemeberVariables();
    }

    private void initializeMemeberVariables() {
        mRealm = Realm.getDefaultInstance();
        mBus = BusProvider.getInstance();

        ArrayAdapter<CharSequence> platformAdapter = ArrayAdapter.createFromResource(this,
                R.array.platform_array, android.R.layout.simple_spinner_item);
        platformAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mPlatformSpinner.setAdapter(platformAdapter);

        ArrayAdapter<CharSequence> regionAdapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        regionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mRegionSpinner.setAdapter(regionAdapter);

        String[] menuArray = {"Profiles"};
        mNavAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, menuArray);
//        mNavList.setAdapter(mNavAdapter);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Searching");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));
//
//        return true;
//    }

    @Subscribe
    public void onLoadProfileSuccess(LoadProfileResponseEvent pEvent){
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

//    @OnItemClick(R.id.navList)
    public void onItemClick(ListView view, int position) {
        String item = (String)view.getAdapter().getItem(position);
        Log.v(item, item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(this, "Search Button Successful", Toast.LENGTH_LONG).show();
        }
    }
}
