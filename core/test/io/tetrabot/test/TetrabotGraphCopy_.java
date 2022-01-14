package io.tetrabot.test;

import io.tetrabot.graph.TetrabotGraph;
import io.tetrabot.graph.TetrabotGraphCopy;
import io.tetrabot.serialization.TetrabotSerializer;

import java.io.File;

public class TetrabotGraphCopy_ {

    public static void main(String[] args) {

        TetrabotSerializer serializer = new TetrabotSerializer(new File("C:\\Users\\naits\\Desktop\\MonentiaDev\\externa\\temp\\datamarts\\wizard\\gamification\\graph"));

        TetrabotGraph graph = serializer.loadGraph();

        long start = System.currentTimeMillis();

        TetrabotGraph copy = new TetrabotGraphCopy().copy(graph);

        long end = System.currentTimeMillis();

        System.out.println("Copy finished in " + (end - start) / 1000.0f + " seconds");
    }
}
