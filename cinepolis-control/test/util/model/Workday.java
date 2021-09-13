package util.model;

import io.intino.gamification.graph.model.Match;
import io.intino.gamification.util.time.Crontab;

public class Workday extends Match {

    public Workday(String worldId, String id) {
        super(worldId, id);
    }

    public Workday(String worldId, String id, Crontab crontab) {
        super(worldId, id, crontab);
    }

    protected Workday(int instance, String worldId, String idBase, String id, Crontab crontab) {
        super(instance, worldId, idBase, id, crontab);
    }

    @Override
    protected void onBegin() {
        world().players().forEach(p -> p.achievementProgress("BeginTwoMatches").increment());
    }

    @Override
    protected void onEnd() {

    }

    @Override
    protected void onUpdate() {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    @Override
    protected Match newInstance(int instance, String worldId, String idBase, String id, Crontab crontab) {
        return new Workday(instance, worldId, idBase, id, crontab);
    }
}
