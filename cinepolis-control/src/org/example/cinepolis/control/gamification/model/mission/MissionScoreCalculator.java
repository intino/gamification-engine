package org.example.cinepolis.control.gamification.model.mission;

public class MissionScoreCalculator {

    public static void main(String[] args) {
        System.out.println(getMaxScore(1));
        System.out.println(getMaxScore(2));
        System.out.println(getMaxScore(3));
        System.out.println(getMaxScore(4));
    }

    private static final int MIN_PRIORITY = 4;
    private static final int BASE_SCORE = 100;

    public static int getMaxScore(int priority) {
        float factor = (float)(MIN_PRIORITY - priority + 1) / (float)MIN_PRIORITY;
        return Math.round(BASE_SCORE * factor);
    }

}
