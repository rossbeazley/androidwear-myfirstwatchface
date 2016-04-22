package uk.co.rossbeazley.wear.android.ui.config;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigItemOptionsListFragment extends Fragment implements NeedsConfigService {

    private ConfigService configService;

    @Nullable
    @Override
    public ConfigOptionsWearView onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ConfigOptionsWearView configOptionsListWearView = new ConfigOptionsWearView(container.getContext());
        configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return configOptionsListWearView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        buildPresenters((ConfigOptionView)view, savedInstanceState);
    }

    public void buildPresenters(ConfigOptionView view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        new ConfigOptionsPresenter(view,configService);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        /* tell presenter to clear down unless...

           options are weak refs in config service,
           nothing else works with weak references as its the config service that is long lived

           another option is to not make config service long lived,
           this means either persisting current choice to database
           or pushing more info into the views. If views become stateful then due to android
           we need to start serialising stuff on rotation etc,
           feels better to push state service side then views always just render the current state (ie no lies)


           The actual reality in this current design is the presenters dont add observers to the config service
           so no such clear down is required, in a larger system I would expect this to happen
         */
    }

    @Override
    public void attachConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public String tag() {
        return "CONFIG_OPTIONS";
    }
}
