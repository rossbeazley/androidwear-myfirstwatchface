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

    public ConfigItemOptionsListFragment() {
    }

    public static ConfigItemOptionsListFragment createConfigItemOptionsListFragment() {
        final ConfigItemOptionsListFragment configItemOptionsListFragment = new ConfigItemOptionsListFragment();
        final Bundle args = new Bundle();
        args.putSerializable("factory",ConfigItemsOptionsListUIFactory.FACTORY);
        configItemOptionsListFragment.setArguments(args);
        return configItemOptionsListFragment;
    }

    public enum ConfigItemsOptionsListUIFactory implements ConfigItemsListFragment.UIFactory<ConfigOptionView> {

        FACTORY;

        @Override
        public View createView(ViewGroup container) {
            ConfigOptionsWearView configOptionsListWearView = new ConfigOptionsWearView(container.getContext());
            configOptionsListWearView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return configOptionsListWearView;
        }

        @Override
        public void createPresenters(ConfigService configService, ConfigOptionView view) {
            new ConfigOptionsPresenter(view, configService);
        }

    }
    private ConfigItemsListFragment.UIFactory uiFactory() {
        return (ConfigItemsListFragment.UIFactory) getArguments().getSerializable("factory");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = uiFactory().createView(container);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // make presenters, but need to cast :S
        uiFactory().createPresenters(configService,view);
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
