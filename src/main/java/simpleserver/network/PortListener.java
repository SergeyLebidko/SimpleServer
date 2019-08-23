package simpleserver.network;

import simpleserver.GUI;

import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable {

    private static int PORT = 4000;

    private GUI gui;

    public PortListener(GUI gui) {
        this.gui = gui;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 1)) {
            while (true) {
                gui.println("Сервер запущен. Порт: " + PORT);
                Socket socket = serverSocket.accept();

                Thread thread = new Thread(new ServerProcess(socket, gui));
                thread.setDaemon(true);
                thread.start();
            }
        } catch (Exception ex) {
            gui.println("Возникла ошибка при прослушивании портов: " + ex);
        }
    }

}
