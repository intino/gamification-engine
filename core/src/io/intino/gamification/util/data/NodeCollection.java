package io.intino.gamification.util.data;

import io.intino.gamification.graph.model.Node;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public interface NodeCollection<T extends Node> extends Iterable<T>, Serializable {

    void add(T node);

    void destroy(T node);

    <E extends T> E find(String id);

    boolean exists(String id);

    Stream<T> stream();

    List<T> list();

    @Override
    default Iterator<T> iterator() {
        return stream().iterator();
    }
}