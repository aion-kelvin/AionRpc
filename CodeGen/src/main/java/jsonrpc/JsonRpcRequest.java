package jsonrpc;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.json.UTF8StreamJsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;

public class JsonRpcRequest {
    private final String jsonrpc;
    private final String method;
    private final String params;
    private final String id;


    @JsonCreator
    public JsonRpcRequest(@JsonProperty("method") String method,
                          @JsonProperty("params") String params,
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

    public String getParams() {
        return params;
    }

    public String getId() {
        return id;
    }

    public void test() throws Exception {
        JsonFactory fact = new JsonFactory();
        JsonParser p = fact.createParser("[\"0x11\", false, {\"some\" : \"thing\"} ]");
        while(!p.isClosed()){
            JsonToken jsonToken = p.nextToken();

            System.out.println("jsonToken = " + jsonToken);
        }
    }
}
