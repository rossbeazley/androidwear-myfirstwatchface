package uk.co.rossbeazley.wear.rotation;

import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigServiceListener;

public class RotationPeristence implements CanReceiveRotationUpdates {

    private final ConfigService configService;
    private final RotationConfigItem rotationConfigItem;

    public RotationPeristence(ConfigService configService, final Rotation rotation, final RotationConfigItem rotationConfigItem) {
        this.configService = configService;
        this.rotationConfigItem = rotationConfigItem;
        rotation.to(reHydrateOrientation());

        configService.addListener(new ConfigServiceListener() {
            private String itemId;

            @Override
            public void configuring(String item) {
                this.itemId = item;
            }

            @Override
            public void error(KeyNotFound keyNotFound) {
            }

            @Override
            public void chosenOption(String option) {
                if (itemId.equals(rotationConfigItem.itemId())) {
                    rotation.to(configServiceStringToRotation(option));
                }
            }
        });
    }

    public Orientation reHydrateOrientation() {
        String rotation = configService.currentOptionForItem(rotationConfigItem.itemId());
        return configServiceStringToRotation(rotation);
    }

    private Orientation configServiceStringToRotation(String rotation) {
        return rotationConfigItem.orientationFor(rotation);
    }

    public void persistOrientation(Orientation orientation) {
        configService.persistItemChoice(rotationConfigItem.itemId(), rotationConfigItem.optionFor(orientation));
    }

    @Override
    public void rotationUpdate(Orientation to) {
        persistOrientation(to);
    }
}
