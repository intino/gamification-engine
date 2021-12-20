package io.intino.gamification.graph;

import io.intino.gamification.graph.model.Competition;
import io.intino.gamification.graph.model.NodeCollection;
import io.intino.gamification.serialization.GamificationSerializer;
import io.intino.gamification.util.serializer.Json;

import java.io.File;

public class GamificationGraph {

    private final NodeCollection<Competition> competitions;

    public GamificationGraph() {
        this.competitions = new NodeCollection<>();
        competitions.init("", Competition.class);
    }

    public NodeCollection<Competition> competitions() {
        return competitions;
    }

    public void save(File directory) {
        new GamificationSerializer(directory).save(this);
    }

    @Override
    public String toString() {
        return Json.toJsonPretty(this);
    }
}
