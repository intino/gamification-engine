package org.example.smartbrain.control.box.adapter;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.model.attributes.MissionType;
import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.gamification.GameWorld;
import org.example.smartbrain.datahub.events.smartbrain.TaskAssign;

import java.util.Collection;
import java.util.List;

public class TaskAssignAdapter extends Adapter<TaskAssign> {

    public TaskAssignAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected Collection<GamificationEvent> doAdapt(TaskAssign event) {
        return List.of(new CreateMission()
                .world(GameWorld.getId())
                .eventInvolved(EventType.Action)
                .expiration(event.ts().plusSeconds(event.durationMinutes() * 60))
                .type(MissionType.Primary)
                .maxCount(1)
                .players(List.of(event.patientId()))
        );
    }
}
