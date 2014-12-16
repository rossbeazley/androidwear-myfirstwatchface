package uk.co.rossbeazley.wear.android.ui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.R;

public class ViewGroupReplaceWatchFaceUINavigation implements WatchFaceUINavigation {


    private final ViewGroup container;

    public ViewGroupReplaceWatchFaceUINavigation(ViewGroup container) {

        this.container = container;
    }

    @Override
    public void screenDim() {
        container.removeAllViews();
        LayoutInflater.from(container.getContext()).inflate(R.layout.watch_face_dimmed,container);
    }

    @Override
    public void screenAwake() {
        container.removeAllViews();
        LayoutInflater.from(container.getContext()).inflate(R.layout.activity_my_watch_face,container);
    }

    @Override
    public void removed(){}

    @Override
    public void screenOff(){

    }

    public void defaultState() {
        screenDim();
    }
}
