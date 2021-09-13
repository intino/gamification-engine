package io.intino.gamification.util.time;

import io.intino.gamification.util.Log;
import org.quartz.CronExpression;

import java.io.Serializable;
import java.text.ParseException;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

import static io.intino.gamification.util.time.TimeUtils.currentInstant;
import static io.intino.gamification.util.time.TimeUtils.dateOf;

public class Crontab implements Serializable {

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
        Date firstDate = cronExpression.getTimeAfter(dateOf(currentInstant()));
        Date secondDate = cronExpression.getTimeAfter(firstDate);
        return secondDate == null ? Type.OneTime : Type.Cyclic;
    }

    private CronExpression getCronFrom(String expression) {
        CronExpression cronExpression = null;
        try {
            cronExpression = new CronExpression(expression);
            cronExpression.setTimeZone(TimeZone.getTimeZone(TimeUtils.timeZone()));
        } catch (ParseException e) {
            Log.error(e);
        }
        return cronExpression;
    }

    public Type type() {
        return type;
    }

    public boolean matches(Instant start) {
        Instant finishDate = cronExpression.getTimeAfter(dateOf(start)).toInstant();
        return !currentInstant().isBefore(finishDate);
    }

    public enum Type {
        Undefined, OneTime, Cyclic
    }
}
