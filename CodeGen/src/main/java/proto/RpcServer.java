package proto;

import jsonrpc.JsonRpcRequest;
import schema.ParamType;

public class RpcServer {
    RpcInterface rpc;

    public String execute(JsonRpcRequest req) {
        Object[]
        switch(req.getMethod()) {
            case "eth_getBlockbyNumber":
                return rpc.eth_getBlockByNumber()
                break;
            default:
                throw new UnsupportedOperationException("Method not found");
    }
}
