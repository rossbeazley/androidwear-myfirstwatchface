package uk.co.rossbeazley.wear.rotation;

public class Rotation {

    private static final Rotation NORTH = new Rotation(0.0f,1);
    private static final Rotation EAST = new Rotation(90.0f,2);
    private static final Rotation SOUTH = new Rotation(180.0f,3);
    private static final Rotation WEST = new Rotation(270.0f,0);

    private float degrees;
    private final int nextIndex;

    Rotation(float degrees, int nextIndex) {
        this.degrees = degrees;
        this.nextIndex = nextIndex;
    }

    public float degrees() {
        return degrees;
    }

    public Rotation right() {
        final Rotation[] compass = {NORTH,EAST,SOUTH,WEST};
        return compass[nextIndex];
    }

    public static Rotation north() {
        return NORTH;
    }
}
