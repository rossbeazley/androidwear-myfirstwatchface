package com.examples.myfirstwatchface;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyWatchFace extends WatchFaceActivity {

    private TextView mDate;

    private final static IntentFilter INTENT_FILTER;
    static {
        INTENT_FILTER = new IntentFilter();
        INTENT_FILTER.addAction(Intent.ACTION_TIME_TICK);
        INTENT_FILTER.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        INTENT_FILTER.addAction(Intent.ACTION_TIME_CHANGED);
    }

    private BroadcastReceiver mTimeInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent intent) {
            Calendar instance = Calendar.getInstance();
            Date time = instance.getTime();

            String dateString = new SimpleDateFormat("dd").format(time);
            dateString += getDayOfMonthSuffix(instance.get(Calendar.DAY_OF_MONTH));
            dateString += new SimpleDateFormat("  MMMM").format(time);
            mDate.setText(dateString);

        }
    };

    String getDayOfMonthSuffix(final int n) {
        if (n >= 4 && n <= 20) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent intent) {
            //mBattery.setText(String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) + "%"));
        }
    };

    @Override
    public void onScreenDim() {

    }

    @Override
    public void onScreenAwake() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watch_face);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mDate = (TextView) stub.findViewById(R.id.date);

                mTimeInfoReceiver.onReceive(MyWatchFace.this, null);
                registerReceiver(mTimeInfoReceiver, INTENT_FILTER);
                registerReceiver(mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTimeInfoReceiver);
        unregisterReceiver(mBatInfoReceiver);
    }
}
