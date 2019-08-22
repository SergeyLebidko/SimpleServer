package simpleserver;

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
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            int threadNumber = 0;
            while (true) {
                gui.println("Ожидаю подключение. Порт: " + PORT);
                Socket socket = serverSocket.accept();

                gui.println("Клиент подключен. Запускаю экземпляр потока-сервера");
                Thread thread = new Thread(new ServerProcess(socket, gui), "Server-" + (threadNumber++));
                thread.setDaemon(true);
                thread.start();
            }
        } catch (Exception ex) {
            gui.println("Возникла ошибка при прослушивании портов: " + ex);
        }
    }

}
