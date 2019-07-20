package schema;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Replaces custom generated type names with byte[] when if the custom generated
 * uses a reference and the chain of references ends at "DATA".
 *
 * Examples:
 *
 * 1. ["DATA"] -> ["byte[]"]
 * 2. ["DATA32"] -> ["byte[]"]
 * 3. ["DATA", "string"] -> ["byte[]", "string"]
 * 4. ["DATA", "DATA', "boolean"] -> ["byte[]", "byte[]", "boolean"]
 * 5. ["QUANTITY", "boolean"] -> ["QUANTITY", "boolean"]
 *
 */
public class ByteArrayInliner {
    private Map<String, String> replacements = Map.of(
        "DATA", "byte[]",
        "QUANTITY", "java.math.BigNumber"
    );

    private Map<String, JsonSchemaRef> refs;
    private JsonNode types;

    public ByteArrayInliner(Map<String, JsonSchemaRef> loadedRefs,
                            JsonNode typeDefinitions) {
        this.refs = loadedRefs;
        this.types = typeDefinitions;
    }

    public ParamType inline(ParamType type) {
        List<String> inlined = type.javaTypes
            .stream()
//            .map(jt -> typeHasRefPathToData(jt) ? "byte[]" : jt)
            .map(jt -> replaceTypesIfPossible(jt))
            .collect(Collectors.toList());

        return new ParamType(type.kind, inlined, type.javaNames, type.refs);
    }

    public String replaceTypesIfPossible(String typeName) {
//        if("DATA".equals(typeName)) {
//            return true;
//        }

        for(Entry<String, String> replacement : replacements.entrySet()) {
            String newTypename = typeName.replace(replacement.getKey(), replacement.getValue());
            if(! typeName.equals(newTypename)) {
                return newTypename;
            }
        }

        JsonSchemaRef maybeRef = refs.get(typeName);
        if(maybeRef == null) {
            return typeName;
        }
        JsonNode deref = maybeRef.dereference(types);

        while(deref.has("$ref")) {
            JsonNode next = deref.get("$ref");
            maybeRef = new JsonSchemaRef(next.asText());

            for(Entry<String, String> replacement : replacements.entrySet()) {
                String newTypename = typeName.replace(replacement.getKey(), replacement.getValue());
                if(! typeName.equals(newTypename)) {
                    return newTypename;
                }
            }
        }

        return typeName;
    }

    public boolean typeHasRefPathToData(String typeName) {
        if("DATA".equals(typeName)) {
            return true;
        }

        JsonSchemaRef maybeRef = refs.get(typeName);
        if(maybeRef == null) {
            return false;
        }
        JsonNode deref = maybeRef.dereference(types);

        while(deref.has("$ref")) {
            JsonNode next = deref.get("$ref");
            maybeRef = new JsonSchemaRef(next.asText());

            if("DATA".equals(maybeRef.getName())) {
                return true;
            }
        }

        return false;
    }
}
