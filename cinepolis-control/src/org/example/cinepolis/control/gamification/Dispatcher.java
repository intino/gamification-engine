package org.example.cinepolis.control.gamification;

import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.datahub.events.gamification.CheckPenalties;

public class Dispatcher {

    private final ControlBox box;

    public Dispatcher(ControlBox box) {
        this.box = box;
    }

    public void dispatch(CheckPenalties event) {

    }
}
