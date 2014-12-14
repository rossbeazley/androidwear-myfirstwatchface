package uk.co.rossbeazley.wear.months;

import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by beazlr02 on 19/11/2014.
 */
public class MonthsPresenterTest {

    @Test
    public void theOneWhereTheMonthsUpdate() {

        FakeMonthView view = new FakeMonthView();

        FakeMonths months = new FakeMonths();

        new MonthsPresenter(months, view);

        months.canReceiveMonthUpdates.monthsUpdate(Month.fromBaseTen(3));

        assertThat(view.monthString, is("March"));
    }


    private static class FakeMonths implements CanBeObserved<CanReceiveMonthsUpdates> {
        private CanReceiveMonthsUpdates canReceiveMonthUpdates;

        @Override
        public void addListener(CanReceiveMonthsUpdates canReceiveMonthsUpdates) {
            this.canReceiveMonthUpdates = canReceiveMonthsUpdates;
        }

        @Override
        public void removeListener(CanReceiveMonthsUpdates canReceiveMonthsUpdates) {

        }
    }


    private static class FakeMonthView implements MonthsPresenter.MonthView {
        public String monthString;

        @Override
        public void showMonthString(String monthString) {
            this.monthString = monthString;
        }
    }

}
