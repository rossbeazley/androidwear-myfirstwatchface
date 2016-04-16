package uk.co.rossbeazley.wear.colour;

public class Colours {

    private Colour backgroundColour;

    public Colours(Colour backgroundColour) {
        this.backgroundColour = backgroundColour;
    }

    public Colour colour() {
        return backgroundColour;
    }

    public final static class Colour {
        public static final Colour BLACK = new Colour(0xFF000000);
        public static final Colour WHITE = new Colour(0xffffffff);
        public static final Colour RED = new Colour(0xffff0000);
        public static final Colour BLUE = new Colour(0xff0000ff);
        public static final Colour GREEN = new Colour(0xff00ff00);


        private final int colourIntValue;

        public Colour(int colourIntValue) {
            this.colourIntValue = colourIntValue;
        }

        public boolean equals(Object o) {
            return colourIntValue == o.hashCode();
        }

        public int hashCode() {
            return colourIntValue;
        }

        public String toString() {
            String unpadded = Integer.toHexString(colourIntValue);
            String padded = "00000000".substring(unpadded.length()) + unpadded;
            return "0x" + padded;
        }

        public int toInt() {
            return colourIntValue;
        }
    }
}
