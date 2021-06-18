package org.example.smartbrain.control.box.adapter;

import io.intino.gamification.core.box.events.GamificationEvent;
import io.intino.gamification.core.box.events.entity.CreatePlayer;
import org.example.smartbrain.control.box.ControlBox;
import org.example.smartbrain.control.gamification.GameWorld;
import org.example.smartbrain.datahub.events.smartbrain.CreateTherapist;

import java.util.Collection;
import java.util.List;

public class CreateTherapistPlayerAdapter extends Adapter<CreateTherapist> {

    public CreateTherapistPlayerAdapter(ControlBox box) {
        super(box);
    }

    @Override
    protected Collection<GamificationEvent> doAdapt(CreateTherapist event) {
        return List.of(createPlayer(event));
    }

    private CreatePlayer createPlayer(CreateTherapist event) {
        CreatePlayer player = new CreatePlayer();
        player.id(event.dni());
        player.world(GameWorld.getId());
        player.health(100);
        player.groups(List.of("Therapists"));
        return player;
    }
}
