package uk.co.rossbeazley.wear.minutes;

import org.junit.Test;

import uk.co.rossbeazley.wear.Sexagesimal;
import uk.co.rossbeazley.wear.seconds.CanBeObservedForChangesToSeconds;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MinutesFromSecondsChangeTest implements CanBeObservedForChangesToMinutes.CanReceiveMinutesUpdates {

    String timeComponentString;

    @Test
    public void theOneWhereTheSecondsTickBackOverToZero() {

        FakeSeconds fakeSeconds = new FakeSeconds();

        Sexagesimal startTime = Sexagesimal.fromBase10(8);
        CanBeObservedForChangesToMinutes minutes = new MinutesFromSeconds(startTime, fakeSeconds);
        minutes.observe(this);

        fakeSeconds.tickTo(Sexagesimal.fromBase10(59));

        timeComponentString = "RESET";

        fakeSeconds.tickTo(Sexagesimal.fromBase10(0));

        assertThat(timeComponentString, is("09"));
    }

    @Override
    public void minutesUpdate(Sexagesimal to) {
        timeComponentString=to.base10String();
    }

    private static class FakeSeconds implements CanBeObservedForChangesToSeconds {
        private CanReceiveSecondsUpdates canReceiveSecondsUpdates;

        @Override
        public void observe(CanReceiveSecondsUpdates canReceiveSecondsUpdates) {
            this.canReceiveSecondsUpdates = canReceiveSecondsUpdates;
        }

        public void tickTo(Sexagesimal sexagesimal) {
            canReceiveSecondsUpdates.secondsUpdate(sexagesimal);
        }
    }

    private class MinutesFromSeconds implements CanBeObservedForChangesToMinutes {
        private Sexagesimal minutes;
        private Sexagesimal seconds;
        private final CanBeObservedForChangesToSeconds secondsSource;
        private CanReceiveMinutesUpdates canReceiveMinutesUpdates;


        public MinutesFromSeconds(final Sexagesimal initialMinutes, final CanBeObservedForChangesToSeconds secondsSource) {
            this.minutes = initialMinutes;
            this.seconds = null;
            this.secondsSource = secondsSource;
            this.secondsSource.observe(new CanBeObservedForChangesToSeconds.CanReceiveSecondsUpdates() {
                @Override
                public void secondsUpdate(Sexagesimal to) {
                    if(seconds !=null && to.isLessThan(seconds)) {
                        incrementMinutes();
                    }
                    seconds = to;
                }
            });
        }

        private void incrementMinutes() {
            minutes = minutes.increment();
            canReceiveMinutesUpdates.minutesUpdate(minutes);
        }

        @Override
        public void observe(CanReceiveMinutesUpdates canReceiveMinutesUpdates) {
            this.canReceiveMinutesUpdates = canReceiveMinutesUpdates;
        }
    }

}
