package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.config.ConfigService;

public class UIFactoryFragment extends Fragment implements NeedsConfigService {
    private ConfigService configService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return uiFactory().createView(container);
    }

    private ConfigItemsListFragment.UIFactory uiFactory() {
        return (ConfigItemsListFragment.UIFactory) getArguments().getSerializable("factory");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        uiFactory().createPresenters(configService, view);
    }

    @Override
    public void attachConfigService(ConfigService configService) {
        this.configService = configService;
    }
}
