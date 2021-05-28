package io.intino.gamification.test;

import groovy.lang.GroovyShell;
import io.intino.gamification.model.events.gamification.entity.Action;

public class Test {

    public static void main(String[] args) {
        Action action = new Action();
        String script = "println(\"Hello Groovy!\")";
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.evaluate(script);
    }
}

