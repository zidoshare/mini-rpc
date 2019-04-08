package site.zido.rpc.utils;

import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentHashSet<E> extends AbstractSet<E> implements Set<E>,java.io.Serializable {
    private static final long serialVersionUID = 5109693542931172595L;
    private static final Object PRESENT = new Object();
    private final ConcurrentMap<E,Object> map;
    public ConcurrentHashSet(){
        map = new ConcurrentHashMap<>();
    }
    public ConcurrentHashSet(int initialCapacity){
        map = new ConcurrentHashMap<>(initialCapacity);
    }
    @Override
    public Iterator<E> iterator() {
        return map.keySet().iterator();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return map.containsKey(o);
    }

    @Override
    public boolean add(E e) {
        return map.putIfAbsent(e,PRESENT) == null;
    }

    @Override
    public boolean remove(Object o) {
        return map.remove(o) == PRESENT;
    }

    @Override
    public void clear() {
        map.clear();
    }
}