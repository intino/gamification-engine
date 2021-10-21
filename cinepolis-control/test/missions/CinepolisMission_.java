package missions;

import org.example.cinepolis.control.gamification.model.mission.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.example.cinepolis.control.gamification.model.mission.CinepolisMission.*;
import static org.junit.Assert.assertEquals;

@RunWith(org.junit.runners.Parameterized.class)
public class CinepolisMission_ {

    private final CinepolisMission mission;

    public CinepolisMission_(CinepolisMission mission) {
        this.mission = mission;
    }

    @Parameterized.Parameters
    public static Object[][] cases() {
        return new Object[][] {
                {new AtencionTicketsOTRSMission()},
                {new EntradaTimeTrackerMission()},
                {new SalidaTimeTrackerMission()},
                {new ParosFuncionMission()},
                {new EnvioPlannerMission()},
                {new ReportesServicioMission()}
        };
    }

    @Test
    public void name() {
        String expectedName = findAttribute(mission.getClass(), Name.class);
        String actualName = mission.name();
        assertEquals(expectedName, actualName);
    }

    @Test
    public void description() {
        String expectedDescription = findAttribute(mission.getClass(), Description.class);
        String actualDescription = mission.description();
        assertEquals(expectedDescription, actualDescription);
    }

    @Test
    public void category() {
        CategoryValue expectedCategory = findAttribute(mission.getClass(), Category.class);
        CategoryValue actualCategory = mission.categoryValue();
        assertEquals(expectedCategory, actualCategory);
    }

    @Test
    public void frequency() {
        FrequencyValue expectedFrequency = findAttribute(mission.getClass(), Frequency.class);
        FrequencyValue actualFrequency = mission.frequencyValue();
        assertEquals(expectedFrequency, actualFrequency);
    }

    @Test
    public void priority() {
        PriorityValue expectedPriority = findAttribute(mission.getClass(), Priority.class);
        PriorityValue actualPriority = mission.priorityValue();
        assertEquals(expectedPriority, actualPriority);
    }
}
