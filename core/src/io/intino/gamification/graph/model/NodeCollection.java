package io.intino.gamification.graph.model;

import io.intino.gamification.graph.structure.SerializableCollection;

import java.util.*;
import java.util.stream.Stream;

public class NodeCollection<T extends Node> extends SerializableCollection implements Iterable<T> {

    private final Map<String, T> collection = new TreeMap<>();

    public void add(T node) {
        collection.put(node.id(), node);
        node.onCreate();
    }

    public void addAll(Collection<T> nodes) {
        for (T node : nodes) {
            add(node);
        }
    }

    public void destroy(T node) {
        collection.remove(node.id());
        node.onDestroy();
    }

    @SuppressWarnings("unchecked")
    public <E extends T> E find(String id) {
        return (E) collection.get(id);
    }

    public boolean exists(String id) {
        return collection.containsKey(id);
    }

    public Stream<T> stream() {
        return collection.values().stream();
    }

    public List<T> list() {
        return List.copyOf(collection.values());
    }

    @Override
    public Iterator<T> iterator() {
        return collection.values().iterator();
    }
}
