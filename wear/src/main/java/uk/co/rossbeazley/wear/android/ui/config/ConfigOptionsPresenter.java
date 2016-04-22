package uk.co.rossbeazley.wear.android.ui.config;

import uk.co.rossbeazley.wear.config.ConfigService;

public class ConfigOptionsPresenter {

    /**
     *
     * maybe it would be a good idea to totally dumb down views so they just speak in terms of
     * primitive data, the presenter is the thing that actually adapts the strings and things
     * into domain objects, maybe it can speak in terms of uiclick and show string and read string
     * it might make a number of view implementations more reusable, eg lists of items
     *
     *
     * the presenter would be responsible for mapping uiclick into the domain message
     * so there would be a generic uiclick listener, maybe viewActionListener
     *
     * In a way this is a more classical MVC arrangement with the view being composed
     * of the P+V from MVP... The M is still the same, but where is the controller?
     *
     * With MVP as I do it, there is something I call a "navigation controller"
     * this is responsible for bringing together the MVP triad.
     *
     * So here we have is Model-ViewPresenter-Controller.
     */

    public ConfigOptionsPresenter(ConfigOptionView configOptionView, final ConfigService configService) {
        configOptionView.addListener(new ConfigOptionView.Listener() {
            @Override
            public void itemSelected(String option) {
                configService.chooseOption(option);
            }
        });
        configOptionView.showConfigOptions(configService.selectedConfigOptions());
    }
}
