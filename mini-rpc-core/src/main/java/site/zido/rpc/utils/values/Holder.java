package site.zido.rpc.utils.values;

public interface Holder<T> {
    void set(T value);

    T get();
}
