package old.core.box.events.action;

public class Action extends AbstractAction {

    public Action() {
        super(Action.class);
    }

    public Action(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public Action(io.intino.alexandria.message.Message message) {
        super(message);
    }
}