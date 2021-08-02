package io.intino.gamification.core.launcher;

import java.util.Map;

public class ParameterProcessor {

    private final String timeZone;
    private final String frameRate;
    private final String gamificationPath;
    private final String eventRegisterScale;

    public ParameterProcessor(Map<String, String> params) {
        this.timeZone = getOrElse(params, "gamification_time_zone", "Atlantic/Canary");
        this.frameRate = getOrElse(params, "gamification_frame_rate", "60");
        this.gamificationPath = getOrElse(params, "gamification_path", ".temp/gamification");
        this.eventRegisterScale = getOrElse(params, "gamification_event_register_scale", "Day");
    }

    public String timeZone() {
        return timeZone;
    }

    public String frameRate() {
        return frameRate;
    }

    public String gamificationPath() {
        return gamificationPath;
    }

    public String eventRegisterScale() {
        return eventRegisterScale;
    }

    private String getOrElse(Map<String, String> params, String param, String defaultValue) {
        return params.containsValue(param) ? params.get(param) : defaultValue;
    }
}
