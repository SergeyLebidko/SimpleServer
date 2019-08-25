package simpleserver;

import simpleserver.explorer.ContentGenerator;
import simpleserver.network.PortListener;

public class MainClass {

    public static void main(String[] args) {
        GUI gui = new GUI();

        ContentGenerator contentGenerator = new ContentGenerator();
        PortListener portListener = new PortListener(gui, contentGenerator);

        Thread thread = new Thread(portListener);
        thread.setDaemon(true);
        thread.start();
    }

}
