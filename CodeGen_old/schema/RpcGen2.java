import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import schema.JsonSchemaRef;
import schema.JsonSchemaTypeResolver;
import schema.ParamType;
import schema.SchemaException;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/** boilerplates and copy pasta */
public class RpcGen2 {
    private final ObjectMapper mapper = new ObjectMapper();
    private final JsonNode typesSchemaRoot;
    private final JsonSchemaTypeResolver resolver = new JsonSchemaTypeResolver();

    public static void main(String[] args) throws Exception {
        URL typesUrl = Resources.getResource("type.json");
        String types = Resources.toString(typesUrl, Charsets.UTF_8);

        URL reqUrl = Resources.getResource("eth_getBlockByNumber.request.json");
        String req = Resources.toString(reqUrl, Charsets.UTF_8);

        URL rezUrl = Resources.getResource("eth_getBlockByNumber.response.json");
        String rez = Resources.toString(rezUrl, Charsets.UTF_8);

        new RpcGen2(types).go("eth_getBlockByNumber", req, rez);
    }

    public RpcGen2(String typesSchemaRoot) throws Exception {
        this.typesSchemaRoot = mapper.readTree(typesSchemaRoot);
    }


    public void go(String name, String requestJsonSchema, String responseJsonSchema) throws IOException
    {
        JsonNode reqRoot = new ObjectMapper().readTree(requestJsonSchema);
        JsonNode rezRoot = new ObjectMapper().readTree(responseJsonSchema);

        System.out.println("Request");
        System.out.println("------------------------------------------------------------------");
        processRequestSchemas2(name, reqRoot, new HashMap<>());

        System.out.println();
        System.out.println();
        System.out.println("Response");
        System.out.println("------------------------------------------------------------------");
        processResponseSchemas(name, rezRoot, new HashMap<>());
    }


    private void processResponseSchemas(String name,
                                        JsonNode requestSchema,
                                        Map<String, JsonSchemaRef> refsVisited) {
        System.out.println("== Response raw param type ==");
        ParamType t = resolver.resolve(requestSchema, refsVisited);
        System.out.println(t);

        System.out.println();
        System.out.println("== Response params formatted ==");

        for(int ix = 0; ix < t.javaNames.size(); ++ix ) {
            System.out.println(t.javaTypes.get(ix) + " " + t.javaNames.get(ix));

        }

        System.out.println();
        System.out.println("== Refs declared ==");

        for(Map.Entry<String, JsonSchemaRef> refEntry : refsVisited.entrySet()) {
            String javaClassName = refEntry.getKey();
            JsonSchemaRef ref = refEntry.getValue();

            JsonNode deref = typesSchemaRoot;
            JsonNode lastDeref = null;
            // start at 1 because 0 is the root node which we're already in
            for(int ix = 1; ix < ref.getPath().length; ++ix) {
                lastDeref = deref;
                deref = deref.get(ref.getPath()[ix]);
                if(deref == null) {
                    throw new SchemaException("Broken reference at: " + lastDeref);
                }
            }

            System.out.println();
            System.out.println("-- Fields for generated class: " + javaClassName);

            // determining the Java types for the schema of custom schema.VisitableSchemaElement types is the same
            // as doing so for the schema of RPC methods' request/response with the exception
            // that the type is not allowed to use the "oneOf" keyword.  this means the returned
            // value will also be singleton.
            ParamType pt = resolver.resolve(deref, refsVisited);
            if(pt.isCollection()) {
                throw new IllegalStateException(
                        "Expected only one Java type to map to schema.VisitableSchemaElement type.  Ref: "
                                + ref.toString());
            }
            String javaType = pt.javaTypes.iterator().next();
            System.out.println(javaType + " " + pt.javaNames.iterator().next());
        }



    }

    private void processRequestSchemas2(String name,
                                       JsonNode requestSchema,
                                       Map<String, JsonSchemaRef> refsVisited) {
        // process each parameter in the param list using the JsonSchemaTypeResolver.
        // the top-level schema for the request itself can't use the resolver though,
        // because of its restriction on arrays.  so, handle the array manually.
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


        System.out.println("== Method signaturess ==");
        System.out.println(sigs);

        System.out.println();
        System.out.println("== Refs == ");
        System.out.println(refsVisited);

        System.out.println();
        System.out.println("== Refs Declared == ");

        for(Map.Entry<String, JsonSchemaRef> refEntry : refsVisited.entrySet()) {
            String javaClassName = refEntry.getKey();
            JsonSchemaRef ref = refEntry.getValue();

            JsonNode deref = typesSchemaRoot;
            JsonNode lastDeref = null;
            // start at 1 because 0 is the root node which we're already in
            for(int ix = 1; ix < ref.getPath().length; ++ix) {
                lastDeref = deref;
                deref = deref.get(ref.getPath()[ix]);
                if(deref == null) {
                    throw new SchemaException("Broken reference at: " + lastDeref);
                }
            }

            System.out.println();
            System.out.println("-- Fields for generated class: " + javaClassName);

            // determining the Java types for the schema of custom schema.VisitableSchemaElement types is the same
            // as doing so for the schema of RPC methods' request/response with the exception
            // that the type is not allowed to use the "oneOf" keyword.  this means the returned
            // value will also be singleton.
            ParamType pt = resolver.resolve(deref, refsVisited);
            if(pt.isCollection()) {
                throw new IllegalStateException(
                        "Expected only one Java type to map to schema.VisitableSchemaElement type.  Ref: "
                                + ref.toString());
            }
            String javaType = pt.javaTypes.iterator().next();
            System.out.println(javaType + " " + pt.javaNames.iterator().next());
        }

    };
}
