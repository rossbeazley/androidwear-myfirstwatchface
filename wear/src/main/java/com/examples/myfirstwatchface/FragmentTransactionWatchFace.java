package com.examples.myfirstwatchface;

import android.app.Fragment;
import android.app.FragmentManager;

class FragmentTransactionWatchFace implements WatchFace {
    private final FragmentManager fragmentManager;

    public FragmentTransactionWatchFace(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private void showFragment(Fragment fragment) {
        this.fragmentManager
                .beginTransaction()
                .replace(0, fragment)
                .commit();
    }

    @Override
    public void screenDim() {
        showFragment(new Fragment());
    }

    @Override
    public void screenAwake() {
        showFragment(new Fragment());
    }

    @Override
    public void removed(){}

    @Override
    public void screenOff(){
        showFragment(new Fragment());
    }
}
