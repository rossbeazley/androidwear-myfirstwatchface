package uk.co.rossbeazley.seconds;

/**
 * Created by rdlb on 14/11/14.
 */
public interface CanPostToMainThread {
    boolean post(Runnable action);
}
