package io.intino.gamification.core;

import io.intino.gamification.events.EventPublisher;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GraphSerializer;
import io.intino.gamification.graph.GamificationGraph;

public class GamificationCore {

    private final Configuration configuration;
    private GamificationGraph graph;
    private GraphSerializer graphSerializer;
    private EventManager eventManager;
    private EventPublisher eventPublisher;

    public GamificationCore(GamificationParameters gamificationParameters) {
        this.configuration = new Configuration(gamificationParameters);
    }

    public void start() {
        initSubSystems();
        graphSerializer.load();
    }

    private void initSubSystems() {
        this.graph = new GamificationGraph(this);
        this.graphSerializer = new GraphSerializer(this);
        this.eventManager = new EventManager(this);
        this.eventPublisher = new EventPublisher(this);
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

    public EventPublisher eventPublisher() {
        return eventPublisher;
    }
}