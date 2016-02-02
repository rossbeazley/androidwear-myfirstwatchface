package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConfigOptionsList extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText("CONFIG LIST");
        textView.setTextSize(40);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
    }
}
