package uk.co.rossbeazley.wear.colour;

import uk.co.rossbeazley.wear.Announcer;
import uk.co.rossbeazley.wear.CanBeColoured;
import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

public class ColourManager implements CanBeObserved<CanReceiveColourUpdates>,CanBeColoured {
    private final ConfigService configService;
    private Colours currentBackgroundColour;
    private final Announcer<CanReceiveColourUpdates> colourUpdates;

    public ColourManager(ConfigService configService) {
        this.configService = configService;


        String background = configService.currentOptionForItem("Background");
        if(background.equals("Black")) {
            currentBackgroundColour = new Colours(Colours.Colour.BLACK);
        } else {
            currentBackgroundColour = new Colours(Colours.Colour.WHITE);
        }

        colourUpdates = Announcer.to(CanReceiveColourUpdates.class);
        colourUpdates.registerProducer(new Announcer.Producer<CanReceiveColourUpdates>() {
            @Override
            public void observed(CanReceiveColourUpdates observer) {
                observer.colourUpdate(currentBackgroundColour);
            }
        });


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
        currentBackgroundColour  = new Colours(colour);
        String option;
        if(colour == Colours.Colour.BLACK) {
            option = "Black";
        }else{
            option = "White";
        }

        configService.persistItemChoice("Background", option);

        colourUpdates.announce().colourUpdate(currentBackgroundColour);
    }
}
