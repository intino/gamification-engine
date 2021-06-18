package org.example.smartbrain.control.box.adapter;

import io.intino.gamification.core.box.events.EventType;
import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.mission.CreateMission;
import io.intino.gamification.core.box.events.mission.MissionDifficulty;
import io.intino.gamification.core.box.events.mission.MissionType;
import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.gamification.GameWorld;
import org.example.smartbrain.datahub.events.smartbrain.TestBegin;

import java.util.Collection;
import java.util.List;

public class CreateTestMissionAdapter extends Adapter<TestBegin> {

    public CreateTestMissionAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected Collection<GamificationEvent> doAdapt(TestBegin event) {
        return List.of(new CreateMission()
                .world(GameWorld.getId())
                .event(EventType.Action) // ...
                .maxCount(1)
                .difficulty(MissionDifficulty.Medium)
                .type(MissionType.Secondary)
                .players(List.of(event.patientId()))
                .description(event.testName())
                .ts(event.ts()));
    }
}
