package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.android.ui.config.HashMapPersistence;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigItem;
import uk.co.rossbeazley.wear.android.ui.config.service.ConfigService;
import uk.co.rossbeazley.wear.android.ui.config.service.StringPersistence;
import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.ColourManager;
import uk.co.rossbeazley.wear.days.CanReceiveDaysUpdates;
import uk.co.rossbeazley.wear.days.DaysFromTick;
import uk.co.rossbeazley.wear.hours.CanReceiveHoursUpdates;
import uk.co.rossbeazley.wear.hours.HoursFromTick;
import uk.co.rossbeazley.wear.minutes.CanReceiveMinutesUpdates;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.months.CanReceiveMonthsUpdates;
import uk.co.rossbeazley.wear.months.MonthsFromTick;
import uk.co.rossbeazley.wear.rotation.CanBeRotated;
import uk.co.rossbeazley.wear.rotation.CanReceiveRotationUpdates;
import uk.co.rossbeazley.wear.rotation.Orientation;
import uk.co.rossbeazley.wear.rotation.Rotation;
import uk.co.rossbeazley.wear.rotation.RotationPeristence;
import uk.co.rossbeazley.wear.seconds.CanReceiveSecondsUpdates;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;

public class Core {

    public final CanBeObserved<CanReceiveMonthsUpdates> canBeObservedForChangesToMonths;
    public final CanBeObserved<CanReceiveDaysUpdates> canBeObservedForChangesToDays;
    public final CanBeObserved<CanReceiveHoursUpdates> canBeObservedForChangesToHours;
    public final CanBeObserved<CanReceiveMinutesUpdates> canBeObservedForChangesToMinutes;
    public final CanBeObserved<CanReceiveSecondsUpdates> canBeObservedForChangesToSeconds;
    public CanBeObserved<CanReceiveColourUpdates> canBeObservedForChangesToColour;

    public final CanBeTicked canBeTicked;
    public final CanBeRotated canBeRotated;
    public final CanBeObserved<CanReceiveRotationUpdates> canBeObservedForChangesToRotation;
    public CanBeColoured canBeColoured;
    public ConfigService configService;

    public Core() {
        this(new HashMapPersistence());
    }

    public Core(Orientation orientation) {
        this(orientation, new HashMapPersistence(), defaultOptions() );
    }

    public Core(StringPersistence persistence) {
        this(Orientation.north(), persistence, defaultOptions() );
    }

    public static ConfigItem[] defaultOptions() {
        return new ConfigItem[]{new ConfigItem("Background")
                .addOptions("Black", "White")
                .defaultOption("White")
        ,new ConfigItem("Rotation")
                        .addOptions("North", "East", "South", "West")
                        .defaultOption("North")};

//                ,new ConfigItem("12/24 Hour")
//                        .addOptions("Twelve", "Twenty Four", "Twelve Padded")
//                        .defaultOption("Twelve Padded")
    }

    public Core(Orientation orientation, StringPersistence hashMapPersistence, ConfigItem... defaultConfigOptions) {
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
        hours = new HoursFromTick(canReceiveHoursUpdatesAnnouncer);
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

        configService = ConfigService.setupConfig(hashMapPersistence, defaultConfigOptions);


        RotationPeristence rotationPeristence = new RotationPeristence(configService);
        Announcer<CanReceiveRotationUpdates> canReceiveRotationUpdatesAnnouncer = Announcer.to(CanReceiveRotationUpdates.class);
        Rotation rotation = new Rotation(rotationPeristence.reHydrateOrientation(), canReceiveRotationUpdatesAnnouncer, configService);
        canBeRotated = rotation;
        canBeObservedForChangesToRotation = canReceiveRotationUpdatesAnnouncer;
        canBeObservedForChangesToRotation.addListener(rotationPeristence);

        setupColourManager();
    }

    private void setupColourManager() {
        ColourManager colourManager = new ColourManager(configService);
        canBeObservedForChangesToColour = colourManager;
        canBeColoured = colourManager;
    }


    public static Core instance() {
        return InstanceHolder.instance;
    }

    private static class InstanceHolder {
        public static Core instance = new Core();
    }

    public static Core init() {
        return InstanceHolder.instance;
    }

}
