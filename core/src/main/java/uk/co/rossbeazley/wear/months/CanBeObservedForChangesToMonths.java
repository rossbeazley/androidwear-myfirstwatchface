package uk.co.rossbeazley.wear.months;

/**
 * Created by beazlr02 on 20/11/2014.
 */
public interface CanBeObservedForChangesToMonths<t> {
    void addListener(t canReceiveMonthsUpdates);
    void removeListener(t canReceiveMonthsUpdates);

}
