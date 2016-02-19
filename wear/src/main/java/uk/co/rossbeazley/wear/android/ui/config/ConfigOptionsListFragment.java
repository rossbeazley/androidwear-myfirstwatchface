package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ConfigOptionsListFragment extends Fragment {

    private ConfigService configService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConfigOptionsListWearView configOptionsListWearView = new ConfigOptionsListWearView(container.getContext());
        configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return configOptionsListWearView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        onViewCreated((ConfigListView)view, savedInstanceState);
    }

    public void onViewCreated(ConfigListView view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        new ConfigOptionsPresenter(configService, view);
    }

    public void attachConfigService(ConfigService configService) {
        this.configService = configService;
    }
}
