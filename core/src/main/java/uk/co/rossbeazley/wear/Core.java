package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.config.HashMapPersistence;
import uk.co.rossbeazley.wear.config.ConfigItem;
import uk.co.rossbeazley.wear.config.ConfigService;
import uk.co.rossbeazley.wear.config.StringPersistence;
import uk.co.rossbeazley.wear.colour.BackgroundColourConfigItem;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.ColourManager;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.colour.HoursColourConfigItem;
import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursModeConfigItem;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.rotation.CanBeRotated;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Rotation;
import uk.co.rossbeazley.wear.rotation.RotationConfigItem;
import uk.co.rossbeazley.wear.rotation.RotationPeristence;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class Core {

    private final BackgroundColourConfigItem backgroundColourConfigItem;
    private final RotationConfigItem rotationConfigItem;
    private final HoursColourConfigItem hoursColourConfigItem;

    public CanBeObserved<CanReceiveMonthsUpdates> canBeObservedForChangesToMonths;
    public CanBeObserved<CanReceiveDaysUpdates> canBeObservedForChangesToDays;
    public CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours;
    public CanBeObserved<CanReceiveMinutesUpdates> canBeObservedForChangesToMinutes;
    public CanBeObserved<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds;
    public CanBeTicked canBeTicked;

    public CanBeRotated canBeRotated;
    public CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation;

    public CanBeColoured canBeColoured;
    public CanBeObserved<CanReceiveColourUpdates> canBeObservedForChangesToColour;

    public ConfigService configService;
    public CanBeObserved<CanReceiveColourUpdates> canBeObservedForChangesToHoursColour;

    public CanConfigureHours canConfigureHours;

    public Core() {
        this(new HashMapPersistence());
    }

    public Core(StringPersistence persistence) {
        this(persistence, defaultOptions.defaultBackgroundColourConfigItem, defaultOptions.defaultRotationConfigItem, defaultOptions.defaultHoursColourConfigItem, new HoursModeConfigItem(HoursModeConfigItem.HR_12));
    }

    public static class DefaultOptions {
        public static final BackgroundColourConfigItem defaultBackgroundColourConfigItem = new BackgroundColourConfigItem();
        public static final RotationConfigItem defaultRotationConfigItem = new RotationConfigItem();
        public static final HoursColourConfigItem defaultHoursColourConfigItem = new HoursColourConfigItem(Colours.Colour.RED);
        public static final HoursModeConfigItem defaultHoursModeConfigItem = new HoursModeConfigItem(HoursModeConfigItem.HR_12);

        public static ConfigItem[] array() {
            return new ConfigItem[]{defaultBackgroundColourConfigItem, defaultRotationConfigItem, defaultHoursColourConfigItem, defaultHoursModeConfigItem};
        }
    }

    private static final DefaultOptions defaultOptions = new DefaultOptions();
    public static DefaultOptions defaultOptions() {return defaultOptions;}

    public BackgroundColourConfigItem backgroundColourConfigItem() {return backgroundColourConfigItem;}
    public RotationConfigItem rotationConfigItem() {return rotationConfigItem;}
    public HoursColourConfigItem hoursColourConfigItem() {return hoursColourConfigItem;}

    public Core(StringPersistence hashMapPersistence, BackgroundColourConfigItem backgroundColourConfigItem, RotationConfigItem rotationConfigItem, HoursColourConfigItem hoursColourConfigItem, HoursModeConfigItem hoursModeConfigItem) {
        this.backgroundColourConfigItem = backgroundColourConfigItem;
        this.rotationConfigItem = rotationConfigItem;
        this.hoursColourConfigItem = hoursColourConfigItem;
        configService = ConfigService.setupConfig(hashMapPersistence, backgroundColourConfigItem, rotationConfigItem, hoursColourConfigItem, hoursModeConfigItem);
        setupChronometerSubsystem(hoursModeConfigItem, configService);
        setupRotationSubsystem();
        setupColourManager();
    }

    private void setupChronometerSubsystem(HoursModeConfigItem hoursModeConfigItem, ConfigService configService) {

        Chronometer chronometer = new Chronometer(hoursModeConfigItem, configService);
        canBeObservedForChangesToSeconds = chronometer.canBeObservedForChangesToSeconds;
        canBeObservedForChangesToMinutes = chronometer.canBeObservedForChangesToMinutes;
        canBeObservedForChangesToDays = chronometer.canBeObservedForChangesToDays;
        canBeObservedForChangesToMonths = chronometer.canBeObservedForChangesToMonths;
        canBeObservedForChangesToHours = chronometer.canBeObservedForChangesToHours;
        canConfigureHours = chronometer.canConfigureHours;
        canBeTicked = chronometer.canBeTicked;
    }


    private void setupRotationSubsystem() {
        Announcer<CanReceiveRotationUpdates> canReceiveRotationUpdatesAnnouncer = Announcer.to(CanReceiveRotationUpdates.class);
        Rotation rotation = new Rotation(canReceiveRotationUpdatesAnnouncer, configService);
        RotationPeristence rotationPeristence = new RotationPeristence(configService, rotation, rotationConfigItem);
        canBeRotated = rotation;
        canBeObservedForChangesToRotation = canReceiveRotationUpdatesAnnouncer;
        canBeObservedForChangesToRotation.addListener(rotationPeristence);
    }

    private void setupColourManager() {
        ColourManager colourManager = new ColourManager(configService, backgroundColourConfigItem, hoursColourConfigItem);
        canBeObservedForChangesToColour = colourManager;
        canBeColoured = colourManager;

        canBeObservedForChangesToHoursColour = colourManager.canBeObservedForChangesToHoursColour;
    }


    public static Core instance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        public static Core instance;
    }

    public static Core init(StringPersistence persistence) {
        return InstanceHolder.instance = new Core(persistence);
    }

}
