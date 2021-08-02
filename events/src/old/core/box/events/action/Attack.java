package old.core.box.events.action;

public class Attack extends AbstractAction {

    public Attack() {
        super(Attack.class);
    }

    public Attack(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public Attack(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String entitySrc() {
        return get("entitySrc");
    }

    public Attack entitySrc(String entitySrc) {
        set("entitySrc", entitySrc);
        return this;
    }

    public Double damage() {
        return getAsDouble("damage");
    }

    public Attack damage(double damage) {
        set("damage", damage);
        return this;
    }
}
