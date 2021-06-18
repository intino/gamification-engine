package org.example.smartbrain.control.box.adapter;

import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.action.Action;
import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.gamification.GameWorld;
import org.example.smartbrain.datahub.events.smartbrain.TaskComplete;

import java.util.Collection;
import java.util.List;

public class TaskCompleteAdapter extends Adapter<TaskComplete> {

    public TaskCompleteAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected Collection<GamificationEvent> doAdapt(TaskComplete event) {
        return List.of(new Action()
                .world(GameWorld.getId())
                .type("TaskComplete")
                .entityDest(event.patientId()));
    }
}
