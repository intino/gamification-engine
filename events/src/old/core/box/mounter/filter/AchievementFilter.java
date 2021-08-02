package old.core.box.mounter.filter;

import old.core.box.CoreBox;
import old.core.box.events.achievement.AchievementNewState;
import old.core.model.attributes.AchievementType;
import old.core.box.events.achievement.CreateAchievement;
import old.core.box.events.achievement.DeleteAchievement;
import old.core.graph.Achievement;
import old.core.graph.Context;
import old.core.graph.Player;
import old.core.graph.World;

public class AchievementFilter extends Filter {

    private final Context context;
    private Achievement achievement;
    private Player player;

    public AchievementFilter(CoreBox box, CreateAchievement event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.description() == null) throwMissingEventAttributeException("description");
        if(event.type() == null) throwMissingEventAttributeException("type");
        if(event.eventInvolved() == null) throwMissingEventAttributeException("eventInvolved");
        if(event.maxCount() == null) throwMissingEventAttributeException("maxCount");
        if(event.maxCount() <= 0) throwInvalidAttributeValueException("maxCount", String.valueOf(event.maxCount()), "The value must be 1 or more.");

        this.context = getContextOf(event.world(), event.type());
        this.achievement = box.graph().achievement(event.id());

        canMount(context != null && achievement == null);
    }

    public AchievementFilter(CoreBox box, DeleteAchievement event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.type() == null) throwMissingEventAttributeException("type");

        this.context = getContextOf(event.world(), event.type());
        if(context != null) {
            this.achievement = box.graph().achievement(context.achievements(), event.id());
        }

        canMount(context != null && achievement != null);
    }

    public AchievementFilter(CoreBox box, AchievementNewState event) {
        super(box);

        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.state() == null) throwMissingEventAttributeException("state");
        if(event.type() == null) throwMissingEventAttributeException("type");
        if(event.player() == null) throwMissingEventAttributeException("player");

        this.context = getContextOf(event.world(), event.type());
        if(context != null) {
            this.achievement = box.graph().achievement(context.achievements(), event.id());
            this.player = box.graph().player(context.players(), event.player());
        }

        canMount(context != null && achievement != null && player != null);
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

    private Context getContextOf(String worldId, AchievementType type) {
        if(type == AchievementType.Global) {
            return box.graph().world(worldId);
        } else if(type == AchievementType.Local) {
            World world = box.graph().world(worldId);
            if(world != null) {
                return world.match();
            }
        }
        return null;
    }
}
