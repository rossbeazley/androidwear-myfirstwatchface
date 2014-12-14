package uk.co.rossbeazley.wear;

public interface CanBeObserved<T> {

    void addListener(T canReceiveSecondsUpdates);

    void removeListener(T canReceiveSecondsUpdates);
}
