package org.example.cinepolis.control.gamification.model.mission;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;

@Name("Envío del planner")
public class EnvioPlanner extends CinepolisMission {

    public EnvioPlanner() {
        super(EnvioPlanner.class.getSimpleName());
    }

    @Override
    protected void setProgressCallbacks() {

    }


}
