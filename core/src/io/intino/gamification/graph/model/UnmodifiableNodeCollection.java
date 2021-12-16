package io.intino.gamification.graph.model;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface UnmodifiableNodeCollection<T> extends Iterable<T> {

    boolean initialized();

    boolean exists(String id);

    T find(String id);

    T find(Predicate<T> condition);

    boolean isEmpty();

    int size();

    T first();

    T last();

    T get(int index);

    List<T> list();

    Stream<T> stream();

    Iterator<T> iterator();
}
