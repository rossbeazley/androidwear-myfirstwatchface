package com.examples.myfirstwatchface;

public interface WatchFace {
    /**
     * Used to detect when the watch is dimming.<br/>
     * Remember to make gray-scale versions of your watch face so they won't burn
     * and drain battery unnecessarily.
     */
    void screenDim();

    /**
     * Used to detect when the watch is not in a dimmed state.<br/>
     * This does not handle when returning "home" from a different activity/application.
     */
    void screenAwake();

    void removed();

    void screenOff();
}
