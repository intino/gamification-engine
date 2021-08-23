package io.intino.gamification.graph.structure;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public abstract class ReadOnlyProperty<T> implements Serializable {

    protected T value;
    protected transient List<Observer<T>> observers = new LinkedList<>();

    public ReadOnlyProperty() {
        this(null);
    }

    public ReadOnlyProperty(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void addObserver(Observer<T> observer) {
        if(observer == null) return;
        observers.add(observer);
    }

    @FunctionalInterface
    public interface Observer<T> {
        void onValueChanged(T oldValue, T newValue);
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        observers = new LinkedList<>();
    }
}
