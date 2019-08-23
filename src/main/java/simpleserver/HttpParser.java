package simpleserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class HttpParser {

    public static HttpRequest parse(byte[] buffer, int size) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer, 0, size);
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(byteStream));

        String method;
        String url;
        String version;
        Map<String, String> parameters;

        String line = "";
        int count = 0;
        while (true) {
            try {
                line = lineReader.readLine();
            } catch (IOException e) {
            }
            if (line == null || line.equals("")) break;

            //Если прочитали первую строку - получаем из неё метод, url и версию протокола

            //Из остальных строк - получаем наименование и значение параметра

        }
        return null;
    }

}
