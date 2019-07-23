package proto;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import jsonrpc.JsonRpcRequest;
import org.everit.json.schema.Schema;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import schema.JsonSchemaRef;
import schema.JsonSchemaTypeResolver;
import schema.ParamType;
import schema.SchemaException;

import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class RpcServer {
    RpcInterface rpc;
    private final JsonSchemaTypeResolver resolver = new JsonSchemaTypeResolver();
    Map<String, JsonSchemaRef> refsVisited;

    public RpcServer() throws  Exception {
        this.refsVisited = new HashMap<String, JsonSchemaRef>();
    }

    public String execute(JsonRpcRequest req) throws Exception {




        switch(req.getMethod()) {
            case "eth_getBlockbyNumber":
                URL reqUrl = Resources.getResource("eth_getBlockByNumber.request.json");
                String reqSchema = Resources.toString(reqUrl, Charsets.UTF_8);
                JsonNode reqSchemaRoot = new ObjectMapper().readTree(reqSchema);

                Set<List<String>> matchingSigs = matchingSigs(reqSchemaRoot);
                String params = req.getParams();
                // if params first arg == TAG, call
                //   rpc.eth_getBlockByNumber( (TAG) param_0, param_1)
                // else if params first arg == QUANTITY, call
                //   rpc.eth_getBlockbyNumber( (QUANTITY) param_0, param_1)



//                return rpc.eth_getBlockByNumber();
            break;
            default:
                throw new UnsupportedOperationException("Method not found");
        }
        return null;
    }

    public Set<List<String>> matchingSigs(JsonNode requestSchema) {
        JsonNode items = requestSchema.get("items");
        // need a set of types for each param since overloads are allowed
        List<Set<String>> paramTypes = new LinkedList<>();

        if(items == null || ! items.isArray()) {
            throw new SchemaException("items must be an array.");
        }
        for(Iterator<JsonNode> it = items.elements(); it.hasNext(); ) {
            JsonNode param = it.next();
            ParamType t = resolver.resolve(param, refsVisited);
            paramTypes.add(new HashSet<>(t.javaTypes));
        }

        // we have a set of types for each param in the param list.
        // need to make a Java method for every combination of param types.
        Set<List<String>> sigs = Sets.cartesianProduct(paramTypes);
        return sigs;
    }



}
