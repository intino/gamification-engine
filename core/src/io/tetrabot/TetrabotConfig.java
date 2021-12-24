package io.tetrabot;

import java.time.ZoneOffset;
import java.util.Objects;

public class TetrabotConfig {

    private final ZoneOffset zoneOffset;
    private final String gamificationPath;
    private final String gamificationDatamart;

    public TetrabotConfig(ZoneOffset zoneOffset, String gamificationPath, String gamificationDatamart) {
        this.zoneOffset = Objects.requireNonNull(zoneOffset);
        this.gamificationPath = Objects.requireNonNull(gamificationPath);
        this.gamificationDatamart = Objects.requireNonNull(gamificationDatamart);
    }

    public ZoneOffset zoneOffset() {
        return zoneOffset;
    }

    public String gamificationPath() {
        return gamificationPath;
    }

    public String gamificationDatamart() {
        return gamificationDatamart;
    }

    @Override
    public String toString() {
        return "GamificationConfig{" +
                "zoneOffset=" + zoneOffset +
                ", gamificationPath='" + gamificationPath + '\'' +
                ", gamificationDatamart='" + gamificationDatamart + '\'' +
                '}';
    }

    public static class Builder {

        private ZoneOffset zoneOffset = ZoneOffset.UTC;
        private String gamificationPath;
        private String gamificationDatamart;

        public TetrabotConfig build() {
            return new TetrabotConfig(zoneOffset, gamificationPath, gamificationDatamart);
        }

        public Builder zoneOffset(ZoneOffset zoneOffset) {
            this.zoneOffset = zoneOffset;
            return this;
        }

        public Builder gamificationPath(String gamificationPath) {
            this.gamificationPath = gamificationPath;
            return this;
        }

        public Builder gamificationDatamart(String gamificationDatamart) {
            this.gamificationDatamart = gamificationDatamart;
            return this;
        }
    }
}
