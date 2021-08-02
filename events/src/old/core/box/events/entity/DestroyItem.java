package old.core.box.events.entity;

public class DestroyItem extends DestroyEntity {

    public DestroyItem() {
        super(DestroyItem.class);
    }

    public DestroyItem(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public DestroyItem(io.intino.alexandria.message.Message message) {
        super(message);
    }
}