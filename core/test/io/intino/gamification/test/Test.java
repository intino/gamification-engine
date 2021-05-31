package io.intino.gamification.test;

import groovy.lang.GroovyShell;
import io.intino.gamification.model.events.gamification.Action;
import io.intino.gamification.model.events.gamification.Entity;

import java.io.File;
import java.io.IOException;

public class Test {

    public static void main(String[] args) throws IOException {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.setProperty("src", new Entity().id("Entity A").maxHealth(100.0));
        groovyShell.setProperty("dest", new Entity().id("Entity B").maxHealth(70.0));
        groovyShell.evaluate(script());
    }

    private static File script() {
        return new File("C:\\Users\\naits\\Desktop\\MonentiaDev\\gamification-engine\\core\\test\\io\\intino\\gamification\\test\\Script0.groovy");
    }
}

