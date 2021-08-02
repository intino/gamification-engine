package io.intino.gamification.utils.time;

import io.intino.gamification.utils.Util;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class TimeUtils extends Util {

	private final String timeZone;

	public TimeUtils(String timeZone) {
		this.timeZone = timeZone;
	}

	/* INSTANT OF ----------------------------------------------------------------------------------------------------*/

	public Instant currentInstant() {
		return getInstantOf(getZonedDateTimeOf());
	}

	public Instant getInstantOf(int year, int month, int day, int hour, int minute, int second, int millis) {
		return getInstantOf(getLocalDateTimeOf(year, month, day, hour, minute, second, millis));
	}

	public Instant getInstantOf(int year, int month, int day, int hour, int minute, int second) {
		return getInstantOf(year, month, day, hour, minute, second, 0);
	}

	public Instant getInstantOf(int year, int month, int day, int hour, int minute) {
		return getInstantOf(year, month, day, hour, minute, 0);
	}

	public Instant getInstantOf(int year, int month, int day, int hour) {
		return getInstantOf(year, month, day, hour, 0);
	}

	public Instant getInstantOf(int year, int month, int day) {
		return getInstantOf(year, month, day, 0);
	}

	public Instant getInstantOf(int year, int month) {
		return getInstantOf(year, month, 1);
	}

	public Instant getInstantOf(int year) {
		if(year < 0) return null;
		return getInstantOf(year, 1);
	}

	/* COMPONENT OF --------------------------------------------------------------------------------------------------*/

	public int secondOf(Instant instant) {
		return getLocalDateTimeOf(instant).getSecond();
	}

	public int minuteOf(Instant instant) {
		return getLocalDateTimeOf(instant).getMinute();
	}

	public int hourOf(Instant instant) {
		return getLocalDateTimeOf(instant).getHour();
	}

	public int weekDayOf(Instant instant) {
		return getLocalDateTimeOf(instant).getDayOfWeek().getValue();
	}

	public int monthDayOf(Instant instant) {
		return getLocalDateTimeOf(instant).getDayOfMonth();
	}

	public int monthOf(Instant instant) {
		return getLocalDateTimeOf(instant).getMonth().getValue();
	}

	public int yearOf(Instant instant) {
		return getLocalDateTimeOf(instant).getYear();
	}

	/* TRUNCATE ------------------------------------------------------------------------------------------------------*/

	public Instant truncateTo(Instant instant, Scale scale) {

		if(scale.equals(Scale.Millis)) return instant.truncatedTo(ChronoUnit.MILLIS);
		if(scale.equals(Scale.Second)) return instant.truncatedTo(ChronoUnit.SECONDS);
		if(scale.equals(Scale.Minute)) return instant.truncatedTo(ChronoUnit.MINUTES);
		if(scale.equals(Scale.Hour)) return instant.truncatedTo(ChronoUnit.HOURS);
		if(scale.equals(Scale.Day)) return instant.truncatedTo(ChronoUnit.DAYS);
		if(scale.equals(Scale.Week)) return truncateToWeek(instant);
		if(scale.equals(Scale.Month)) return truncateToMonth(instant);
		if(scale.equals(Scale.Year)) return truncateToYear(instant);
		return instant;
	}

	/* OFFSET INSTANT ------------------------------------------------------------------------------------------------*/

	public Instant previousInstant(Instant instant, Scale scale) {
		return previousInstant(instant, scale, 1);
	}

	public Instant previousInstant(Instant instant, Scale scale, int n) {

		if(scale.equals(Scale.Millis)) return instant.minus(n, ChronoUnit.MILLIS);
		if(scale.equals(Scale.Second)) return instant.minus(n, ChronoUnit.SECONDS);
		if(scale.equals(Scale.Minute)) return instant.minus(n, ChronoUnit.MINUTES);
		if(scale.equals(Scale.Hour)) return instant.minus(n, ChronoUnit.HOURS);
		if(scale.equals(Scale.Day)) return instant.minus(n, ChronoUnit.DAYS);
		if(scale.equals(Scale.Week)) return instant.minus(7L * n, ChronoUnit.DAYS);
		if(scale.equals(Scale.Month)) return minusMonths(instant, n);
		if(scale.equals(Scale.Year)) return minusYears(instant, n);
		return instant;
	}

	public Instant nextInstant(Instant instant, Scale scale) {
		return nextInstant(instant, scale, 1);
	}

	public Instant nextInstant(Instant instant, Scale scale, int n) {

		if(scale.equals(Scale.Millis)) return instant.plus(n, ChronoUnit.MILLIS);
		if(scale.equals(Scale.Second)) return instant.plus(n, ChronoUnit.SECONDS);
		if(scale.equals(Scale.Minute)) return instant.plus(n, ChronoUnit.MINUTES);
		if(scale.equals(Scale.Hour)) return instant.plus(n, ChronoUnit.HOURS);
		if(scale.equals(Scale.Day)) return instant.plus(n, ChronoUnit.DAYS);
		if(scale.equals(Scale.Week)) return instant.plus(7L * n, ChronoUnit.DAYS);
		if(scale.equals(Scale.Month)) return plusMonths(instant, n);
		if(scale.equals(Scale.Year)) return plusYears(instant, n);
		return instant;
	}

	/*-------------------------------------------------------------------------------------------------------*/

	public long getMillisOf(Scale scale, int amount) {

		long millis = 0;
		switch (scale) {
			case Millis:
				millis = 1;
				break;
			case Second:
				millis = 1000;
				break;
			case Minute:
				millis = 60 * 1000;
				break;
			case Hour:
				millis = 60 * 60 * 1000;
				break;
			case Day:
				millis = 24 * 60 * 60 * 1000;
				break;
			case Week:
				millis = 7 * 24 * 60 * 60 * 1000;
		}
		return millis * amount;
	}

	/*public long getInstantDiff(Instant instant1, Instant instant2, TimeUnit timeUnit) {
		if(instant1 == null || instant2 == null) return -1;
		long diffInSeconds = Math.abs(instant1.getEpochSecond() - instant2.getEpochSecond());
		return timeUnit.convert(diffInSeconds, TimeUnit.SECONDS);
	}*/

	public boolean instantIsInRange(Instant instant, Instant from, Instant to) {
		return !instant.isBefore(from) && instant.isBefore(to);
	}

	/*-------------------------------------------------------------------------------------------------------*/

	private Instant getInstantOf(ZonedDateTime zdt) {
		return Instant.parse(zdt.toString().split("Z|[+-]\\d*:")[0] + "Z");
	}

	private Instant getInstantOf(LocalDateTime localDateTime) {
		if(localDateTime == null) return null;
		return localDateTime.toInstant(ZoneOffset.UTC);
	}

	private ZonedDateTime getZonedDateTimeOf() {
		return ZonedDateTime.now(ZoneId.of(timeZone));
	}

	private LocalDateTime getLocalDateTimeOf(Instant instant) {
		return LocalDateTime.of(getYearOf(instant), getMonthOf(instant), getDayOf(instant), getHourOf(instant), getMinuteOf(instant), getSecondOf(instant), getMillisOf(instant));
	}

	private LocalDateTime getLocalDateTimeOf(int year, int month, int day, int hour, int minute, int second, int millis) {
		try {
			return LocalDateTime.of(year, month, day, hour, minute);
		} catch (Exception e) {
			return null;
		}
	}

	private int getYearOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.YEARS);
	}

	private int getMonthOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.MONTHS);
	}

	private int getDayOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.DAYS);
	}

	private int getHourOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.HOURS);
	}

	private int getMinuteOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.MINUTES);
	}

	private int getSecondOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.SECONDS);
	}

	private int getMillisOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.MILLIS);
	}

	private int getComponentOf(Instant instant, ChronoUnit unit) {

		String ret = "-1";

		if(unit.equals(ChronoUnit.YEARS)) ret = instant.toString().split("[TZ]")[0].split("-")[0];
		if(unit.equals(ChronoUnit.MONTHS)) ret = instant.toString().split("[TZ]")[0].split("-")[1];
		if(unit.equals(ChronoUnit.DAYS)) ret = instant.toString().split("[TZ]")[0].split("-")[2];
		if(unit.equals(ChronoUnit.HOURS)) ret = instant.toString().split("[TZ]")[1].split(":")[0];
		if(unit.equals(ChronoUnit.MINUTES)) ret = instant.toString().split("[TZ]")[1].split(":")[1];
		if(unit.equals(ChronoUnit.SECONDS)) ret = String.valueOf((int) Float.parseFloat(instant.toString().split("[TZ]")[1].split(":")[2]));
		if(unit.equals(ChronoUnit.MILLIS)) ret = String.valueOf((int) (1000 * (Double.parseDouble(instant.toString().split("[TZ]")[1].split(":")[2]) % 1)));

		return Integer.parseInt(ret);
	}

	private Instant truncateToWeek(Instant instant) {

		Instant dayInstant = truncateTo(instant, Scale.Day);
		int dayOfWeek = weekDayOf(dayInstant);
		return previousInstant(dayInstant, Scale.Day, dayOfWeek - 1);
	}

	private Instant truncateToMonth(Instant instant) {
		return getInstantOf(yearOf(instant), monthOf(instant));
	}

	private Instant truncateToYear(Instant instant) {
		return getInstantOf(yearOf(instant));
	}

	private Instant minusMonths(Instant instant, int nMonth) {
		return offsetMonth(instant, -nMonth);
	}

	private Instant plusMonths(Instant instant, int nMonth) {
		return offsetMonth(instant, nMonth);
	}

	private Instant offsetMonth(Instant instant, int nMonth) {
		int day = monthDayOf(instant);
		int month = monthOf(instant) + nMonth;
		int year = yearOf(instant);

		while(month < 1) {
			month += 12;
			year--;
		}

		while(month > 12) {
			month -= 12;
			year++;
		}

		day = adjustedDay(day, month, year);

		return getInstantOf(year, month, day, hourOf(instant), minuteOf(instant));
	}

	private Instant minusYears(Instant instant, int nYears) {
		return offsetYear(instant, -nYears);
	}

	private Instant plusYears(Instant instant, int nYears) {
		return offsetYear(instant, nYears);
	}

	private Instant offsetYear(Instant instant, int nYears) {
		int day = monthDayOf(instant);
		int year = yearOf(instant) + nYears;

		day = adjustedDay(day, monthOf(instant), year);

		return getInstantOf(year, monthOf(instant), day, hourOf(instant), minuteOf(instant));
	}

	private int adjustedDay(int day, int month, int year) {
		switch (month) {
			case 2:
				return Math.min(day, getFebruaryDaysOf(year));
			case 4:
			case 6:
			case 9:
			case 11:
				return Math.min(day, 30);
			default:
				return day;
		}
	}

	private int getFebruaryDaysOf(int year) {
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0) ? 29 : 28;
	}
}