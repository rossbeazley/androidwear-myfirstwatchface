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

    private TextView mTimeHours, mTimeMins, mDate;

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
            Date time = Calendar.getInstance().getTime();
            mTimeHours.setText(new SimpleDateFormat("KK").format(time));
            mTimeMins.setText(new SimpleDateFormat("mm").format(time));
            mDate.setText(new SimpleDateFormat("dd MMMM").format(time));
        }
    };

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context arg0, Intent intent) {
            //mBattery.setText(String.valueOf(intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) + "%"));
        }
    };

    @Override
    public void onScreenDim() {
        //mTimeHours.setTextColor(Color.WHITE);
        //mTimeMins.setTextColor(Color.WHITE);
        //mBattery.setTextColor(Color.WHITE);
    }

    @Override
    public void onScreenAwake() {
        //mTimeHours.setTextColor(Color.RED);
        //mTimeMins.setTextColor(Color.GRAY);
        //mBattery.setTextColor(Color.RED);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_watch_face);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTimeHours = (TextView) stub.findViewById(R.id.watch_time);
                mTimeMins = (TextView) stub.findViewById(R.id.watch_time_mins);

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
