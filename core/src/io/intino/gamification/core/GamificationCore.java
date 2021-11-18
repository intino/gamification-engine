package io.intino.gamification.core;

import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GraphSerializer;
import io.intino.gamification.graph.GamificationGraph;

public class GamificationCore {

    private final Configuration configuration;
    private GamificationGraph graph;
    private GraphSerializer graphSerializer;
    private EventManager eventManager;

    public GamificationCore(GamificationParameters gamificationParameters) {
        this.configuration = new Configuration(gamificationParameters);
    }

    public void start() {
        initSubSystems();
        //graphSerializer.load(); TODO
    }

    private void initSubSystems() {
        this.graph = new GamificationGraph(this);
        this.graphSerializer = new GraphSerializer(this);
        this.eventManager = new EventManager();
    }

    public Configuration configuration() {
        return this.configuration;
    }

    public GamificationGraph graph() {
        return graph;
    }

    public GraphSerializer graphSerializer() {
        return this.graphSerializer;
    }

    public EventManager eventManager() {
        return eventManager;
    }
}