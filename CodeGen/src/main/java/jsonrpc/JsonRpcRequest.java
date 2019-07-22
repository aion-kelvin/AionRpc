package jsonrpc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonRpcRequest {
    private final String jsonrpc;
    private final String method;
    private final String[] params;
    private final String id;


    @JsonCreator
    public JsonRpcRequest(@JsonProperty("method") String method,
                          @JsonProperty("params") String[] params,
                          @JsonProperty("id") String id,
                          @JsonProperty("jsonrpc") String jsonrpc) {
        this.jsonrpc = jsonrpc;
        this.method = method;
        this.params = params;
        this.id = id;
    }

    public String getJsonrpc() {
        return jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public Object getParams() {
        return params;
    }

    public String getId() {
        return id;
    }
}
