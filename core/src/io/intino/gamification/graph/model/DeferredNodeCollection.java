package io.intino.gamification.graph.model;

import io.intino.gamification.graph.model.Node;
import io.intino.gamification.graph.structure.NodeCollection;
import io.intino.gamification.graph.structure.SerializableCollection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Stream;

public class DeferredNodeCollection<T extends Node> extends SerializableCollection implements NodeCollection<T> {

    public static final int DEFAULT_INITIAL_CAPACITY = 1024;

    private final List<T> nodes;
    private final Queue<T> nodesToAdd;
    private final Queue<T> nodesToDestroy;
    private transient Map<String, T> lookupTable;

    public DeferredNodeCollection() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public DeferredNodeCollection(int initialCapacity) {
        super();
        this.nodes = Collections.synchronizedList(new ArrayList<>(initialCapacity));
        this.lookupTable = new ConcurrentHashMap<>(initialCapacity);
        this.nodesToAdd = new ConcurrentLinkedQueue<>();
        this.nodesToDestroy = new ConcurrentLinkedQueue<>();
    }

    @Override
    public void add(T node) {
        if(node == null) return;
        nodesToAdd.add(node);
    }

    @Override
    public void destroy(T node) {
        //TODO: Se comprueba si está destruido???
        //if(node == null || node.destroyed()) return;
        //node.destroy();
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

    public boolean sealContents() {
        final boolean hasChanged = !nodesToAdd.isEmpty() || !nodesToDestroy.isEmpty();
        while(!nodesToAdd.isEmpty()) {
            addNode(nodesToAdd.poll());
        }
        while(!nodesToDestroy.isEmpty()) {
            destroyNode(nodesToDestroy.poll());
        }
        return hasChanged;
    }

    private boolean addNode(T node) {
        synchronized (this) {
            if(exists(node.id())) return false;
            nodes.add(node);
            lookupTable.put(node.id(), node);
            node.onCreate();
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

    @Override
    protected void initTransientAttributes() {
        lookupTable = new ConcurrentHashMap<>();
        if(nodes != null) {
            for (T node : nodes) {
                lookupTable.put(node.id(), node);
            }
        }
    }
}