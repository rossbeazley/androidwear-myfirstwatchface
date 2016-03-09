package uk.co.rossbeazley.wear.android.ui.config.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class ConfigItemsListFragment extends Fragment {

    private ConfigService configService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConfigItemsListWearView configOptionsListWearView = new ConfigItemsListWearView(container.getContext());
        configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return configOptionsListWearView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        onViewCreated((ConfigItemsListView)view, savedInstanceState);
    }

    public void onViewCreated(ConfigItemsListView view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        new ConfigOptionsPresenter(configService, view);
    }

    public void attachConfigService(ConfigService configService) {
        this.configService = configService;
    }
}