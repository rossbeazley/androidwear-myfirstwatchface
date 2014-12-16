package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;

import uk.co.rossbeazley.wear.R;

public class FragmentTransactionWatchFaceUINavigation implements WatchFaceUINavigation {
    private final FragmentManager fragmentManager;

    public FragmentTransactionWatchFaceUINavigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    private void showFragment(Fragment fragment) {
        this.fragmentManager
                .beginTransaction()
                .replace(R.id.watch_container, fragment)
                .commit();
    }

    @Override
    public void screenDim() {
        showFragment(new WatchFaceViewDimmedFragment());
    }

    @Override
    public void screenAwake() {
        showFragment(new WatchFaceViewActiveFragment());
    }

    @Override
    public void removed(){}

    @Override
    public void screenOff(){
        showFragment(new WatchFaceViewDimmedFragment());
    }

    public void defaultState() {
        this.fragmentManager
                .beginTransaction()
                .add(R.id.watch_container, new WatchFaceViewActiveFragment())
                .commit();
    }
}
