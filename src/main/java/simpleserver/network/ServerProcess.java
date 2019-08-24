package simpleserver.network;

import simpleserver.GUI;

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

        byte[] buffer = new byte[64 * 1024];
        int readBytes;

        try {
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            //Ожидаем данные от клиента
            readBytes = in.read(buffer);

            //Формируем ответ
            byte[] response;
            if (readBytes != (-1)) {
                String url = getUrl(buffer, readBytes);
                gui.println("Запрошено: /" + url);
                response = createResponse("");
            } else {
                response = createResponse("");
            }

            out.write(response);
            out.flush();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            gui.println("Ошибка: " + e);
        }
    }

    private String getUrl(byte[] buffer, int size) {
        String request = new String(buffer, 0, size);
        int pos = request.indexOf('\r');
        String firstLine = request.substring(0, pos);
        String url = firstLine.split(" ")[1].substring(1);
        return url;
    }

    private byte[] createResponse(String content) {
        String headerString = "HTTP/1.1 200 OK\r\n";
        headerString += "Server: SimpleServer\r\n";
        headerString += "Content-Type: text/html; charset=utf-8\r\n";
        headerString += "Connection: close\r\n";
        headerString += "Content-Length: " + content.length() + "\r\n\r\n";

        byte[] header = headerString.getBytes();
        byte[] response = new byte[header.length + content.length()];

        return response;
    }

}
