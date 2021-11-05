package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.SerializableCollection;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class NodeCollection<T extends Node> extends SerializableCollection implements Iterable<T> {

    private final String context;
    private final Map<String, T> collection;

    public NodeCollection() {
        this("", new TreeMap<>());
    }

    public NodeCollection(String context) {
        this(context, new TreeMap<>());
    }

    public NodeCollection(Map<String, T> collection) {
        this("", collection);
    }

    public NodeCollection(String context, Map<String, T> collection) {
        this.context = context;
        this.collection = collection;
    }

    public void add(T node) {
        if(node == null) return;
        if(!node.getClass().equals(Competition.class)) {
            if(node.parent() != null) return;
            node.parent(context);
        }
        collection.put(node.id(), node);
        node.init();
        node.onCreate();
    }

    public void addAll(Collection<? extends T> nodes) {
        nodes.forEach(this::add);
    }

    public T computeIfAbsent(String key, Function<String, T> function) {
        return collection.computeIfAbsent(key, function);
    }

    public void destroy(T node) {
        collection.remove(node.id());
        node.markAsDestroyed();
        node.onDestroy();
    }

    public boolean exists(String id) {
        return collection.containsKey(id);
    }

    @SuppressWarnings("unchecked")
    public <E extends T> E find(String id) {
        return (E) collection.get(id);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return collection.values().iterator();
    }

    public T last() {
        return collection.values().stream().skip(collection.size() - 1).findFirst().orElse(null);
    }

    public List<T> list() {
        return List.copyOf(collection.values());
    }

    @SuppressWarnings("all")
    public void removeIf(Predicate<T> predicate) {
        Iterator<Map.Entry<String, T>> iterator = collection.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String, T> entry = iterator.next();
            if(predicate.test(entry.getValue())) iterator.remove();
        }
    }

    public int size() {
        return collection.size();
    }

    public Stream<T> stream() {
        return collection.values().stream();
    }
}
