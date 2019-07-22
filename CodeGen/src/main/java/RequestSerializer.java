import com.fasterxml.jackson.databind.ObjectMapper;
import jsonrpc.JsonRpcRequest;

public class RequestSerializer {
    public static void main(String[] args) throws Exception {
        String payload = "{                                                                                                                                                                                                                   \n" +
                "  \"method\": \"eth_getBlockByNumber\",\n" +
                "  \"params\": [\"0xfe\", false],\n" +
                "  \"id\": \"1\",\n" +
                "  \"jsonrpc\": \"2.0\"\n" +
                "}";

        JsonRpcRequest req = new ObjectMapper().readValue(payload, JsonRpcRequest.class);

        System.out.println();
    }


}
