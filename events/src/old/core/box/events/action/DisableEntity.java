package old.core.box.events.action;

public class DisableEntity extends AbstractAction {

    public DisableEntity() {
        super(DisableEntity.class);
    }

    public DisableEntity(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DisableEntity(io.intino.alexandria.message.Message message) {
        super(message);
    }
}