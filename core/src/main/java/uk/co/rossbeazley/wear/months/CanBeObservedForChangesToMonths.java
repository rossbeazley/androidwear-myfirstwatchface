package uk.co.rossbeazley.wear.months;

/**
 * Created by beazlr02 on 20/11/2014.
 */
public interface CanBeObservedForChangesToMonths {
    void addListener(CanReceiveMonthsUpdates canReceiveMonthsUpdates);
    void removeListener(CanReceiveMonthsUpdates canReceiveMonthsUpdates);

}
