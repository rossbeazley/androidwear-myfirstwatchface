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

        new DaysPresenter(days, daysView);

        daysChange.daysUpdate(new Day(1));
        assertThat(dateString, is("1st"));
    }

    private class DaysPresenter {
        public DaysPresenter(CanBeObservedForChangesToDays days, final DaysView daysView) {
            days.observe(new CanBeObservedForChangesToDays.CanReceiveDaysUpdates() {
                @Override
                public void daysUpdate(Day to) {
                    daysView.showDaysString(to.toOrdinalString());
                }
            });
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
