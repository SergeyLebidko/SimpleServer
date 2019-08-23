package simpleserver;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerProcess implements Runnable {

    private GUI gui;

    private Socket socket;
    private String threadName;

    public ServerProcess(Socket socket, GUI gui) {
        this.socket = socket;
        this.gui = gui;
    }

    @Override
    public void run() {
        threadName = Thread.currentThread().getName();
        gui.println("Поток-сервер запущен. Имя потока: " + threadName);

        byte[] buffer = new byte[64 * 1024];
        int readBytes;
        String requestString;

        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            while (true) {
                readBytes = in.read(buffer);
                HttpRequest request = HttpParser.parse(buffer, readBytes);

                gui.println();
                gui.println("Получен HTTP-запрос (поток-сервер: " + threadName + ")");
                gui.println(request.toString());
            }
        } catch (Exception e) {
            gui.println("При работе потока-сервера " + threadName + " возникла ошибка: " + e);
        }
    }

}
