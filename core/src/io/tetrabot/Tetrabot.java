package io.tetrabot;

import io.tetrabot.graph.TetrabotGraph;
import io.tetrabot.util.time.TimeUtils;

import static java.util.Objects.requireNonNull;

public class Tetrabot {

    private final TetrabotConfig configuration;
    private TetrabotGraph graph;

    public Tetrabot(TetrabotConfig configuration) {
        this.configuration = configuration;
    }

    public void launch() {
        TimeUtils.zoneOffset(configuration.zoneOffset());
    }

    public TetrabotConfig configuration() {
        return this.configuration;
    }

    public TetrabotGraph graph() {
        return this.graph;
    }

    public Tetrabot setGraph(TetrabotGraph graph) {
        this.graph = requireNonNull(graph);
        return this;
    }
}
