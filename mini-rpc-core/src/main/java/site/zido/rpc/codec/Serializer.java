package site.zido.rpc.codec;

import site.zido.rpc.core.extensions.SPI;

import java.io.IOException;

@SPI(value = "protostuff",scope = SPI.Scope.SINGLETON)
public interface Serializer {
    byte[] serialize(Object msg) throws IOException;

    <T> T deserialize(byte[] data,Class<T> type) throws IOException;
}
