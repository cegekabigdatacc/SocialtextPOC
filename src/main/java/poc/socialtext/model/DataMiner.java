package poc.socialtext.model;

import java.util.*;

public class DataMiner {

    public static void main(String[] args) {

        mineForSignals();
    }

    private static void mineForSignals() {
        List<String> signals = new SocialtextRepository().retrieveSignals();
        new HBaseSignalDAO().putRawSignals(signals);
    }

}
