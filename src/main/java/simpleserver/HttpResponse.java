package simpleserver;

public class HttpResponse {

    private String version;
    private String code;
    private String phrase;

    private byte[] content;

    public HttpResponse() {
        content = new byte[0];
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public byte[] getBytes() {
        String headerString = version + " " + code + " " + phrase + "\r\n";
        headerString += "Server: SimpleServer\r\n";
        headerString += "Content-Type: text/html; charset=utf-8\r\n";
        headerString += "Connection: close\r\n";
        headerString += "Content-Length: " + content.length + "\r\n\r\n";

        byte[] header = headerString.getBytes();
        byte[] response = new byte[header.length + content.length];

        return response;
    }

}
