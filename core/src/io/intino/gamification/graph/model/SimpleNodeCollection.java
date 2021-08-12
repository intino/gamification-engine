package io.intino.gamification.graph.model;

import io.intino.gamification.graph.model.Node;
import io.intino.gamification.graph.property.NodeCollection;
import io.intino.gamification.graph.property.SerializableCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class SimpleNodeCollection<T extends Node> extends SerializableCollection implements NodeCollection<T> {

    private final Map<String, T> collection = new ConcurrentHashMap<>();

    @Override
    public void add(T node) {
        collection.put(node.id(), node);
        //RLP
        node.onStart();
    }

    @Override
    public void destroy(T node) {
        collection.remove(node.id());
        //RLP
        node.onDestroy();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends T> E find(String id) {
        return (E) collection.get(id);
    }

    @Override
    public boolean exists(String id) {
        return collection.containsKey(id);
    }

    @Override
    public Stream<T> stream() {
        return collection.values().stream();
    }

    @Override
    public List<T> list() {
        return new ArrayList<>(collection.values());
    }
}
