package io.intino.gamification.core.box.events;

public abstract class Action extends GamificationEvent {

    public Action(String type) {
        super(type);
    }

    public Action(io.intino.alexandria.message.Message message) {
        super(message);
    }

    public String entity() {
        return get("entity");
    }

    public Action entity(String entity) {
        set("entity", entity);
        return this;
    }

    protected String attribute() {
        return get("attribute");
    }

    protected Action attribute(String attribute) {
        set("attribute", attribute);
        return this;
    }

    protected String value() {
        return get("value");
    }

    protected Action value(String value) {
        set("value", value);
        return this;
    }

    public class CustomAction extends Action {

        public CustomAction() {
            super("Action");
        }

        public CustomAction(io.intino.alexandria.event.Event event) {
            this(event.toMessage());
        }

        public CustomAction(io.intino.alexandria.message.Message message) {
            super(message);
        }

        public CustomAction attribute(String attribute) {
            super.attribute(attribute);
            return this;
        }

        public String attribute() {
            return super.attribute();
        }

        public CustomAction value(String value) {
            super.value(value);
            return this;
        }

        public String value() {
            return value();
        }
    }

    public class ChangeLevel extends Action {

        public ChangeLevel() {
            super("ChangeLevel");
            super.attribute("level");
        }

        public ChangeLevel(io.intino.alexandria.event.Event event) {
            this(event.toMessage());
        }

        public ChangeLevel(io.intino.alexandria.message.Message message) {
            super(message);
            super.attribute("level");
        }

        public ChangeLevel level(Integer value) {
            super.value(String.valueOf(value));
            return this;
        }

        public Integer level() {
            return super.getAsInt("level");
        }
    }

    public class ChangeScore extends Action {

        public ChangeScore() {
            super("ChangeScore");
            super.attribute("score");
        }

        public ChangeScore(io.intino.alexandria.event.Event event) {
            this(event.toMessage());
        }

        public ChangeScore(io.intino.alexandria.message.Message message) {
            super(message);
            super.attribute("score");
        }

        public ChangeScore score(Integer value) {
            super.value(String.valueOf(value));
            return this;
        }

        public Integer score() {
            return super.getAsInt("score");
        }
    }

    public class ChangeHealth extends Action {

        public ChangeHealth() {
            super("ChangeHealth");
            super.attribute("health");
        }

        public ChangeHealth(io.intino.alexandria.event.Event event) {
            this(event.toMessage());
        }

        public ChangeHealth(io.intino.alexandria.message.Message message) {
            super(message);
            super.attribute("health");
        }

        public ChangeHealth health(Integer value) {
            super.value(String.valueOf(value));
            return this;
        }

        public Integer health() {
            return super.getAsInt("health");
        }
    }
}