package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.SerializableCollection;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public class NodeCollection<T extends Node> extends SerializableCollection implements Iterable<T> {

    private transient Object owner;
    private transient Class<T> elementType;
    private final List<T> nodes;
    private transient final Map<String, T> lookupTable;

    public NodeCollection() {
        this.nodes = new ArrayList<>();
        this.lookupTable = new HashMap<>();
    }

    public synchronized void init(Object owner, Class<T> elementType) {
        if(initialized()) throw new IllegalStateException("NodeCollection has been already initialized");
        this.owner = requireNonNull(owner);
        this.elementType = elementType;
        for(T node : nodes) {
            lookupTable.put(node.id(), node);
        }
    }

    public boolean initialized() {
        return owner != null;
    }

    public synchronized boolean add(T node) {
        if(!meetPreconditions(node)) return false;
        node.index(nodes.size());
        nodes.add(node);
        lookupTable.put(node.id(), node);
        node.setParent(owner);
        node.onInit();
        return true;
    }

    private boolean meetPreconditions(T node) {
        if(!initialized()) throw new IllegalStateException("This collection is not initialized");
        if(node == null) return false;
        if(exists(node.id())) return false;
        return node.parent() == null;
    }

    public void addAll(Collection<? extends T> nodes) {
        nodes.forEach(this::add);
    }

    public T addIfNotExists(String key) {
        if(!exists(key)) {
            try {
                Constructor<T> constructor = elementType.getDeclaredConstructor(String.class);
                constructor.setAccessible(true);
                T instance = constructor.newInstance(key);
                add(instance);
            } catch (Exception e) {
                throw new RuntimeException("Class " + elementType.getSimpleName() + " has no constructor taking 1 String");
            }
        }
        return find(key);
    }

    public T addIfNotExists(String key, Supplier<T> supplier) {
        if(!exists(key)) add(supplier.get());
        return find(key);
    }

    public synchronized void remove(T node) {
        nodes.remove(node.index());
        removeInternal(node);
    }

    public synchronized void removeAll() {
        Iterator<T> iterator = nodes.iterator();
        while(iterator.hasNext()) {
            T node = iterator.next();
            iterator.remove();
            removeInternal(node);
        }
    }

    @SuppressWarnings("all")
    public synchronized void removeIf(Predicate<T> predicate) {
        Iterator<T> iterator = nodes.iterator();
        while(iterator.hasNext()) {
            T node = iterator.next();
            if(predicate.test(node)) {
                iterator.remove();
                removeInternal(node);
            }
        }
    }

    private void removeInternal(T node) {
        lookupTable.remove(node.id());
        node.index(Integer.MIN_VALUE);
    }

    public boolean exists(String id) {
        return lookupTable.containsKey(id);
    }

    public T find(String id) {
        return lookupTable.get(id);
    }

    public T find(Predicate<T> condition) {
        return nodes.stream().filter(condition).findFirst().orElse(null);
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
        return nodes.stream();
    }

    public void sort(Comparator<T> comparator) {
        nodes.sort(comparator);
    }

    @Override
    public Iterator<T> iterator() {
        return nodes.iterator();
    }

    public boolean readOnly() {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeCollection<?> that = (NodeCollection<?>) o;
        return Objects.equals(owner, that.owner) && Objects.equals(elementType, that.elementType) && Objects.equals(nodes, that.nodes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(owner, elementType, nodes);
    }

    @Override
    public String toString() {
        return "[" + nodes.stream().map(Node::id).collect(Collectors.joining(", ")) + "]";
    }

    public NodeCollection<T> asReadOnly() {
        return new NodeCollection<>() {

            {
                init(owner, elementType);
            }

            @Override
            public boolean readOnly() {
                return true;
            }

            @Override
            public synchronized boolean add(T node) {
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
            public synchronized void remove(T node) {
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
