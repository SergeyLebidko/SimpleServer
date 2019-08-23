package simpleserver;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class HttpParser {

    public static HttpRequest parse(byte[] buffer, int size) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(buffer, 0, size);
        BufferedReader lineReader = new BufferedReader(new InputStreamReader(byteStream));

        String method = "";
        String url = "";
        String version = "";
        Map<String, String> parameters = new HashMap<>();

        String line = "";
        int count = 0;
        while (true) {
            try {
                line = lineReader.readLine();
                count++;
            } catch (IOException e) {
            }
            if (line == null || line.equals("")) break;

            //Если прочитали первую строку - получаем из неё метод, url и версию протокола
            if (count == 1) {
                String[] splitLine = line.split(" ");
                method = splitLine[0];
                url = splitLine[1].substring(1);
                version = splitLine[2];
            }

            //Из остальных строк - получаем наименование и значение параметра
            if (count > 1) {
                int pos = line.indexOf(":");
                parameters.put(line.substring(0, pos), line.substring(pos + 1));
            }
        }
        return new HttpRequest(method, url, version, parameters);
    }

}
