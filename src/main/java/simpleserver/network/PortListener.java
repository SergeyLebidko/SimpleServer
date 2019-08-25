package simpleserver.network;

import simpleserver.GUI;
import simpleserver.explorer.ContentGenerator;

import java.net.ServerSocket;
import java.net.Socket;

public class PortListener implements Runnable {

    private static int PORT = 4000;

    private GUI gui;
    private ContentGenerator contentGenerator;

    public PortListener(GUI gui, ContentGenerator contentGenerator) {
        this.gui = gui;
        this.contentGenerator = contentGenerator;
    }

    @Override
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT, 1)) {
            gui.println("Сервер запущен. Порт: " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();

                Thread thread = new Thread(new ServerProcess(socket, gui, contentGenerator));
                thread.setDaemon(true);
                thread.start();
            }
        } catch (Exception ex) {
            gui.println("Возникла ошибка при прослушивании портов: " + ex);
        }
    }

}
