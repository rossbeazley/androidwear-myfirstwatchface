package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class RotationConfiguredThroughConfigService {


    private Orientation observedValue;
    private TestWorld testWorld;
    private ConfigService configService;

    @Before
    public void setUp() throws Exception {
        configService = (testWorld = new TestWorld()).build();
        CanReceiveRotationUpdates observer = new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                observedValue = to;
            }
        };
        testWorld.core.canBeObservedForChangesToRotation.addListener(observer);
        observedValue = null;
    }

    @Test
    public void notifyOfChangeToNorth() {
        configService.configureItem("Rotation");
        configService.chooseOption("North");
        assertThat(observedValue, is(Orientation.north()));
    }

    @Test
    public void notifyOfChangeToEast() {
        configService.configureItem("Rotation");
        configService.chooseOption("East");
        assertThat(observedValue, is(Orientation.east()));
    }

    @Test
    public void notifyOfChangeToSouth() {
        configService.configureItem("Rotation");
        configService.chooseOption("South");
        assertThat(observedValue, is(Orientation.south()));
    }

    @Test
    public void notifyOfChangeToWest() {
        configService.configureItem("Rotation");
        configService.chooseOption("West");
        assertThat(observedValue, is(Orientation.west()));
    }


    @Test
    public void notifyOfAnyChange() {
        String rotationID = "Rotation";
        configService.configureItem(rotationID);
        String currentOptionForItem = configService.currentOptionForItem(rotationID);
        configService.chooseOption(testWorld.aDifferentOptionForItem(rotationID, currentOptionForItem));

        observedValue = null;
        configService.configureItem("Background");
        configService.chooseOption("Black");

        assertThat(observedValue, is(Orientation.west()));
    }

}
