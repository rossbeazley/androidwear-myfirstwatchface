package uk.co.rossbeazley.wear.days;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DaysPresenterTest {
    private String dateString;
    private CanBeObservedForChangesToDays.CanReceiveDaysUpdates daysChange;

    @Test
    public void theOneWhereTheDayUpdates() {

        DaysView daysView = new DaysView() {
            @Override public void showDaysString(String newDays) {
                dateString = newDays;
            }
        };

        CanBeObservedForChangesToDays days = new CanBeObservedForChangesToDays() {
            @Override
            public void observe(CanReceiveDaysUpdates canReceiveDaysUpdates) {
                DaysPresenterTest.this.daysChange = canReceiveDaysUpdates;
            }
        };

        daysChange.daysUpdate(new Day(1));

        new DaysPresenter(days, daysView);

        assertThat(dateString, is("1st"));
    }

    private class DaysPresenter {
        public DaysPresenter(CanBeObservedForChangesToDays days, DaysView daysView) {

        }
    }

    private interface DaysView {
        void showDaysString(String newDays);
    }

    private interface CanBeObservedForChangesToDays {
        void observe(CanReceiveDaysUpdates canReceiveSecondsUpdates);

        interface CanReceiveDaysUpdates {
            void daysUpdate(Day to);
        }
    }

    private class Day {
        private final int value;

        public Day(int i) {
            this.value = i;
        }

        public String toOrdinalString() {
            return value+"st";
        }

    }
}
