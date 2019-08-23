package simpleserver;

import simpleserver.network.PortListener;

public class MainClass {

    public static void main(String[] args) {
        GUI gui = new GUI();
        PortListener portListener = new PortListener(gui);

        Thread thread = new Thread(portListener);
        thread.setDaemon(true);
        thread.start();
    }

}
