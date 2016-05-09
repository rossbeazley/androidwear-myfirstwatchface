package uk.co.rossbeazley.wear.android.ui.config;

import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.config.ConfigService;

public interface UIFactory<ViewType> {
    View createView(ViewGroup container);
    void createPresenters(ConfigService configService, ViewType view);
}
