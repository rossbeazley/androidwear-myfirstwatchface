package uk.co.rossbeazley.wear.android.ui.config.service;

public interface ConfigServiceListener {
    void configuring(String item);

    void error(KeyNotFound keyNotFound);

    void chosenOption(String option);

    class KeyNotFound {
        private final String noneExistentKey;

        public KeyNotFound(String noneExistentKey) {
            this.noneExistentKey = noneExistentKey;
        }

        @Override
        public String toString() {
            return "KeyNotFound \"" + noneExistentKey + "\"";
        }

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof KeyNotFound)) return false;
            KeyNotFound keyNotFound = (KeyNotFound) o;
            boolean rtn = (keyNotFound.noneExistentKey) != null ? keyNotFound.noneExistentKey.equals(noneExistentKey) : false;
            return rtn;
        }
    }
}
