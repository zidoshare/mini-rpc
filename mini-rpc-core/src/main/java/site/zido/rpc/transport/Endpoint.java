package site.zido.rpc.transport;

import java.net.InetSocketAddress;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public interface Endpoint {
    InetSocketAddress getLocalAddress();

    InetSocketAddress getRemoteAddress();

    boolean open();

    boolean isAvaliable();

    boolean isClosed();

    URL getUrl();

    void close();

    void close(int timeout, TimeUnit unit);
}
