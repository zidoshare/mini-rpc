package site.zido.rpc.transport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.net.URL;

public abstract class AbstractClient implements NettyClient{
    protected final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    protected InetSocketAddress localAddress;
    protected InetSocketAddress remoteAddress;

    protected URL url;

}
