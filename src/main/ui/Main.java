package ui;

import model.CircuitVariable;

public class Main {
    private static PatriceApplication activePatriceInstance;

    public static void main(String[] args) {
        activePatriceInstance = new PatriceApplication();
    }

    //EFFECTS: returns the current active Patrice application instance
    public static PatriceApplication getActiveInstance() {
        return activePatriceInstance;
    }


}
