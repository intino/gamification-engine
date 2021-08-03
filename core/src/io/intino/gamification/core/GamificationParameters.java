package io.intino.gamification.core;

import java.util.Map;

public class GamificationParameters {

    private final String timeZone;
    private final String gamificationPath;

    public GamificationParameters(Map<String, String> params) {
        this.timeZone = getOrElse(params, "gamification_time_zone", "Atlantic/Canary");
        this.gamificationPath = getOrElse(params, "gamification_path", ".temp/gamification");
    }

    public String timeZone() {
        return timeZone;
    }

    public String gamificationPath() {
        return gamificationPath;
    }

    private String getOrElse(Map<String, String> params, String param, String defaultValue) {
        return params.containsValue(param) ? params.get(param) : defaultValue;
    }
}
