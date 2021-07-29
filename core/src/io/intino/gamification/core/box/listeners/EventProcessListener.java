package io.intino.gamification.core.box.listeners;

import io.intino.gamification.core.model.Component;

public interface EventProcessListener<S extends Component> {

    void onProcess(S component);

    default void process(Component component) {
        onProcess((S) component);
    }
}