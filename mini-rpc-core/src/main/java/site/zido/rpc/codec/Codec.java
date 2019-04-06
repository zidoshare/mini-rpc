package site.zido.rpc.codec;

import site.zido.rpc.core.extensions.SPI;

import java.io.IOException;
import java.net.URL;

@SPI
public interface Codec {
    byte[] encode(URL url,Object message) throws IOException;

    Object decode(URL url,byte messageType,byte[] data) throws IOException;
}
