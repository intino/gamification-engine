package old.core.box.mounter.filter;

import old.core.box.CoreBox;
import old.core.box.events.mission.CreateMission;
import old.core.box.events.mission.NewStateMission;
import old.core.box.utils.TimeUtils;
import old.core.graph.Match;
import old.core.graph.Mission;
import old.core.graph.Player;
import old.core.graph.World;

public class MissionFilter extends Filter {

    private final World world;
    private Match match;
    private Mission mission;
    private Player player;

    public MissionFilter(CoreBox box, CreateMission event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.difficulty() == null) throwMissingEventAttributeException("difficulty");
        if(event.type() == null) throwMissingEventAttributeException("type");
        if(event.description() == null) throwMissingEventAttributeException("description");
        if(event.eventInvolved() == null) throwMissingEventAttributeException("eventInvolved");
        if(event.expiration() != null && event.expiration().isBefore(TimeUtils.currentInstant())) throwInvalidAttributeValueException("expiration", String.valueOf(event.expiration()), "The value must be after than now.");
        if(event.maxCount() == null) throwMissingEventAttributeException("maxCount");
        if(event.maxCount() <= 0) throwInvalidAttributeValueException("maxCount", String.valueOf(event.maxCount()), "The value must be 1 or more.");

        this.world = box.graph().world(event.world());
        this.mission = box.graph().mission(event.id());
        if(world != null) {
            this.match = world.match();
        }

        canMount(world != null && match != null && mission == null);
    }

    public MissionFilter(CoreBox box, NewStateMission event) {
        super(box);

        if(event.id() == null) throwMissingEventAttributeException("id");
        if(event.world() == null) throwMissingEventAttributeException("world");
        if(event.player() == null) throwMissingEventAttributeException("player");
        if(event.state() == null) throwMissingEventAttributeException("state");

        this.world = box.graph().world(event.world());
        if(world != null) {
            this.match = world.match();
            if(match != null) {
                this.mission = box.graph().mission(match.missions(), event.id());
                if(mission.players().isEmpty() || mission.players().contains(event.player())) {
                    this.player = box.graph().player(world.players(), event.player());
                }
            }
        }


        canMount(world != null && match != null && mission != null && player != null);
    }

    public World world() {
        return world;
    }

    public Match match() {
        return match;
    }

    public Player player() {
        return player;
    }

    public Mission mission() {
        return mission;
    }
}
