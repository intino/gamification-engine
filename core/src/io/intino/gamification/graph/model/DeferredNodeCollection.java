package io.intino.gamification.graph.model;

import io.intino.gamification.util.data.NodeCollection;

import java.util.*;
import java.util.stream.Stream;

public class DeferredNodeCollection<T extends Node> implements NodeCollection<T> {

    private static final int INITIAL_CAPACITY = 1024;

    private final List<T> nodes = new ArrayList<>(INITIAL_CAPACITY);
    //TODO HACER TRANSIENT PARA AHORRAR MEMORIA
    private final Map<String, T> lookupTable = new HashMap<>(INITIAL_CAPACITY);
    private transient final Queue<T> nodesToAdd = new ArrayDeque<>();
    private transient final Queue<T> nodesToDestroy = new ArrayDeque<>();

    @Override
    public void add(T node) {
        if(node == null) return;
        nodesToAdd.add(node);
    }

    @Override
    public void destroy(T node) {
        if(node == null || node.destroyed()) return;
        node.markDestroyed();
        nodesToDestroy.add(node);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <E extends T> E find(String id) {
        return (E) lookupTable.get(id);
    }

    @Override
    public boolean exists(String id) {
        return lookupTable.containsKey(id);
    }

    @Override
    public Stream<T> stream() {
        return nodes.stream();
    }

    @Override
    public List<T> list() {
        return Collections.unmodifiableList(nodes);
    }

    void update() {
        while(!nodesToAdd.isEmpty()) {
            addNode(nodesToAdd.poll());
        }
        while(!nodesToDestroy.isEmpty()) {
            destroyNode(nodesToDestroy.poll());
        }
    }

    private boolean addNode(T node) {
        synchronized (this) {
            if(exists(node.id())) return false;
            nodes.add(node);
            lookupTable.put(node.id(), node);
            node.onStart();
            return true;
        }
    }

    private void destroyNode(T node) {
        synchronized (this) {
            if(!exists(node.id())) return;
            nodes.remove(node);
            lookupTable.remove(node.id());
            node.onDestroy();
        }
    }
}