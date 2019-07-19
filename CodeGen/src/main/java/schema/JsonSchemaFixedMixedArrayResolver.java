package schema;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JsonSchemaFixedMixedArrayResolver {

    private final JsonSchemaTypeResolver resolver;

    public JsonSchemaFixedMixedArrayResolver() {
        this.resolver = new JsonSchemaTypeResolver();
    }

    public List<ParamType> resolve(JsonNode items, Map<String, JsonSchemaRef> refsVisited) {
        List<ParamType> paramTypes = new LinkedList<>();

        if(items == null || ! items.isArray()) {
            throw new SchemaException("items must be an array.");
        }
        for(Iterator<JsonNode> it = items.elements(); it.hasNext(); ) {
            JsonNode param = it.next();
            ParamType t = resolver.resolve(param, refsVisited);
            paramTypes.add(t);
        }

        return paramTypes;
    }
}
