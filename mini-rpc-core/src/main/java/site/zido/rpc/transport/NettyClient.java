package site.zido.rpc.transport;

import site.zido.rpc.core.RPCRequest;
import site.zido.rpc.core.RPCResponse;

public interface NettyClient extends Endpoint {

    RPCResponse invoikeAsync(RPCRequest request) throws InterruptedException;

    RPCResponse invokeSync(RPCRequest request) throws InterruptedException;

    void invokeOneWay(RPCRequest request) throws InterruptedException;
}
