package uk.co.rossbeazley.wear.rotation;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class OrientationFromDegreesTest {

    @Test
    public void zeroDegreesIsNorth() {
        assertThat(Orientation.from(0.0f), is(Orientation.north()));
    }

    @Test
    public void ninetyDegreesIsEast() {
        assertThat(Orientation.from(90.0f), is(Orientation.east()));
    }

    @Test
    public void oneEightyDegreesIsSouth() {
        assertThat(Orientation.from(180.0f), is(Orientation.south()));
    }

    @Test
    public void twoSeventyDegreesIsWest() {
        assertThat(Orientation.from(270.0f), is(Orientation.west()));
    }
}
