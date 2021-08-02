package io.intino.gamification.events;

import com.google.gson.Gson;
import io.intino.gamification.core.Core;
import io.intino.gamification.utils.file.FileUtils;
import io.intino.gamification.utils.time.Scale;
import io.intino.gamification.utils.time.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class EventRegister {

    private final String registerFolderPath;
    private final Scale registerScale;
    private final TimeUtils timeUtils;
    private final FileUtils fileUtils;

    public EventRegister(Core core) {
        this.registerFolderPath = registerFolderPath(core.configuration().gamificationPath.get());
        this.registerScale = core.configuration().eventRegisterScale.get();
        //TODO SUSCRIBIRSE??
        this.timeUtils = (TimeUtils) core.util(TimeUtils.class);
        this.fileUtils = (FileUtils) core.util(FileUtils.class);

        this.fileUtils.setFolder(this.registerFolderPath);
    }

    public <T extends GamificationEvent> T register(T event) {
        seal(event);
        return event;
    }

    public List<GamificationEvent> reflow() {
        //TODO
        return new ArrayList<>();
    }

    private void seal(GamificationEvent event) {
        String ts = timeUtils.truncateTo(timeUtils.currentInstant(), registerScale).toString();

        File file = fileUtils.setFile(registerFolderPath + "/" + ts + ".ge");
        fileUtils.appendIn(file, registerOf(event));
    }

    private String registerOf(GamificationEvent event) {
        return new StringBuilder()
                .append(headerOf(event))
                .append('\n')
                .append(new Gson().toJson(event))
                .append('\n')
                .append('\n')
                .toString();
    }

    private String headerOf(GamificationEvent event) {
        return "[" + event.getClass().getSimpleName().toUpperCase() + "]";
    }

    private String registerFolderPath(String gamificationPath) {
        return gamificationPath + "/events";
    }
}
