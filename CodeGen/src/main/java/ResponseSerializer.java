import com.fasterxml.jackson.databind.ObjectMapper;
import jsonrpc.JsonRpcResponse;
import proto.eth_getBlockByNumberResponse;

import java.math.BigInteger;

public class ResponseSerializer {
    public static void main(String[] args) throws Exception {
        eth_getBlockByNumberResponse resp = new eth_getBlockByNumberResponse(
                new BigInteger("12"),
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc },
                new BigInteger("12"),
                new BigInteger("12"),
                new byte[] { 0xf, 0x1, 0xc },
                new BigInteger("12"),
                new BigInteger("12"),
                new BigInteger("12"),
                new BigInteger("12"),
                new BigInteger("12"),
                new BigInteger("12"),
                new Object[] { 0xf, 0x1, 0xc },
                new byte[] { 0xf, 0x1, 0xc }
        );

        String out = new ObjectMapper().writeValueAsString(new JsonRpcResponse(resp, "1"));
        System.out.println(out);
    }
}
