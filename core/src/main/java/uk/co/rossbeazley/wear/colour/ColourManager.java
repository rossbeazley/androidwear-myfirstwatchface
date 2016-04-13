package uk.co.rossbeazley.wear.colour;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.CanBeColoured;
import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigServiceListener;

public class ColourManager implements CanBeObserved<CanReceiveColourUpdates>, CanBeColoured {
    private final ConfigService configService;
    private final BackgroundColourConfigItem backgroundColourConfigItem;
    private final ConfigServiceListener announceIfBackgroundColourReConfigured = new ConfigServiceListener() {
        private String item;

        @Override
        public void configuring(String item) {
            this.item = item;
        }

        @Override
        public void error(KeyNotFound keyNotFound) {

        }

        @Override
        public void chosenOption(String option) {
            if (item.equals(backgroundColourConfigItem.itemId())) {
                parseConfigServiceColourStringAndSet(option);
                colourUpdates.announce().colourUpdate(currentBackgroundColour);
            }
        }
    };
    private Colours currentBackgroundColour;
    private final Announcer<CanReceiveColourUpdates> colourUpdates;

    public ColourManager(final ConfigService configService, BackgroundColourConfigItem backgroundColourConfigItem) {
        this.configService = configService;
        this.backgroundColourConfigItem = backgroundColourConfigItem;

        String background = configService.currentOptionForItem(backgroundColourConfigItem.itemId());
        parseConfigServiceColourStringAndSet(background);

        colourUpdates = Announcer.to(CanReceiveColourUpdates.class);
        colourUpdates.registerProducer(new Announcer.Producer<CanReceiveColourUpdates>() {
            @Override
            public void observed(CanReceiveColourUpdates observer) {
                observer.colourUpdate(currentBackgroundColour);
            }
        });
        configService.addListener(announceIfBackgroundColourReConfigured);
    }

    private void parseConfigServiceColourStringAndSet(String background) {
        currentBackgroundColour = new Colours(backgroundColourConfigItem.colourFor(background));
    }

    @Override
    public void addListener(CanReceiveColourUpdates canReceiveSecondsUpdates) {
        colourUpdates.addListener(canReceiveSecondsUpdates);
    }

    @Override
    public void removeListener(CanReceiveColourUpdates canReceiveSecondsUpdates) {
        colourUpdates.removeListener(canReceiveSecondsUpdates);
    }

    @Override
    public void background(Colours.Colour colour) {
        currentBackgroundColour = new Colours(colour);
        String option;
        option = backgroundColourConfigItem.optionFor(colour);
        configService.persistItemChoice(backgroundColourConfigItem.itemId() , option);
        colourUpdates.announce().colourUpdate(currentBackgroundColour);
    }
}
