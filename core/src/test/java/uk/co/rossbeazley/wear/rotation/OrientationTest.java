package uk.co.rossbeazley.wear.rotation;

import org.junit.Before;
import org.junit.Test;

import uk.co.rossbeazley.wear.CanBeObserved;
import uk.co.rossbeazley.wear.Core;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OrientationTest {

    private Core core;
    private CanBeObserved<CanReceiveRotationUpdates> rotation;
    private Orientation rotatedTo;

    @Before
    public void setUp() throws Exception {
        core = new Core();
        rotation = core.canBeObservedForChangesToRotation;
        rotation.addListener(new CanReceiveRotationUpdates() {
            @Override
            public void rotationUpdate(Orientation to) {
                rotatedTo = to;
            }
        });

    }

    @Test
    public void theOneWhereWeRotateRightToEast() {
        core.canBeRotated.right();
        assertThat(rotatedTo, is(Orientation.east()));
    }

    @Test
    public void theOneWhereWeRotateRightToSouth() {
        core.canBeRotated.right();
        core.canBeRotated.right();
        assertThat(rotatedTo, is(Orientation.south()));
    }


    @Test
    public void theOneWhereWeRotateRightToWest() {
        core.canBeRotated.right();
        core.canBeRotated.right();
        core.canBeRotated.right();
        assertThat(rotatedTo, is(Orientation.west()));
    }


    @Test
    public void theOneWhereWeRotateRightToNorth() {
        core.canBeRotated.right();
        core.canBeRotated.right();
        core.canBeRotated.right();
        core.canBeRotated.right();
        assertThat(rotatedTo, is(Orientation.north()));
    }


}
