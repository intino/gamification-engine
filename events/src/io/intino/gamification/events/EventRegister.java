package io.intino.gamification.events;

import com.google.gson.Gson;
import io.intino.gamification.utils.file.FileHandler;
import io.intino.gamification.utils.time.Scale;
import io.intino.gamification.utils.time.TimeHandler;

import java.io.File;

public class EventRegister {

    private final String registerFolderPath;
    private final TimeHandler timeHandler;
    private final FileHandler fileHandler;
    private final Scale sealerScale;

    public EventRegister(String registerFolderPath, Scale registerScale, TimeHandler timeHandler, FileHandler fileHandler) {
        this.registerFolderPath = registerFolderPath;
        this.sealerScale = registerScale;
        this.timeHandler = timeHandler;
        this.fileHandler = fileHandler;

        fileHandler.setFolder(registerFolderPath);
    }

    public <T extends GamificationEvent> T register(T event) {
        seal(event);
        return event;
    }

    private void seal(GamificationEvent event) {
        String ts = timeHandler.truncateTo(timeHandler.currentInstant(), sealerScale).toString();

        File file = fileHandler.setFile(registerFolderPath + "/" + ts + ".ge");
        fileHandler.appendIn(file, registerOf(event));
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
}
