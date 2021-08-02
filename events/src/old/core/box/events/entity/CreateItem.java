package old.core.box.events.entity;

public class CreateItem extends CreateEntity {

    public CreateItem() {
        super(CreateItem.class);
    }

    public CreateItem(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public CreateItem(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String player() {
        return get("player");
    }

    public CreateEntity player(String player) {
        set("player", player);
        return this;
    }
}