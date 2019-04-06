package site.zido.rpc.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DefualtRPCResponse implements RPCRequest, Serializable {
    private static final long serialVersionUID = -1374450943179001911L;

    private String interfaceName;
    private String methodName;
    private Object[] arguments;
    private Class<?>[] parameterTypes;
    /**
     * 请求类型
     */
    private byte type;
    private Map<String,String> attachments = new HashMap<>();

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public Map<String, String> getAttachments() {
        return attachments;
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
        attachments.put(key,value);
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
