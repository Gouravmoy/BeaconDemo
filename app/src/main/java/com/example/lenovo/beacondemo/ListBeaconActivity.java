package com.example.lenovo.beacondemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.example.lenovo.beacondemo.adapter.BeaconListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListBeaconActivity extends AppCompatActivity {


    private static final Region ALL_ESTIMOTE_BEACONS_REGION = new Region("rid", null, null, null);

    private static final String TAG = ListBeaconActivity.class.getSimpleName();

    private BeaconManager beaconManager;
    private BeaconListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "Inside onCreate ");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_beacon);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });




        // Configure device list.
        adapter = new BeaconListAdapter(this);
        ListView list = (ListView) findViewById(R.id.device_list);
        list.setAdapter(adapter);





        //adapter.replaceWith(listOfBeacons);
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {

            @Override
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {

                Log.i(TAG, "Inside onBeaconDiscover ");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        toolbar.setSubtitle("Found beacon " + beacons.size());
                        adapter.replaceWith(beacons);
                    }
                });
            }
        });


    }

    @Override
    protected void onResume() {

        super.onResume();

        Log.i(TAG, "onREsume ");

        if (SystemRequirementsChecker.checkWithDefaultDialogs(this)) {
            Log.i(TAG, "start scanning ");
            startScanning();
        }


    }

    @Override
    protected void onDestroy() {

        Log.i(TAG, "Inside onDestory");
        beaconManager.disconnect();

        super.onDestroy();
    }

    @Override
    protected void onStop() {

        Log.i(TAG, "Inside onStop");
        super.onStop();
    }

    private void startScanning() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Log.i(TAG, "Inside srart running");
        toolbar.setSubtitle("Scanning...");
        adapter.replaceWith(Collections.<Beacon>emptyList());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(ALL_ESTIMOTE_BEACONS_REGION);
            }
        });
    }


}
