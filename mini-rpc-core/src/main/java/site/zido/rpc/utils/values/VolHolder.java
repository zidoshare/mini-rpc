package site.zido.rpc.utils.values;

public class VolHolder<T> implements Holder<T>{
    private volatile T value;
    @Override
    public void set(T value) {
        this.value = value;
    }

    @Override
    public T get() {
        return value;
    }
}
