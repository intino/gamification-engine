package io.tetrabot.graph;

import io.tetrabot.graph.model.Competition;
import io.tetrabot.graph.model.NodeCollection;
import io.tetrabot.serialization.TetrabotSerializer;
import io.tetrabot.util.Json;

import java.io.File;

public class TetrabotGraph {

    private final NodeCollection<Competition> competitions;

    public TetrabotGraph() {
        this.competitions = new NodeCollection<>();
        competitions.init("", Competition.class);
    }

    public NodeCollection<Competition> competitions() {
        return competitions;
    }

    public void save(File directory) {
        new TetrabotSerializer(directory).save(this);
    }

    @Override
    public String toString() {
        return Json.toJsonPretty(this);
    }
}
