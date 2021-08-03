package io.intino.gamification.core;

import io.intino.gamification.api.Configuration;
import io.intino.gamification.events.EventManager;
import io.intino.gamification.graph.GraphSerializer;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.GraphViewer;

public class GamificationCore {

    private final Configuration configuration;
    private GamificationGraph graph;
    private GraphSerializer graphSerializer;
    private GraphViewer graphViewer;
    private EventManager eventManager;
    private GameLoop gameLoop;

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
        this.graphViewer = new GraphViewer(this);
        this.eventManager = new EventManager(this);
        this.gameLoop = new GameLoop(this);
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

    public GraphViewer graphViewer() {
        return this.graphViewer;
    }

    public EventManager eventManager() {
        return eventManager;
    }

    public GameLoop gameLoop() {
        return gameLoop;
    }
}