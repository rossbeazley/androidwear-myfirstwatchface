package uk.co.rossbeazley.wear;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnnouncerTest {

    private final String notified = "NOTIFIED";
    private String observer = "IGNORED";

    @Test
    public void theOneWhereANewObserverIsUpdatedStraightAway() {

        Announcer<CanBeUpdated> announcer = Announcer.to(CanBeUpdated.class);

        announcer.registerProducer(new Announcer.Producer<CanBeUpdated>() {
            @Override public void observed(CanBeUpdated observer) {
                observer.update(notified);
            }
        });

        announcer.addListener(new CanBeUpdated() {
            @Override
            public void update(String newValue) {
                observer = newValue;
            }
        });

        assertThat(observer, is(notified));
    }

    @Test
    public void theOneWhereABadListenerBreaksTheAnnouncement() {
        Announcer<CanBeUpdated> announcer = Announcer.to(CanBeUpdated.class);

        announcer.addListener(new CanBeUpdated() {
            @Override
            public void update(String newValue) {
                throw new RuntimeException("Test throws exception on purpose");
            }
        });
        announcer.addListener(new CanBeUpdated() {
            @Override
            public void update(String newValue) {
                observer = newValue;
            }
        });

        announcer.announce().update(notified);
        assertThat(observer, is(notified));
    }

    private interface CanBeUpdated {
        public void update(String newValue);
    }

}