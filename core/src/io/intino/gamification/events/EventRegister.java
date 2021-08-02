package io.intino.gamification.events;

import com.google.gson.Gson;
import io.intino.gamification.core.Core;
import io.intino.gamification.utils.event.EventUtils;
import io.intino.gamification.utils.file.FileUtils;
import io.intino.gamification.utils.time.Scale;
import io.intino.gamification.utils.time.TimeUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EventRegister {

    private final Core core;

    private final String registerFolderPath;
    private final Scale registerScale;
    private final TimeUtils timeUtils;
    private final FileUtils fileUtils;
    private final EventUtils eventUtils;

    public EventRegister(Core core) {
        this.core = core;

        this.registerFolderPath = registerFolderPath(core.configuration().gamificationPath.get());
        this.registerScale = core.configuration().eventRegisterScale.get();
        //TODO SUSCRIBIRSE??
        this.timeUtils = (TimeUtils) core.util(TimeUtils.class);
        this.fileUtils = (FileUtils) core.util(FileUtils.class);
        this.eventUtils = (EventUtils) core.util(EventUtils.class);

        this.fileUtils.setFolder(this.registerFolderPath);
    }

    public <T extends GamificationEvent> T register(T event) {
        seal(event);
        return event;
    }

    public void reflow() {
        File folder = new File(registerFolderPath);
        Arrays.stream(Objects.requireNonNull(folder.listFiles())).forEach(this::reflow);
    }

    private void reflow(File file) {
        List<String> lines = fileUtils.read(file);
        for (int i = 0; i < lines.size(); i+=3) {
            reflow(eventUtils.classOf(typeOf(lines.get(i))), lines.get(i + 1));
        }
    }

    private void reflow(Class<? extends GamificationEvent> type, String jsonEvent) {
        core.mounters().mount(type, new Gson().fromJson(jsonEvent, type));
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

    private String typeOf(String header) {
        return String.valueOf(header.charAt(1)).toUpperCase() + header.substring(2, header.length() - 1);
    }

    private String registerFolderPath(String gamificationPath) {
        return gamificationPath + "/events";
    }
}
