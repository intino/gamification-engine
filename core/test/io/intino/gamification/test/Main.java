package io.intino.gamification.test;

import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.model.World;
import org.quartz.CronExpression;

public class Main {

    public static void main(String[] args) {
        World world = null;
        GamificationGraph.get().worlds().destroy(world);
    }
}
