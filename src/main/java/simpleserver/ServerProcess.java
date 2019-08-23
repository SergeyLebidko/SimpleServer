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

        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            //Получаем запрос
            readBytes = in.read(buffer);
            HttpRequest request = HttpParser.parse(buffer, readBytes);
            gui.println(threadName + " получил запрос: " + request.toString());

            System.out.println(request.getParameterValue("Cookie"));

            //Формируем ответ
            HttpResponse response = new HttpResponse();
            response.setVersion("HTTP/1.1");
            response.setCode("200");
            response.setPhrase("OK");

            out.write(response.getBytes());
            out.flush();

            socket.close();
        } catch (Exception e) {
            gui.println(threadName + " ошибка: " + e);
        }
        gui.println(threadName + " завершён");
    }

}
