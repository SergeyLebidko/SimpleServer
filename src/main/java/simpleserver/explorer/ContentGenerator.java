package simpleserver.explorer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ContentGenerator {

    private Map<String, File> links;
    private int linkIndex;

    public ContentGenerator() {
        links = new HashMap<>();
        linkIndex = 0;
    }

    public String getContent(String url) {
        String content;

        if (url.equals("") | url.equals("index")) {
            content = createIndexPage();
        } else if (links.get(url) != null) {
            content = createListPage(url);
        } else {
            content = create404Page();
        }

        return content;
    }

    private String createIndexPage() {
        String indexPage = "<html>" +
                "<head><title>Simple Server</title></head>" +
                "<body><h3>Simple Server - Локальные диски</h3></body>" +
                "</html>";
        indexPage += "<table>";

        for (File disk : getDisks()) {
            indexPage += "<tr><td>" + createLink(disk, null) + "</td></tr>";
        }
        indexPage += "</table>";

        return indexPage;
    }

    private String createListPage(String url) {
        File folder = links.get(url);

        String listPage = "<html>" +
                "<head><title>Simple Server</title></head>" +
                "<body>";

        //Добавляем на страницу текущий путь
        listPage += "<h3>" + (folder.getAbsolutePath()) + "</h3><p>";

        //Добавляем ссылку на главную страницу
        listPage += "<a href=\"index\">Главная страница</a><br>";

        //Добавляем ссылку на каталог верхнего уровня
        if (folder.getParentFile() != null) {
            listPage += createLink(folder.getParentFile(), "Вверх");
        }

        listPage += "</p><p></p>";

        //Проверяем первый особый случай - нельзя получить доступ к содержимому папки
        File[] fileList = getFileList(folder);
        if (fileList == null) {
            listPage += "<p>Невозможно получить доступ к содержимому папки</p>";
            listPage += "</body></html>";
            return listPage;
        }

        //Проверяем второй особый случай - папка пуста
        if (fileList.length == 0) {
            listPage += "<p>Папка пуста</p>";
            listPage += "</body></html>";
            return listPage;
        }

        //Папка не пуста и доступ к ней возможен
        listPage += "<table>";
        for (File element : getFileList(folder)) {
            if (element.isFile()) {
                listPage += "<tr><td>" + element.getName() + "</td></tr>";
            }
            if (element.isDirectory()) {
                listPage += "<tr><td>" + createLink(element, null) + "</td></tr>";
            }
        }
        listPage += "</table>";
        listPage += "</body></html>";

        return listPage;
    }

    private String create404Page() {
        String notFoundPage = "<html>" +
                "<head><title>Simple Server</title></head>" +
                "<body>" +
                "<h3>Simple Server - Страница не найдена</h3>" +
                "<p><a href=\"index\">На главную</a></p>" +
                "</body>" +
                "</html>";
        return notFoundPage;
    }

    private String createLink(File file, String text) {
        String ref = "" + (linkIndex++);
        String name = file.getName();
        if (name.equals("")) name = file.getAbsolutePath();
        String link = "<a href=\"" + ref + "\">" + (text == null ? name : text) + "</a>";
        links.put(ref, file);
        return link;
    }

    private File[] getDisks() {
        File[] disks = File.listRoots();
        return disks;
    }

    private File[] getFileList(File folder) {
        File[] listFiles = folder.listFiles();
        if (listFiles == null) return null;

        File[] result = new File[listFiles.length];

        int index = 0;
        for (File f : listFiles) {
            if (f.isDirectory()) {
                result[index++] = f;
            }
        }
        for (File f : listFiles) {
            if (f.isFile()) {
                result[index++] = f;
            }
        }

        return result;
    }

}
