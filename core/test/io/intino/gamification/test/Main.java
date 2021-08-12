package io.intino.gamification.test;

import org.quartz.CronExpression;

public class Main {

    public static void main(String[] args) {
        CronExpression.isValidExpression("* * * * * *");
    }
}
