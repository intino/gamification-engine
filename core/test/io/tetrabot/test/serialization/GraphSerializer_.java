package io.tetrabot.test.serialization;

import io.tetrabot.graph.TetrabotGraph;
import io.tetrabot.graph.model.*;
import io.tetrabot.serialization.TetrabotSerializer;
import io.tetrabot.util.Json;

import java.io.File;
import java.util.Random;

public class GraphSerializer_ {

    public static void main(String[] args) {

        TetrabotSerializer serializer = new TetrabotSerializer(new File("C:\\Users\\naits\\Desktop\\MonentiaDev\\externa\\temp\\datamarts\\wizard\\gamification\\graph"));

        TetrabotGraph graph = serializer.loadGraph();

        Competition competition = graph.competitions().find("Mexico");

        Season season = competition.currentSeason();

        season.playerStates().get(0).assignMission(new MissionAssignment("hola", competition.missions().get(0).id(), 1, null));

        System.out.println(competition.seasons());
    }

    public static void main3(String[] args) {

        String json = Json.toJson(MissionAssignment.class);

        Class<MissionAssignment> c = Json.fromJson(Class.class, json);

        TetrabotSerializer serializer = new TetrabotSerializer(new File("C:\\Users\\naits\\Desktop\\MonentiaDev\\externa\\temp\\datamarts\\wizard\\gamification\\graph"));

        TetrabotGraph graph = serializer.loadGraph();

        Competition competition = graph.competitions().find("Mexico");
        Season season = competition.seasons().get(0);
        Round round = season.rounds().get(0);

        System.out.println(graph);
    }

    public static void main1(String[] args) throws InterruptedException {

        //GamificationEngine engine = EngineTestHelper.getEngine();

        TetrabotGraph graph = new TetrabotGraph();

        //engine.setGraph(graph);

        for(int i = 0;i < 1;i++) {
            Competition competition = new Competition(String.valueOf(i + 1));
            graph.competitions().add(competition);

            addPlayers(competition);
            addAchievements(competition);
            addSeasons(competition);
        }

        System.gc();

        Thread.sleep(1000);

        Runtime r = Runtime.getRuntime();
        long totalMemory = r.totalMemory();
        long freeMemory = r.freeMemory();

        double usedMemory = totalMemory - freeMemory;
        usedMemory = usedMemory / 1024.0 / 1024.0;

        System.out.println("used memory = " + usedMemory + " MB");

        long start = System.currentTimeMillis();

        TetrabotSerializer serializer = new TetrabotSerializer(new File("temp/graph1"));
        serializer.prettyPrinting(true);
        serializer.save(graph);

        long time = System.currentTimeMillis() - start;

        System.out.println("Write Time: " + time + " ms");

        load(serializer, graph);
    }

    private static void load(TetrabotSerializer serializer, TetrabotGraph expected) {
        long start = System.currentTimeMillis();
        TetrabotGraph actual = serializer.loadGraph();
        long time = System.currentTimeMillis() - start;
        System.out.println("Read Time: " + time + " ms");
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
        for(int i = 0;i < 10;i++) {
            Season season = new Season(String.valueOf(i + 1));
            competition.startNewSeason(season, false);
            addMissionAssignments(season);
            addRounds(season);
        }
    }

    private static void addMissionAssignments(Season season) {
        for(Player player : season.competition().players()) {
            PlayerState state = season.playerStates().addIfNotExists(player.id());
            for(int i = 0;i < 10;i++) {
                state.assignMission(new MissionAssignment("ma" + i, "mission" + i, 1, null));
            }
        }
    }

    private static void addRounds(Season season) {
        for(int i = 0;i < 24;i++) {
            Round round = new Round(String.valueOf(i + 1));
            season.startNewRound(round);
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
