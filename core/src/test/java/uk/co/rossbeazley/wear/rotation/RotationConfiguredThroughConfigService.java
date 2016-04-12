package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.android.ui.config.TestWorld;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class RotationConfiguredThroughConfigService {


    private Orientation observedValue;
    private TestWorld testWorld;
    private ConfigService configService;
    private String rotationItemId;
    private String aDifferentItem;
    private RotationConfigItem rotationConfigItem;

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
        rotationConfigItem = testWorld.core.rotationConfigItem();
        rotationItemId = rotationConfigItem.itemId();
    }

    @Test
    public void notifyOfChangeToNorth() {
        Orientation orientation = Orientation.north();
        configService.configureItem(rotationItemId);
        configService.chooseOption(rotationConfigItem.optionFor(orientation));
        assertThat(observedValue, is(orientation));
    }

    @Test
    public void notifyOfChangeToEast() {
        Orientation orientation = Orientation.east();
        configService.configureItem(rotationItemId);
        configService.chooseOption(rotationConfigItem.optionFor(orientation));
        assertThat(observedValue, is(orientation));
    }

    @Test
    public void notifyOfChangeToSouth() {
        Orientation orientation = Orientation.south();
        configService.configureItem(rotationItemId);
        configService.chooseOption(rotationConfigItem.optionFor(orientation));
        assertThat(observedValue, is(orientation));
    }

    @Test
    public void notifyOfChangeToWest() {
        Orientation orientation = Orientation.west();
        configService.configureItem(rotationItemId);
        configService.chooseOption(rotationConfigItem.optionFor(orientation));
        assertThat(observedValue, is(orientation));
    }


    @Test
    public void notNotifyOfChangeAfterDifferent() {
        String rotationID = rotationItemId;
        configService.configureItem(rotationID);
        String currentOptionForItem = configService.currentOptionForItem(rotationID);
        configService.chooseOption(testWorld.aDifferentOptionForItem(rotationID, currentOptionForItem));

        observedValue = null;
        aDifferentItem = testWorld.aDifferentItem(rotationID);
        configService.configureItem(aDifferentItem);
        String aDifferentOptionForItem = testWorld.aDifferentOptionForItem(aDifferentItem);
        configService.chooseOption(aDifferentOptionForItem);

        assertThat(observedValue, is(nullValue()));
    }

}
