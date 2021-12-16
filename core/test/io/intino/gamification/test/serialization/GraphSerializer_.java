package io.intino.gamification.test.serialization;

import io.intino.gamification.GamificationEngine;
import io.intino.gamification.graph.GamificationGraph;
import io.intino.gamification.graph.GamificationGraphSerializer;
import io.intino.gamification.graph.model.*;
import io.intino.gamification.graph.structure.Fact;
import io.intino.gamification.test.util.EngineTestHelper;
import io.intino.gamification.util.serializer.Json;

import java.io.File;
import java.util.Random;

public class GraphSerializer_ {

    public static void main(String[] args) {

        GamificationEngine engine = EngineTestHelper.getEngine();

        GamificationGraph graph = new GamificationGraph();

        engine.setGraph(graph);

        Competition competition = new Competition("1");
        graph.competitions().add(competition);

        addPlayers(competition);
        addAchievements(competition);
        addSeasons(competition);

        System.out.println(competition.seasons().get(0));

        GamificationGraphSerializer serializer = new GamificationGraphSerializer(new File("temp/graph1"));
        serializer.save(competition, true);

        load(serializer, graph);
    }

    private static void load(GamificationGraphSerializer serializer, GamificationGraph expected) {
        GamificationGraph actual = serializer.loadGraph();
        System.out.println(Json.toJsonPretty(actual));
    }

    private static void addAchievements(Competition competition) {
        for(int i = 0;i < 10;i++) {
            competition.achievements().add(new Achievement("achievement" + i, Achievement.Type.Bonus));
        }
    }

    private static void addPlayers(Competition competition) {
        for(int i = 0;i < 100;i++) {
            competition.players().add(new Player("player" + i));
        }
    }

    private static void addSeasons(Competition competition) {
        for(int i = 0;i < 3;i++) {
            Season season = new Season(String.valueOf(i + 1));
            competition.seasons().add(season);
            addMissionAssignments(season);
            addRounds(season);
        }
    }

    private static void addMissionAssignments(Season season) {
        for(Player player : season.competition().players()) {
            PlayerState state = season.playerStates().find(player.id());
            for(int i = 0;i < 5;i++) {
                state.assignMission(new MissionAssignment("ma" + i, "mission" + i, 1, null));
            }
        }
    }

    private static void addRounds(Season season) {
        for(int i = 0;i < 4;i++) {
            Round round = new Round(String.valueOf(i + 1));
            season.rounds().add(round);
            addMatches(round);
        }
    }

    private static void addMatches(Round round) {
        for(Player player : round.season().competition().players()) {
            Match match = round.matches().addIfNotExists(player.id());
            Random random = new Random();
            int count = random.nextInt(20);
            for(int i = 0;i < count;i++) {
                match.addFact(new Fact()
                        .name("f" + i).type(Fact.StandardTypes.values().get(random.nextInt(Fact.StandardTypes.values().size())))
                        .points(random.nextInt(100)));
            }
        }
    }
}
