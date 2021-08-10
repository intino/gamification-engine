package io.intino.gamification.util.time;

import com.google.gson.Gson;
import io.intino.gamification.util.Logger;
import org.quartz.CronExpression;

import java.io.Serializable;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

public class Crontab {

    public static Crontab undefined() {
        return new Crontab();
    }

    private final CronExpression cronExpression;
    private final Type type;

    public Crontab() {
        this.cronExpression = null;
        this.type = Type.Undefined;
    }

    public Crontab(String expression) {
        if(!CronExpression.isValidExpression(expression)) {
            this.cronExpression = null;
            this.type = Type.Undefined;
        } else {
            this.cronExpression = getCronFrom(expression);
            this.type = typeOf(this.cronExpression);
        }
    }

    private Type typeOf(CronExpression cronExpression) {
        Date firstDate = cronExpression.getTimeAfter(TimeUtils.dateOf(TimeUtils.currentInstant()));
        Date secondDate = cronExpression.getTimeAfter(firstDate);
        return secondDate == null ? Type.OneTime : Type.Cyclic;
    }

    private CronExpression getCronFrom(String expression) {
        CronExpression cronExpression = null;
        try {
            cronExpression = new CronExpression(expression);
            cronExpression.setTimeZone(TimeZone.getTimeZone(TimeUtils.timeZone()));
        } catch (ParseException e) {
            Logger.error(e);
        }
        return cronExpression;
    }

    public Type type() {
        return type;
    }

    public boolean matches(Instant start, Instant now) {
        Instant finishDate = cronExpression.getTimeAfter(TimeUtils.dateOf(start)).toInstant();
        return !now.isBefore(finishDate);
    }

    public enum Type {
        Undefined, OneTime, Cyclic
    }
}
