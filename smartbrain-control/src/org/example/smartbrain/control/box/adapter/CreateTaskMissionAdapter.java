package org.example.smartbrain.control.box.adapter;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.mission.MissionType;
import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.gamification.GameWorld;
import org.example.smartbrain.datahub.events.smartbrain.TaskAssignment;

import java.util.Collection;
import java.util.List;

public class CreateTaskMissionAdapter extends Adapter<TaskAssignment> {

    public CreateTaskMissionAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected Collection<GamificationEvent> doAdapt(TaskAssignment event) {
        return List.of(new CreateMission()
                .world(GameWorld.getId())
                .event(EventType.Action)
                .type(MissionType.Primary)
                .description(event.description())
                .maxCount(1)
                .players(List.of(event.patientId()))
        );
    }
}
