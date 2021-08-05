package io.intino.gamification.test;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.graph.model.Mission;
import io.intino.gamification.graph.model.Player;
import io.intino.gamification.graph.model.World;
import io.intino.gamification.util.time.Crontab;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        CustomMatch match = new CustomMatch("", "", new ArrayList<>());
    }

    public static class CustomMatch extends Match {

        public CustomMatch(String world, String id, List<Mission> missions) {
            super(world, id, missions);
        }

        public CustomMatch(String world, String id, List<Mission> missions, Crontab crontab) {
            super(world, id, missions, crontab);
        }
    }
}
