package io.intino.gamification.util.time;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class TimeUtils {

	private static String timeZone = "Atlantic/Canary";

	public static void timeZone(String timeZone) {
		TimeUtils.timeZone = timeZone;
	}

	public static String timeZone() {
		return timeZone;
	}

	/* INSTANT OF ----------------------------------------------------------------------------------------------------*/

	public static Instant now() {
		return getInstantOf(getZonedDateTimeOf());
	}

	public static LocalDateTime localDateTimeNow() {
		return getLocalDateTimeOf(now());
	}

	public static Date dateOf(Instant instant) {
		return new Date(instant.toEpochMilli());
	}

	public static Instant getInstantOf(int year, int month, int day, int hour, int minute, int second, int millis) {
		return getInstantOf(getLocalDateTimeOf(year, month, day, hour, minute, second, millis));
	}

	public static Instant getInstantOf(int year, int month, int day, int hour, int minute, int second) {
		return getInstantOf(year, month, day, hour, minute, second, 0);
	}

	public static Instant getInstantOf(int year, int month, int day, int hour, int minute) {
		return getInstantOf(year, month, day, hour, minute, 0);
	}

	public static Instant getInstantOf(int year, int month, int day, int hour) {
		return getInstantOf(year, month, day, hour, 0);
	}

	public static Instant getInstantOf(int year, int month, int day) {
		return getInstantOf(year, month, day, 0);
	}

	public static Instant getInstantOf(int year, int month) {
		return getInstantOf(year, month, 1);
	}

	public static Instant getInstantOf(int year) {
		if(year < 0) return null;
		return getInstantOf(year, 1);
	}

	/* COMPONENT OF --------------------------------------------------------------------------------------------------*/

	public static int secondOf(Instant instant) {
		return getLocalDateTimeOf(instant).getSecond();
	}

	public static int minuteOf(Instant instant) {
		return getLocalDateTimeOf(instant).getMinute();
	}

	public static int hourOf(Instant instant) {
		return getLocalDateTimeOf(instant).getHour();
	}

	public static int weekDayOf(Instant instant) {
		return getLocalDateTimeOf(instant).getDayOfWeek().getValue();
	}

	public static int monthDayOf(Instant instant) {
		return getLocalDateTimeOf(instant).getDayOfMonth();
	}

	public static int monthOf(Instant instant) {
		return getLocalDateTimeOf(instant).getMonth().getValue();
	}

	public static int yearOf(Instant instant) {
		return getLocalDateTimeOf(instant).getYear();
	}

	/* TRUNCATE ------------------------------------------------------------------------------------------------------*/

	public static Instant truncateTo(Instant instant, Scale scale) {

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

	public static Instant previousInstant(Instant instant, Scale scale) {
		return previousInstant(instant, scale, 1);
	}

	public static Instant previousInstant(Instant instant, Scale scale, int n) {

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

	public static Instant nextInstant(Instant instant, Scale scale) {
		return nextInstant(instant, scale, 1);
	}

	public static Instant nextInstant(Instant instant, Scale scale, int n) {

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

	public static long getMillisOf(Scale scale, int amount) {

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
				break;
			default:
				throw new IllegalArgumentException("La escala no está permitida");
		}
		return millis * amount;
	}

	public static long getInstantDiff(Instant instant1, Instant instant2, Scale scale, boolean includeCurrent) {

		if(instant1 == null || instant2 == null) return -1;

		float diff;

		switch (scale) {
			case Week:
			case Day:
			case Hour:
			case Minute:
				diff = (instant2.toEpochMilli() - instant1.toEpochMilli()) / (float) getMillisOf(scale, 1);
				break;
			default:
				throw new IllegalArgumentException("La escala no está permitida");
		}

		if(diff % 1 == 0 || includeCurrent) {
			return (long) Math.ceil(diff);
		} else {
			return (long) (Math.ceil(diff) - 1);
		}
	}

	public static boolean instantIsInRange(Instant instant, Instant from, Instant to) {
		return !instant.isBefore(from) && instant.isBefore(to);
	}

	/*-------------------------------------------------------------------------------------------------------*/

	private static Instant getInstantOf(ZonedDateTime zdt) {
		return Instant.parse(zdt.toString().split("Z|[+-]\\d*:")[0] + "Z");
	}

	private static Instant getInstantOf(LocalDateTime localDateTime) {
		if(localDateTime == null) return null;
		return localDateTime.toInstant(ZoneOffset.UTC);
	}

	private static ZonedDateTime getZonedDateTimeOf() {
		return ZonedDateTime.now(ZoneId.of(timeZone));
	}

	private static LocalDateTime getLocalDateTimeOf(Instant instant) {
		return LocalDateTime.of(getYearOf(instant), getMonthOf(instant), getDayOf(instant), getHourOf(instant), getMinuteOf(instant), getSecondOf(instant), getMillisOf(instant));
	}

	private static LocalDateTime getLocalDateTimeOf(int year, int month, int day, int hour, int minute, int second, int millis) {
		try {
			return LocalDateTime.of(year, month, day, hour, minute, second, 1000000 * millis);
		} catch (Exception e) {
			return null;
		}
	}

	private static int getYearOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.YEARS);
	}

	private static int getMonthOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.MONTHS);
	}

	private static int getDayOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.DAYS);
	}

	private static int getHourOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.HOURS);
	}

	private static int getMinuteOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.MINUTES);
	}

	private static int getSecondOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.SECONDS);
	}

	private static int getMillisOf(Instant instant) {
		return getComponentOf(instant, ChronoUnit.MILLIS);
	}

	private static int getComponentOf(Instant instant, ChronoUnit unit) {

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

	private static Instant truncateToWeek(Instant instant) {

		Instant dayInstant = truncateTo(instant, Scale.Day);
		int dayOfWeek = weekDayOf(dayInstant);
		return previousInstant(dayInstant, Scale.Day, dayOfWeek - 1);
	}

	private static Instant truncateToMonth(Instant instant) {
		return getInstantOf(yearOf(instant), monthOf(instant));
	}

	private static Instant truncateToYear(Instant instant) {
		return getInstantOf(yearOf(instant));
	}

	private static Instant minusMonths(Instant instant, int nMonth) {
		return offsetMonth(instant, -nMonth);
	}

	private static Instant plusMonths(Instant instant, int nMonth) {
		return offsetMonth(instant, nMonth);
	}

	private static Instant offsetMonth(Instant instant, int nMonth) {
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

	private static Instant minusYears(Instant instant, int nYears) {
		return offsetYear(instant, -nYears);
	}

	private static Instant plusYears(Instant instant, int nYears) {
		return offsetYear(instant, nYears);
	}

	private static Instant offsetYear(Instant instant, int nYears) {
		int day = monthDayOf(instant);
		int year = yearOf(instant) + nYears;

		day = adjustedDay(day, monthOf(instant), year);

		return getInstantOf(year, monthOf(instant), day, hourOf(instant), minuteOf(instant));
	}

	private static int adjustedDay(int day, int month, int year) {
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

	private static int getFebruaryDaysOf(int year) {
		return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0) ? 29 : 28;
	}

	public static Instant toInstant(LocalDate date) {
		return getInstantOf(date.getYear(), date.getMonth().getValue(), date.getDayOfMonth());
	}
}