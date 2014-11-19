package uk.co.rossbeazley.wear.months;

import org.junit.Test;

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

        months.canReceiveMonthUpdates.monthUpdate(Month.fromBaseTen(3));

        assertThat(view.monthString, is("March"));
    }


    private class MonthsPresenter {
        public MonthsPresenter(CanBeObservedForChangesToMonths months, final FakeMonthView view) {

        }
    }



    private interface MonthView {
        void showMonthString(String monthString);
    }

    private interface CanBeObservedForChangesToMonths {
        void observe(CanReceiveMonthUpdates canReceiveMonthUpdates);

        interface CanReceiveMonthUpdates {
            void monthUpdate(Month month);
        }
    }

    private static class Month {
        private final String monthString;

        private Month(int i) {
            this.monthString = "March";
        }

        @Override
        public String toString() {
            return this.monthString;
        }

        public static Month fromBaseTen(int i) {
            return new Month(i);
        }
    }

    private static class FakeMonths implements CanBeObservedForChangesToMonths {
        private CanReceiveMonthUpdates canReceiveMonthUpdates;

        @Override
        public void observe(CanReceiveMonthUpdates canReceiveMonthUpdates) {

            this.canReceiveMonthUpdates = canReceiveMonthUpdates;
        }
    }


    private static class FakeMonthView implements MonthView {
        public String monthString;

        @Override
        public void showMonthString(String monthString) {
            this.monthString = monthString;
        }
    }

}
