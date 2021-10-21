package io.intino.gamification.test.util.model;

import io.intino.gamification.graph.model.Achievement;

public class MonthEmployee extends Achievement {

    private static final String ID = "MonthEmployee";
    private static final String DESCRIPTION = "Empleado del mes: ";
    private static final int STEPS_TO_COMPLETE = 1;

    public MonthEmployee() {
        super(_id(), _description());
    }

    private static String _id() {
        return ID + "-" + "Agosto" + "-" + "2021";
    }

    private static String _description() {
        return DESCRIPTION + "Agosto" + " de " + "2021";
    }
}
