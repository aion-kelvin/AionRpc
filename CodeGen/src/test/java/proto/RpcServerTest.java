package proto;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jsonrpc.JsonRpcRequest;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class RpcServerTest {
    @Test
    public void test() throws Exception {
        RpcServer s = new RpcServer();
        JsonRpcRequest req = new JsonRpcRequest(
                "eth_getBlockByNumber",
                "[\"0x321\", \"false\"]",
                "1",
                "2.0"
        );
        s.execute(req);
    }

    @Test
    public void test2() throws Exception {
        String x = "[\"0x321\", \"false\", { \"hello\" : \"world\"} ]";








//        for(int ix = 0; ix < vals.length; ++ix){
//            System.out.println(vals[ix]);
//        }
    }
}