package org.example.cinepolis.control.box.actions;

import io.intino.alexandria.Json;
import io.intino.alexandria.http.spark.SparkContext;
import io.intino.gamification.api.EngineDatamart;
import org.example.cinepolis.control.box.ControlBox;

public abstract class AbstractGetAction<T> {

    protected static final String EMPTY_JSON = "{}";

    public ControlBox box;
    public SparkContext context;

    public final String execute() {
        final T response = get();
        return response == null ? EMPTY_JSON : Json.toString(response);
    }

    protected abstract T get();

    protected final EngineDatamart datamart() {
        return box.engine().datamart();
    }
}
