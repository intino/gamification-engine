package old.core.box.mounter.filter;

import old.core.box.CoreBox;
import io.intino.gamification.core.exception.InvalidAttributeValueException;
import io.intino.gamification.core.exception.MissingEventAttributeException;

public abstract class Filter {

    protected final CoreBox box;
    protected boolean canMount = true;

    public Filter(CoreBox box) {
        this.box = box;
    }

    public boolean canMount() {
        return canMount;
    }

    protected void canMount(boolean canMount) {
        this.canMount = this.canMount && canMount;
    }

    protected void throwMissingEventAttributeException(String attribute) {
        canMount(false);
        throw new MissingEventAttributeException(attribute);
    }

    protected void throwInvalidAttributeValueException(String attribute, String value, String attributeValue) {
        canMount(false);
        throw new InvalidAttributeValueException(attribute, value, attributeValue);
    }
}
