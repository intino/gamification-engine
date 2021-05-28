package io.intino.gamification;

import io.intino.alexandria.core.Box;
import io.intino.alexandria.core.BoxConfiguration;
import io.intino.alexandria.logger.Logger;
import io.intino.datahub.box.DataHubBox;
import io.intino.datahub.box.DataHubConfiguration;
import io.intino.datahub.graph.NessGraph;
import io.intino.magritte.framework.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Model implements Runnable {

    private final String[] args;

    private static final String[] NessStashes = {"Solution"};

    public Model(BoxConfiguration configuration) {
        args = argsFrom(configuration.args());
    }

    public void start() {
        Thread t = new Thread(this);
        t.start();
    }

    public void run() {

        DataHubConfiguration configuration = new DataHubConfiguration(args);
        NessGraph nessGraph = new Graph().loadStashes(NessStashes).as(NessGraph.class);
        loadUsers(configuration.home(), nessGraph);
        Box box = new DataHubBox(args).put(nessGraph.core$()).start();
        Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
    }

    private static void loadUsers(File workspace, NessGraph nessGraph) {
        try {
            File file = new File(workspace, "users.bin");
            if (!file.exists()) return;
            nessGraph.broker().clear().user(u -> true);
            String[] users = new String(Files.readAllBytes(file.toPath())).split("\n");
            for (String user : users) nessGraph.broker().create().user(user.split("::")[0], user.split("::")[1]);
        } catch (IOException e) {
            Logger.error(e);
        }
    }

    private String[] argsFrom(Map<String, String> args) {

        Map<String, String> modelArgs = new HashMap<>();
        modelArgs.put("home", args.get("home"));
        //modelArgs.put("backup_directory", getBackupFrom(args.get("home")));
        //modelArgs.put("datalake_directory", args.get("datalake_path"));
        //modelArgs.put("broker_directory", getBrokerFrom(args.get("home")));
        modelArgs.put("broker_port", "64000");
        modelArgs.put("broker_secondary_port", "1884");
        modelArgs.put("ui_port", "9030");

        return modelArgs.entrySet().stream().map(e -> e.getKey() + "=" + e.getValue()).toArray(String[]::new);
    }

    private String getBackupFrom(String home) {
        return home + "/backup";
    }

    private String getBrokerFrom(String home) {
        return home + "/datahub/broker";
    }
}
