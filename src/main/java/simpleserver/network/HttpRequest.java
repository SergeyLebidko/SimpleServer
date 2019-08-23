package simpleserver.network;

import java.util.Map;

public class HttpRequest {

    private String method;
    private String url;
    private String version;

    private Map<String, String> parameters;

    public HttpRequest(String method, String url, String version, Map<String, String> parameters) {
        this.method = method;
        this.url = url;
        this.version = version;
        this.parameters = parameters;
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getVersion() {
        return version;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public String getParameterValue(String parameterName) {
        return parameters.get(parameterName);
    }

    @Override
    public String toString() {
        return method + " /" + url + " " + version;
    }

}
