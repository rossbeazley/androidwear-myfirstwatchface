package uk.co.rossbeazley.wear.rotation;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrientationToDegreesTest {

    @Test
    public void zeroDegreesIsNorth() {
        assertThat(Orientation.north().degrees(),is(0.0f));
    }

    @Test
    public void ninetyDegreesIsEast() {
        assertThat(Orientation.east().degrees(), is(90.0f));
    }

    @Test
    public void oneEightyDegreesIsSouth() {
        assertThat(Orientation.south().degrees(), is(180.0f));
    }

    @Test
    public void twoSeventyDegreesIsWest() {
        assertThat(Orientation.west().degrees(), is(270.0f));
    }
}
