package io.intino.gamification.data;

import java.util.Objects;

public class Property<T> extends ReadOnlyProperty<T> {

    public Property() {
        this(null);
    }

    public Property(T value) {
        super(value);
    }

    public void set(T value) {
        final T oldValue = this.value;
        this.value = value;
        notifyObservers(oldValue, value);
    }

    public void bind(ReadOnlyProperty<T> other) {
        other.addObserver(this::onBoundPropertyChanged);
    }

    public void unbind(ReadOnlyProperty<T> other) {
        other.removeObserver(this::onBoundPropertyChanged);
    }

    private void onBoundPropertyChanged(T oldValue, T newValue) {
        set(newValue);
    }

    private void notifyObservers(T oldValue, T newValue) {
        if(Objects.equals(oldValue, newValue)) return;
        observers.forEach(observer -> observer.onValueChanged(oldValue, newValue));
    }
}
