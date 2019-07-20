import static com.google.common.collect.Sets.cartesianProduct;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.io.Resources;
import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import schema.ByteArrayInliner;
import schema.JsonSchemaFixedMixedArrayResolver;
import schema.JsonSchemaRef;
import schema.JsonSchemaTypeResolver;
import schema.ParamType;

public class App {
    public static void main(String[] args) throws Exception {
        // Configure FreeMarker
        Configuration cfg = new Configuration();
        cfg.setClassForTemplateLoading(App.class, "templates");
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // Get JsonSchema stuff
        ObjectMapper mapper = new ObjectMapper();
        URL typesUrl = Resources.getResource("type.json");
        URL rezUrl = Resources.getResource("eth_getBlockByNumber.response.json");
        URL reqUrl = Resources.getResource("eth_getBlockByNumber.request.json");
        String types = Resources.toString(typesUrl, Charsets.UTF_8);
        String req = Resources.toString(reqUrl, Charsets.UTF_8);
        String rez = Resources.toString(rezUrl, Charsets.UTF_8);
        JsonNode typesSchemaRoot = mapper.readTree(types);
        JsonNode reqRoot = new ObjectMapper().readTree(req);
        JsonNode rezRoot = new ObjectMapper().readTree(rez);

        // Resolve types
        JsonSchemaTypeResolver resolver = new JsonSchemaTypeResolver();
        JsonSchemaFixedMixedArrayResolver arrayResolver =
            new JsonSchemaFixedMixedArrayResolver();
        Map<String, JsonSchemaRef> visitedRefs = new HashMap<>();
        ByteArrayInliner sub = new ByteArrayInliner(visitedRefs, typesSchemaRoot);

        // Parameter types to method signatures
        List<Set<String>> inputTypes = arrayResolver
            .resolve(reqRoot.get("items"), visitedRefs)
            .stream()
            .map(t -> sub.inline(t))
            .map(t -> new HashSet<>(t.javaTypes))
            .collect(Collectors.toList());
        Set<List<String>> signatures = Sets.cartesianProduct(inputTypes);

        // Ftl setup for Request
        Map<String, Object> ftlMap = new HashMap<>();
        ftlMap.put("rpcMethodName", "eth_getBlockByNumber");
        ftlMap.put("sigs", signatures);

        // Apply Freemarker template; output the result
        System.out.println("// -- REQUEST ------------------------------------------");
        Writer consoleWriter = new OutputStreamWriter(System.out);
        cfg.getTemplate("RpcInterface.java.ftl").process(ftlMap, consoleWriter);

        // Return value to class definition
        ParamType outputType = sub.inline(resolver.resolve(rezRoot, visitedRefs));
        List<List<String>> typeNamePairs = new LinkedList<>();
        for(int ix = 0; ix < outputType.javaNames.size(); ++ix) {
            typeNamePairs.add(List.of(
                outputType.javaTypes.get(ix),
                outputType.javaNames.get(ix)
            ));
        }
        ftlMap = new HashMap<>();
        ftlMap.put("rpcMethodName", "eth_getBlockByNumber");
        ftlMap.put("fields", typeNamePairs);

        System.out.println("// -- RESPONSE ------------------------------------------");
        cfg.getTemplate("RpcResponse.java.ftl").process(ftlMap, consoleWriter);

        for(Map.Entry<String, JsonSchemaRef> refEntry : visitedRefs.entrySet()) {
            ftlMap = new HashMap<>();
            ftlMap.put("rpcMethodName", "eth_getBlockByNumber");

            // each output class
            String javaClassName = refEntry.getKey();
            JsonSchemaRef ref = refEntry.getValue();

            System.out.println("// -- NEW DATATYPE: " + javaClassName + "--------------------------------");

            JsonNode deref = ref.dereference(typesSchemaRoot);

            outputType = sub.inline(resolver.resolve(deref, visitedRefs));
            typeNamePairs = new LinkedList<>();
            for(int ix = 0; ix < outputType.javaNames.size(); ++ix) {
                typeNamePairs.add(List.of(
                    outputType.javaTypes.get(ix),
                    outputType.javaNames.get(ix)
                ));
            }
            ftlMap.put("javaClassName", javaClassName);
            ftlMap.put("fields", typeNamePairs);
            cfg.getTemplate("RpcDataHolder.java.ftl").process(ftlMap, consoleWriter);
        }
    }

}
