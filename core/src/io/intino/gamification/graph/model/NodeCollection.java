package io.intino.gamification.graph.model;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NodeCollection<T extends Node> implements UnmodifiableNodeCollection<T> {

    private transient Object owner;
    private transient Class<T> elementType;
    private final List<T> nodes;

    public NodeCollection() {
        this.nodes = new ArrayList<>();
    }

    public synchronized void init(Object owner, Class<T> elementType) {
        if (initialized()) throw new IllegalStateException("NodeCollection has been already initialized");
        if (owner == null) throw new NullPointerException("Owner cannot be null");
        if (elementType == null) throw new NullPointerException("Element type cannot be null");
        this.owner = owner;
        this.elementType = elementType;
    }

    @Override
    public boolean initialized() {
        return owner != null;
    }

    public synchronized boolean add(T node) {
        if (!meetPreconditions(node)) return false;
        nodes.add(node);
        node.setParent(owner);
        node.onInit();
        return true;
    }

    private boolean meetPreconditions(T node) {
        if (!initialized()) throw new IllegalStateException("This collection is not initialized");
        if (node == null) return false;
        if (exists(node.id())) return false;
        return node.parent() == null;
    }

    public void addAll(Collection<? extends T> nodes) {
        nodes.forEach(this::add);
    }

    public T addIfNotExists(String key) {
        if (!exists(key)) {
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
        if (!exists(key)) add(supplier.get());
        return find(key);
    }

    public synchronized void remove(T node) {
        nodes.remove(node);
        removeInternal(node);
    }

    public synchronized void removeAll() {
        Iterator<T> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            T node = iterator.next();
            iterator.remove();
            removeInternal(node);
        }
    }

    @SuppressWarnings("all")
    public synchronized void removeIf(Predicate<T> predicate) {
        Iterator<T> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            T node = iterator.next();
            if (predicate.test(node)) {
                iterator.remove();
                removeInternal(node);
            }
        }
    }

    private void removeInternal(T node) {
        node.setParent(null);
    }

    @Override
    public boolean exists(String id) {
        return find(id) != null;
    }

    @Override
    public T find(String id) {
        return nodes.stream().filter(node -> node.id().equals(id)).findFirst().orElse(null);
    }

    @Override
    public T find(Predicate<T> condition) {
        return nodes.stream().filter(condition).findFirst().orElse(null);
    }

    @Override
    public boolean isEmpty() {
        return nodes.isEmpty();
    }

    @Override
    public int size() {
        return nodes.size();
    }

    @Override
    public T first() {
        return nodes.isEmpty() ? null : nodes.get(0);
    }

    @Override
    public T last() {
        return nodes.isEmpty() ? null : nodes.get(nodes.size() - 1);
    }

    @Override
    public T get(int index) {
        return nodes.get(index);
    }

    @Override
    public List<T> list() {
        return Collections.unmodifiableList(nodes);
    }

    @Override
    public Stream<T> stream() {
        return nodes.stream();
    }

    @Override
    public Iterator<T> iterator() {
        return nodes.iterator();
    }

    public void sort(Comparator<T> comparator) {
        nodes.sort(comparator);
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
}