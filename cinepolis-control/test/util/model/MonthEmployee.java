package util.model;

import io.intino.gamification.graph.model.Achievement;

public class MonthEmployee extends Achievement {

    private static final String ID = "MonthEmployee";
    private static final String DESCRIPTION = "Empleado del mes: ";
    private static final int STEPS_TO_COMPLETE = 1;

    public MonthEmployee() {
        super(_id(), _description(), STEPS_TO_COMPLETE);
    }

    public static String _id() {
        return ID + "-" + "Agosto" + "-" + "2021";
    }

    public static String _description() {
        return DESCRIPTION + "Agosto" + " de " + "2021";
    }
}
