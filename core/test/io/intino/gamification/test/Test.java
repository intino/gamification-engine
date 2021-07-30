package io.intino.gamification.test;

import io.intino.gamification.GamificationEngine;

import java.time.temporal.ChronoUnit;

import static io.intino.gamification.core.box.utils.TimeUtils.Scale.Hour;

public class Test {

    public static void main(String[] args) {

        String instant1 = "2021-07-30T13:29:45.32";
        String instant2 = "2021-07-30T13:29:45";

        System.out.println(String.valueOf((int) Float.parseFloat(instant1.split("[TZ]")[1].split(":")[2])));
        System.out.println(String.valueOf((int) Float.parseFloat(instant2.split("[TZ]")[1].split(":")[2])));
        System.out.println(String.valueOf((int) (1000 * (Float.parseFloat(instant1.split("[TZ]")[1].split(":")[2]) % 1))));
        System.out.println(String.valueOf((int) (1000 * (Float.parseFloat(instant2.split("[TZ]")[1].split(":")[2]) % 1))));
    }
}


