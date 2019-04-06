package site.zido.rpc.core;

import java.util.Map;

/**
 * @author zido
 */
public interface RPCResponse {
    RPCRequest getRequest();
    Exception getException();
    Object getResult();
    Map<String,String> getAttachments();
    String getAttachment(String key);
    String getAttachment(String key,String defaultValue);
    void setAttachment(String key,String value);
}
