package io.intino.gamification.core;

import io.intino.gamification.util.Log;

import java.util.Map;
import java.util.NoSuchElementException;

public class GamificationParameters {

    private final String timeZone;
    private final String gamificationPath;
    private final String savingCron;

    public GamificationParameters(Map<String, String> params) {
        this.timeZone = getOrElse(params, "gamification_time_zone");
        this.gamificationPath = getOrElse(params, "gamification_path");
        this.savingCron = getOrElse(params, "gamification_saving_cron");
    }

    public String timeZone() {
        return timeZone;
    }

    public String gamificationPath() {
        return gamificationPath;
    }

    public String savingCron() {
        return savingCron;
    }

    private String getOrElse(Map<String, String> params, String param) {
        if(!params.containsKey(param)) {
            NoSuchElementException e = new NoSuchElementException("Falta el atributo " + param);
            Log.error(e);
            throw e;
        }
        return params.get(param);
    }
}
