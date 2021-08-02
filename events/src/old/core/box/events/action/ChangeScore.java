package old.core.box.events.action;

public class ChangeScore extends AbstractAction {

    public ChangeScore() {
        super(ChangeScore.class);
    }

    public ChangeScore(io.intino.alexandria.event.Event event) {
        this(event.toMessage());
    }

    public ChangeScore(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public Integer change() {
        return getAsInt("change");
    }

    public ChangeScore change(Integer change) {
        set("change", change);
        return this;
    }
}
