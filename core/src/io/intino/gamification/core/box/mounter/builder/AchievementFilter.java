package io.intino.gamification.core.box.mounter.builder;

import io.intino.gamification.core.box.CoreBox;
import io.intino.gamification.core.box.events.achievement.AchievementNewState;
import io.intino.gamification.core.box.events.achievement.AchievementType;
import io.intino.gamification.core.box.events.achievement.CreateAchievement;
import io.intino.gamification.core.box.events.achievement.DeleteAchievement;
import io.intino.gamification.core.graph.Achievement;
import io.intino.gamification.core.graph.Context;
import io.intino.gamification.core.graph.Player;
import io.intino.gamification.core.graph.World;

public class AchievementFilter extends Filter {

    private final Context context;
    private Achievement achievement;
    private Player player;

    public AchievementFilter(CoreBox box, CreateAchievement event) {
        super(box);
        this.context = getContextOf(event.world(), event.type());
        this.achievement = box.graph().achievement(event.id());
    }

    public AchievementFilter(CoreBox box, DeleteAchievement event) {
        super(box);
        this.context = getContextOf(event.world(), event.type());
        if(context != null) {
            this.achievement = box.graph().achievement(context.achievements(), event.id());
        }
    }

    public AchievementFilter(CoreBox box, AchievementNewState event) {
        super(box);
        this.context = getContextOf(event.world(), event.type());
        if(context != null) {
            this.achievement = box.graph().achievement(context.achievements(), event.id());
            this.player = box.graph().player(context.players(), event.player());
        }
    }

    public Context context() {
        return context;
    }

    public Achievement achievement() {
        return achievement;
    }

    public Player player() {
        return player;
    }

    public boolean createAchievementCanMount() {
        return context != null && achievement == null;
    }

    public boolean deleteAchievementCanMount() {
        return context != null && achievement != null;
    }

    public boolean achievementNewStateCanMount() {
        return context != null && achievement != null && player != null;
    }

    private Context getContextOf(String worldId, AchievementType type) {
        if(type.equals(AchievementType.Global)) {
            return box.graph().world(worldId);
        } else if(type.equals(AchievementType.Local)) {
            World world = box.graph().world(worldId);
            if(world != null) {
                return world.match();
            }
        }
        return null;
    }
}
