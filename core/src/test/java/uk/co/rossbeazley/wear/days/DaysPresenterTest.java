package uk.co.rossbeazley.wear.days;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class DaysPresenterTest {
    private String dateString;
    private CanReceiveDaysUpdates daysChange;

    @Test
    public void theOneWhereTheDayUpdates() {

        DaysPresenter.DaysView daysView = new DaysPresenter.DaysView() {
            @Override public void showDaysString(String newDays) {
                dateString = newDays;
            }
        };

        CanBeObservedForChangesToDays days = new CanBeObservedForChangesToDays() {
            @Override
            public void addListener(CanReceiveDaysUpdates canReceiveDaysUpdates) {
                DaysPresenterTest.this.daysChange = canReceiveDaysUpdates;
            }

            @Override
            public void removeListener(CanReceiveDaysUpdates canReceiveSecondsUpdates) {

            }
        };

        new DaysPresenter(days, daysView);

        daysChange.daysUpdate(Day.fromBase10(1));
        assertThat(dateString, is("1st"));
    }

}
