package uk.co.rossbeazley.wear.rotation;

/**
* Created by beazlr02 on 27/11/2014.
*/
public class Rotation {

    private float degrees;

    Rotation(float degrees) {
        this.degrees = degrees;
    }

    public static Rotation north() {
        return new Rotation(0.0f);
    }

    public float degrees() {
        return degrees;
    }

    public static Rotation east() {
        return new Rotation(90.0f);
    }

    public Rotation right() {
        return null;
    }
}
