package org.example.smartbrain.tests;

import io.intino.magritte.framework.Graph;
import io.intino.magritte.framework.stores.FileSystemStore;
import io.intino.magritte.io.Stash;
import org.example.smartbrain.control.graph.ControlGraph;

import java.io.File;

public class TestGraph {

    private static final String Stashes = "Control";
    private static final String[] StartUpStashes = {Stashes};

    public static void main(String[] args) {
        ControlGraph graph = new ControlGraph(new Graph(store(new File("temp/_stashes"))).loadStashes(StartUpStashes));
        graph.create().test("Test1").save$();
        graph.create("STASH_1", "X").test("Test1").save$();

        graph.testList().forEach(System.out::println);
    }

    private static FileSystemStore store(File datamartFolder) {
        return new FileSystemStore(datamartFolder) {

            @Override
            public Stash stashFrom(String path) {
                Stash stash = super.stashFrom(path);
                if (stash != null && stash.language == null) stash.language = Stashes;
                return stash;
            }

            @Override
            public void writeStash(Stash stash, String path) {
                stash.language = stash.language == null || stash.language.isEmpty() ? Stashes : stash.language;
                super.writeStash(stash, path);
            }
        };
    }
}
