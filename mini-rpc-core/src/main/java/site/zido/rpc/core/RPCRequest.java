package site.zido.rpc.core;

import java.util.Map;

public interface RPCRequest {
    String getInterfaceName();

    String getMethodName();

    Object[] getArguments();

    Class<?>[] getParameterTypes();

    Map<String,String> getAttachments();

    String getAttachment(String key);

    String getAttachment(String key, String defaultValue);

    void setAttachment(String key, String value);
}
