package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;
import uk.co.rossbeazley.wear.colour.BackgroundColourConfigItem;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.ColourManager;
import uk.co.rossbeazley.wear.colour.Colours;
import uk.co.rossbeazley.wear.colour.HoursColourConfigItem;
import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursBaseConfigItem;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.months.MonthsFromTick;
import uk.co.rossbeazley.wear.rotation.CanBeRotated;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Rotation;
import uk.co.rossbeazley.wear.rotation.RotationConfigItem;
import uk.co.rossbeazley.wear.rotation.RotationPeristence;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.seconds.Seconds;
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

    public Core() {
        this(new HashMapPersistence());
    }

    public Core(StringPersistence persistence) {
        this(persistence, defaultOptions.defaultBackgroundColourConfigItem, defaultOptions.defaultRotationConfigItem, defaultOptions.defaultHoursColourConfigItem, null);
    }

    public static class DefaultOptions {
        public static final BackgroundColourConfigItem defaultBackgroundColourConfigItem = new BackgroundColourConfigItem();
        public static final RotationConfigItem defaultRotationConfigItem = new RotationConfigItem();
        public static final HoursColourConfigItem defaultHoursColourConfigItem = new HoursColourConfigItem(Colours.Colour.RED);

        public static ConfigItem[] array() {
            return new ConfigItem[]{defaultBackgroundColourConfigItem, defaultRotationConfigItem, defaultHoursColourConfigItem};
        }
    }

    private static final DefaultOptions defaultOptions = new DefaultOptions();
    public static DefaultOptions defaultOptions() {return defaultOptions;}

    public BackgroundColourConfigItem backgroundColourConfigItem() {return backgroundColourConfigItem;}
    public RotationConfigItem rotationConfigItem() {return rotationConfigItem;}
    public HoursColourConfigItem hoursColourConfigItem() {return hoursColourConfigItem;}

    public Core(StringPersistence hashMapPersistence, BackgroundColourConfigItem backgroundColourConfigItem, RotationConfigItem rotationConfigItem, HoursColourConfigItem hoursColourConfigItem, HoursBaseConfigItem hoursBaseConfigItem) {
        this.backgroundColourConfigItem = backgroundColourConfigItem;
        this.rotationConfigItem = rotationConfigItem;
        this.hoursColourConfigItem = hoursColourConfigItem;
        setupChronometerSubsystem(hoursBaseConfigItem);
        configService = ConfigService.setupConfig(hashMapPersistence, backgroundColourConfigItem, rotationConfigItem, hoursColourConfigItem);
        setupRotationSubsystem();
        setupColourManager();
    }

    private void setupChronometerSubsystem(HoursBaseConfigItem hoursBaseConfigItem) {
        Seconds seconds;
        MinutesFromTick minutes;
        HoursFromTick hours;
        DaysFromTick days;
        MonthsFromTick months;


        final Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdatesAnnouncer = Announcer.to(CanReceiveSecondsUpdates.class);
        seconds = new Seconds(canReceiveSecondsUpdatesAnnouncer);
        canBeObservedForChangesToSeconds = canReceiveSecondsUpdatesAnnouncer;

        Announcer<CanReceiveMinutesUpdates> canReceiveMinutesUpdatesAnnouncer = Announcer.to(CanReceiveMinutesUpdates.class);
        minutes = new MinutesFromTick(canReceiveMinutesUpdatesAnnouncer);
        canBeObservedForChangesToMinutes = canReceiveMinutesUpdatesAnnouncer;

        Announcer<CanReceiveHoursUpdates> canReceiveHoursUpdatesAnnouncer = Announcer.to(CanReceiveHoursUpdates.class);
        hours = new HoursFromTick(canReceiveHoursUpdatesAnnouncer, hoursBaseConfigItem);
        canBeObservedForChangesToHours = canReceiveHoursUpdatesAnnouncer;

        Announcer<CanReceiveDaysUpdates> canReceiveDaysUpdatesAnnouncer = Announcer.to(CanReceiveDaysUpdates.class);
        days = new DaysFromTick(canReceiveDaysUpdatesAnnouncer);
        canBeObservedForChangesToDays = canReceiveDaysUpdatesAnnouncer;

        Announcer<CanReceiveMonthsUpdates> canReceiveMonthsUpdatesAnnouncer = Announcer.to(CanReceiveMonthsUpdates.class);
        months = new MonthsFromTick(canReceiveMonthsUpdatesAnnouncer);
        canBeObservedForChangesToMonths = canReceiveMonthsUpdatesAnnouncer;

        canBeTicked = Announcer.to(CanBeTicked.class)
                .addListeners(months, days, hours, minutes, seconds)
                .announce();
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
