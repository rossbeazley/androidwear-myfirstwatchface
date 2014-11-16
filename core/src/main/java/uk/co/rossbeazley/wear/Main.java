package uk.co.rossbeazley.wear;

import uk.co.rossbeazley.wear.minutes.CanBeObservedForChangesToMinutes;
import uk.co.rossbeazley.wear.minutes.MinutesFromTick;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;
import uk.co.rossbeazley.wear.seconds.Seconds;
import uk.co.rossbeazley.wear.ticktock.CanBeTicked;
import uk.co.rossbeazley.wear.ticktock.TickTock;

public class Main {

    private static Main instance;
    public final Core core;

    public static Main instance() { return instance; }

    public static void init() { //thinking i will init the core with the external stuff?
        instance = new Main();
    }

    public Main() {

        this.core = new Core();

        TickTock tickTock = TickTock.createTickTock(this.core.canBeTicked);

        /** probably also initialise the ui navigation framework here */
    }

    public static class Core {

        public final CanBeObservedForChangesToMinutes canBeObservedForChangesToMinutes;
        public final CanBeObservedForChangesToSeconds canBeObservedForChangesToSeconds;

        public final CanBeTicked canBeTicked;

        private Seconds seconds;
        private MinutesFromTick minutes;

        public Core() {
            seconds = new Seconds();
            minutes = new MinutesFromTick();
            canBeObservedForChangesToSeconds = seconds;
            canBeObservedForChangesToMinutes = minutes;

            canBeTicked = Announcer.to(CanBeTicked.class)
                             .addListeners(seconds,minutes)
                             .announce();
        }
    }

}
