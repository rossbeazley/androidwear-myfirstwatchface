package uk.co.rossbeazley.wear.rotation;

public class Orientation {

    private static final Orientation NORTH = new Orientation(0.0f,1);
    private static final Orientation EAST = new Orientation(90.0f,2);
    private static final Orientation SOUTH = new Orientation(180.0f,3);
    private static final Orientation WEST = new Orientation(270.0f,0);

    private float degrees;
    private final int nextIndex;

    Orientation(float degrees, int nextIndex) {
        this.degrees = degrees;
        this.nextIndex = nextIndex;
    }

    public float degrees() {
        return degrees;
    }

    public Orientation right() {
        final Orientation[] compass = {NORTH,EAST,SOUTH,WEST};
        return compass[nextIndex];
    }

    public static Orientation north() {
        return NORTH;
    }

    public static Orientation east() {
        return EAST;
    }

    public static Orientation south() {
        return SOUTH;
    }

    public static Orientation west() {
        return WEST;
    }
}
