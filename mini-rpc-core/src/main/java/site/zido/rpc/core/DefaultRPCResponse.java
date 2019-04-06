package site.zido.rpc.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DefaultRPCResponse implements RPCResponse, Serializable {
    private static final long serialVersionUID = -8256806680081833318L;
    private RPCRequest request;
    private Exception exception;
    private Object result;
    private Map<String,String> attachments;
    private long processTime;

    public void setRequest(RPCRequest request){
        this.request = request;
    }
    @Override
    public RPCRequest getRequest() {
        return request;
    }


    @Override
    public Object getResult() {
        return result;
    }

    @Override
    public Map<String, String> getAttachments() {
        return null;
    }

    @Override
    public String getAttachment(String key) {
        return attachments.get(key);
    }

    @Override
    public String getAttachment(String key, String defaultValue) {
        return attachments.getOrDefault(key,defaultValue);
    }

    @Override
    public void setAttachment(String key, String value) {
        if(attachments == null){
            this.attachments = new HashMap<>(6);
        }
        this.attachments.put(key,value);
    }

    @Override
    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getProcessTime() {
        return processTime;
    }

    public void setProcessTime(long processTime) {
        this.processTime = processTime;
    }
}
