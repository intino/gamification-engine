package io.intino.gamification.graph.structure;

import java.io.IOException;
import java.io.Serializable;

public abstract class SerializableCollection implements Serializable {

    public SerializableCollection() {
        initTransientAttributes();
    }

    //TODO: Para qué sirve???
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        initTransientAttributes();
    }

    protected final void initTransientAttributes() {}
}
