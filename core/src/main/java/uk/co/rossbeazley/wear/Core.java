package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.colour.CanReceiveColourUpdates;
import uk.co.rossbeazley.wear.colour.Colours;
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
    private Colours currentBackgroundColour;

    public Core() {
        this(Orientation.north());
    }


    public Core(Orientation orientation) {
        System.out.println("CORE INIT");
        Seconds seconds;
        MinutesFromTick minutes;
        HoursFromTick hours;
        DaysFromTick days;
        MonthsFromTick months;


        final Announcer<CanReceiveSecondsUpdates> canReceiveSecondsUpdatesAnnouncer = Announcer.to(CanReceiveSecondsUpdates.class);
        seconds = new Seconds(canReceiveSecondsUpdatesAnnouncer.announce());
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
                .addListeners(seconds, minutes, hours, days, months)
                .announce();

        Announcer<CanReceiveRotationUpdates> canReceiveRotationUpdatesAnnouncer = Announcer.to(CanReceiveRotationUpdates.class);
        Rotation rotation = new Rotation(orientation, canReceiveRotationUpdatesAnnouncer);
        canBeRotated = rotation;
        canBeObservedForChangesToRotation = canReceiveRotationUpdatesAnnouncer;

        setupColourSubsystem();
        currentBackgroundColour = new Colours(Colours.Colour.WHITE);
    }

    private void setupColourSubsystem() {
        final Announcer<CanReceiveColourUpdates> colourUpdates = Announcer.to(CanReceiveColourUpdates.class);
        canBeObservedForChangesToColour = colourUpdates;
        colourUpdates.registerProducer(new Announcer.Producer<CanReceiveColourUpdates>() {
            @Override
            public void observed(CanReceiveColourUpdates observer) {
                observer.colourUpdate(currentBackgroundColour);
            }
        });


        canBeColoured = new CanBeColoured() {
            @Override
            public void background(Colours.Colour colour) {
                currentBackgroundColour  = new Colours(colour);
                colourUpdates.announce().colourUpdate(currentBackgroundColour);
            }
        };
    }

    private static Core instance;

    public static Core instance() {
        if(instance==null) instance = new Core();
        return instance;
    }

    public static Core init() {
        return init(Orientation.north());
    }

    public static Core init(Orientation orientation) {
        return (instance  = new Core(orientation));
    }
}
