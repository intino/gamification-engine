package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.SerializableCollection;
import io.intino.gamification.util.Log;

import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class NodeCollection<T extends Node> extends SerializableCollection implements Iterable<T> {

    private final String context;
    private final List<T> nodes;
    private final Map<String, T> lookupTable;

    public NodeCollection() {
        this("");
    }

    public NodeCollection(String context) {
        this.context = context;
        this.nodes = new ArrayList<>();
        this.lookupTable = new HashMap<>();
    }

    public synchronized void add(T node) {
        if(node == null) return;
        if(!(node instanceof Competition)) {
            if(node.parent() != null) return;
            node.parent(context);
        }
        node.index = nodes.size();
        nodes.add(node);
        lookupTable.put(node.id(), node);
        node.init();
        node.onCreate();
    }

    public void addAll(Collection<? extends T> nodes) {
        nodes.forEach(this::add);
    }

    public T addIfNotExists(String key, Supplier<T> supplier) {
        if(!exists(key)) add(supplier.get());
        return find(key);
    }

    public synchronized void destroy(T node) {
        nodes.remove(node.index);
        destroyInternal(node);
    }

    @SuppressWarnings("all")
    public synchronized void removeIf(Predicate<T> predicate) {
        Iterator<T> iterator = nodes.iterator();
        while(iterator.hasNext()) {
            T node = iterator.next();
            if(predicate.test(node)) {
                iterator.remove();
                destroyInternal(node);
            }
        }
    }

    private void destroyInternal(T node) {
        lookupTable.remove(node.id());
        node.markAsDestroyed();
        node.onDestroy();
        node.index = Integer.MIN_VALUE;
    }

    public boolean exists(String id) {
        return lookupTable.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    public <E extends T> E find(String id) {
        return (E) lookupTable.get(id);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return nodes.size();
    }

    public T first() {
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    public T last() {
        return nodes.isEmpty() ? null : nodes.get(nodes.size() - 1);
    }

    public T get(int index) {
        return nodes.get(index);
    }

    public List<T> list() {
        return Collections.unmodifiableList(nodes);
    }

    public Stream<T> stream() {
        return lookupTable.values().stream();
    }

    public void sort(Comparator<T> comparator) {
        nodes.sort(comparator);
    }

    @Override
    public Iterator<T> iterator() {
        return lookupTable.values().iterator();
    }

    public boolean readOnly() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeCollection<?> that = (NodeCollection<?>) o;
        return context.equals(that.context) && nodes.equals(that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context, nodes);
    }

    @Override
    public String toString() {
        return "NodeCollection{" +
                "context='" + context + '\'' +
                ", nodes=" + nodes +
                '}';
    }

    public NodeCollection<T> asReadOnly() {
        return new NodeCollection<>() {

            @Override
            public boolean readOnly() {
                return true;
            }

            @Override
            public synchronized void add(T node) {
                throw new UnsupportedOperationException("Collection is read only");
            }

            @Override
            public void addAll(Collection<? extends T> nodes) {
                throw new UnsupportedOperationException("Collection is read only");
            }

            @Override
            public T addIfNotExists(String key, Supplier<T> supplier) {
                throw new UnsupportedOperationException("Collection is read only");
            }

            @Override
            public synchronized void destroy(T node) {
                throw new UnsupportedOperationException("Collection is read only");
            }

            @Override
            public synchronized void removeIf(Predicate<T> predicate) {
                throw new UnsupportedOperationException("Collection is read only");
            }

            @Override
            public void sort(Comparator<T> comparator) {
                throw new UnsupportedOperationException("Collection is read only");
            }
        };
    }
}
