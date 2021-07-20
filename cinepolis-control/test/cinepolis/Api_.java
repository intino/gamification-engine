package cinepolis;

import org.example.cinepolis.control.box.ControlBox;
import org.example.cinepolis.control.box.actions.GetGetPlayerAction;
import org.junit.Test;

public class Api_ {

    private static final ControlBox Box = createBox();
    public static final String WORLD_ID = "Cinesa";

    private static ControlBox createBox() {
        final ControlBox box = cinepolis.Test.run();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return box;
    }

    @Test
    public void getPlayer() {

        GetGetPlayerAction action = new GetGetPlayerAction();
        action.box = Box;
        action.world = WORLD_ID;
        action.id = "empleado1";

        System.out.println(action.execute());
    }
}
