package simpleserver.network;

import simpleserver.GUI;
import simpleserver.explorer.ContentGenerator;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerProcess implements Runnable {

    private GUI gui;

    private Socket socket;
    private ContentGenerator contentGenerator;

    public ServerProcess(Socket socket, GUI gui, ContentGenerator contentGenerator) {
        this.socket = socket;
        this.gui = gui;
        this.contentGenerator = contentGenerator;
    }

    @Override
    public void run() {
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
                synchronized (contentGenerator) {
                    String url = getUrl(buffer, readBytes);
                    gui.println("Запрошено: /" + url);
                    response = createResponse(contentGenerator.getContent(url));
                }
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
        byte[] contentBytes = content.getBytes();

        String headerString = "HTTP/1.1 200 OK\r\n";
        headerString += "Server: SimpleServer\r\n";
        headerString += "Content-Type: text/html; charset=utf-8\r\n";
        headerString += "Connection: close\r\n";
        headerString += "Content-Length: " + contentBytes.length + "\r\n\r\n";

        byte[] headerBytes = headerString.getBytes();
        byte[] response = new byte[headerBytes.length + contentBytes.length];

        int index = 0;
        for (byte b : headerBytes) {
            response[index++] = b;
        }
        for (byte b : contentBytes) {
            response[index++] = b;
        }

        return response;
    }

}
