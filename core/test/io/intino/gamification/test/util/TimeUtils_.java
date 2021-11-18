package io.intino.gamification.test.util;

import io.intino.gamification.util.time.Scale;
import org.junit.Test;

import java.time.Instant;

import static io.intino.gamification.util.time.TimeUtils.*;
import static org.junit.Assert.*;

public class TimeUtils_ {

	@Test
	public void instantOf() {

		assertEquals(getInstantOf(2020), Instant.parse("2020-01-01T00:00:00Z"));
		assertEquals(getInstantOf(2020, 12), Instant.parse("2020-12-01T00:00:00Z"));
		assertEquals(getInstantOf(2020, 12, 31), Instant.parse("2020-12-31T00:00:00Z"));
		assertEquals(getInstantOf(2020, 12, 16, 8), Instant.parse("2020-12-16T08:00:00Z"));
		assertEquals(getInstantOf(2020, 12, 16, 8, 16), Instant.parse("2020-12-16T08:16:00Z"));
		assertEquals(getInstantOf(2020, 2, 28), Instant.parse("2020-02-28T00:00:00Z"));
		assertEquals(getInstantOf(2020, 2, 29), Instant.parse("2020-02-29T00:00:00Z"));
		assertEquals(getInstantOf(2019, 2, 28), Instant.parse("2019-02-28T00:00:00Z"));

		assertNull(getInstantOf(-2020));
		assertNull(getInstantOf(2020, -12));
		assertNull(getInstantOf(2020, 0));
		assertNull(getInstantOf(2020, 13));
		assertNull(getInstantOf(2020, 12, -16));
		assertNull(getInstantOf(2020, 12, 0));
		assertNull(getInstantOf(2020, 12, 32));
		assertNull(getInstantOf(2020, 11, -16));
		assertNull(getInstantOf(2020, 11, 0));
		assertNull(getInstantOf(2020, 11, 31));
		assertNull(getInstantOf(2020, 2, 30));
		assertNull(getInstantOf(2019, 2, 29));
		assertNull(getInstantOf(2019, 2, 30));
		assertNull(getInstantOf(2020, 12, 16, -1));
		assertNull(getInstantOf(2020, 12, 16, 25));
		assertNull(getInstantOf(2020, 12, 16, 8, -1));
		assertNull(getInstantOf(2020, 12, 16, 8, 60));
	}

	@Test
	public void componentOf() {

		Instant instant = getInstantOf(2020, 12, 16, 8, 16);

		assertEquals(minuteOf(instant), 16);
		assertEquals(hourOf(instant), 8);
		assertEquals(monthOf(instant), 12);
		assertEquals(yearOf(instant), 2020);
	}

	@Test
	public void truncateOf() {

		Instant instant = getInstantOf(2020, 12, 16, 8, 16);

		assertEquals(truncateTo(instant, Scale.Hour), getInstantOf(2020, 12, 16, 8));
		assertEquals(truncateTo(instant, Scale.Day), getInstantOf(2020, 12, 16));
		assertEquals(truncateTo(instant, Scale.Week), getInstantOf(2020, 12, 14));
		assertEquals(truncateTo(instant, Scale.Month), getInstantOf(2020, 12));
		assertEquals(truncateTo(instant, Scale.Year), getInstantOf(2020));
	}

	@Test
	public void offsetInstant() {

		Instant instant = getInstantOf(2020, 12, 16, 8, 16);
		Instant previousInstant = getInstantOf(2021, 1, 1, 0, 1);
		Instant nextInstant = getInstantOf(2020, 12, 31, 23, 59);

		assertEquals(previousInstant(instant, Scale.Hour), getInstantOf(2020, 12, 16, 7, 16));
		assertEquals(previousInstant(instant, Scale.Day), getInstantOf(2020, 12, 15, 8, 16));
		assertEquals(previousInstant(instant, Scale.Week), getInstantOf(2020, 12, 9, 8, 16));
		assertEquals(previousInstant(instant, Scale.Month), getInstantOf(2020, 11, 16, 8, 16));
		assertEquals(previousInstant(instant, Scale.Year), getInstantOf(2019, 12, 16, 8, 16));
		assertEquals(previousInstant(instant, Scale.Hour, 2), getInstantOf(2020, 12, 16, 6, 16));
		assertEquals(previousInstant(instant, Scale.Day, 2), getInstantOf(2020, 12, 14, 8, 16));
		assertEquals(previousInstant(instant, Scale.Week, 2), getInstantOf(2020, 12, 2, 8, 16));
		assertEquals(previousInstant(instant, Scale.Month, 2), getInstantOf(2020, 10, 16, 8, 16));
		assertEquals(previousInstant(instant, Scale.Year, 2), getInstantOf(2018, 12, 16, 8, 16));
		assertEquals(previousInstant(previousInstant, Scale.Hour), getInstantOf(2020, 12, 31, 23, 1));
		assertEquals(previousInstant(previousInstant, Scale.Day), getInstantOf(2020, 12, 31, 0, 1));
		assertEquals(previousInstant(previousInstant, Scale.Week), getInstantOf(2020, 12, 25, 0, 1));
		assertEquals(previousInstant(previousInstant, Scale.Month), getInstantOf(2020, 12, 1, 0, 1));
		assertEquals(previousInstant(previousInstant, Scale.Year), getInstantOf(2020, 1, 1, 0, 1));

		assertEquals(nextInstant(instant, Scale.Hour), getInstantOf(2020, 12, 16, 9, 16));
		assertEquals(nextInstant(instant, Scale.Day), getInstantOf(2020, 12, 17, 8, 16));
		assertEquals(nextInstant(instant, Scale.Week), getInstantOf(2020, 12, 23, 8, 16));
		assertEquals(nextInstant(instant, Scale.Month), getInstantOf(2021, 1, 16, 8, 16));
		assertEquals(nextInstant(instant, Scale.Year), getInstantOf(2021, 12, 16, 8, 16));
		assertEquals(nextInstant(instant, Scale.Hour, 2), getInstantOf(2020, 12, 16, 10, 16));
		assertEquals(nextInstant(instant, Scale.Day, 2), getInstantOf(2020, 12, 18, 8, 16));
		assertEquals(nextInstant(instant, Scale.Week, 2), getInstantOf(2020, 12, 30, 8, 16));
		assertEquals(nextInstant(instant, Scale.Month, 2), getInstantOf(2021, 2, 16, 8, 16));
		assertEquals(nextInstant(instant, Scale.Year, 2), getInstantOf(2022, 12, 16, 8, 16));
		assertEquals(nextInstant(nextInstant, Scale.Hour), getInstantOf(2021, 1, 1, 0, 59));
		assertEquals(nextInstant(nextInstant, Scale.Day), getInstantOf(2021, 1, 1, 23, 59));
		assertEquals(nextInstant(nextInstant, Scale.Week), getInstantOf(2021, 1, 7, 23, 59));
		assertEquals(nextInstant(nextInstant, Scale.Month), getInstantOf(2021, 1, 31, 23, 59));
		assertEquals(nextInstant(nextInstant, Scale.Year), getInstantOf(2021, 12, 31, 23, 59));

		assertEquals(previousInstant(getInstantOf(2020, 4, 30), Scale.Month), getInstantOf(2020, 3, 30));
		assertEquals(previousInstant(getInstantOf(2020, 5, 31), Scale.Month), getInstantOf(2020, 4, 30));
		assertEquals(nextInstant(getInstantOf(2020, 4, 30), Scale.Month), getInstantOf(2020, 5, 30));
		assertEquals(nextInstant(getInstantOf(2020, 5, 31), Scale.Month), getInstantOf(2020, 6, 30));
		assertEquals(previousInstant(getInstantOf(2020, 2, 29), Scale.Year), getInstantOf(2019, 2, 28));
		assertEquals(nextInstant(getInstantOf(2020, 2, 29), Scale.Year), getInstantOf(2021, 2, 28));
	}

	@Test
	public void instantDiff() {

		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Week, true), 53);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Week, false), 52);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Day, true), 365);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Day, false), 365);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Hour, true), 8760);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Hour, false), 8760);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Minute, true), 525600);
		assertEquals(getInstantDiff(instant1Year(), instant2(), Scale.Minute, false), 525600);

		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Week, true), 9);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Week, false), 8);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Day, true), 61);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Day, false), 61);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Hour, true), 1464);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Hour, false), 1464);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Minute, true), 87840);
		assertEquals(getInstantDiff(instant1Month(), instant2(), Scale.Minute, false), 87840);

		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Week, true), 7);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Week, false), 6);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Day, true), 44);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Day, false), 44);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Hour, true), 1056);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Hour, false), 1056);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Minute, true), 63360);
		assertEquals(getInstantDiff(instant1Day(), instant2(), Scale.Minute, false), 63360);

		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Week, true), 7);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Week, false), 6);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Day, true), 44);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Day, false), 43);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Hour, true), 1044);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Hour, false), 1044);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Minute, true), 62640);
		assertEquals(getInstantDiff(instant1Hour(), instant2(), Scale.Minute, false), 62640);

		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Week, true), 7);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Week, false), 6);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Day, true), 44);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Day, false), 43);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Hour, true), 1044);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Hour, false), 1043);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Minute, true), 62585);
		assertEquals(getInstantDiff(instant1Minute(), instant2(), Scale.Minute, false), 62585);

		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Week, true), 7);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Week, false), 6);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Day, true), 44);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Day, false), 43);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Hour, true), 1044);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Hour, false), 1043);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Minute, true), 62585);
		assertEquals(getInstantDiff(instant1Second(), instant2(), Scale.Minute, false), 62584);

		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Week, true), 7);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Week, false), 6);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Day, true), 44);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Day, false), 43);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Hour, true), 1044);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Hour, false), 1043);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Minute, true), 62585);
		assertEquals(getInstantDiff(instant1Milli(), instant2(), Scale.Minute, false), 62584);
	}

	private static Instant instant1Year() {
		return getInstantOf(2021);
	}

	private static Instant instant1Month() {
		return getInstantOf(2021, 11);
	}

	private static Instant instant1Day() {
		return getInstantOf(2021, 11, 18);
	}

	private static Instant instant1Hour() {
		return getInstantOf(2021, 11, 18, 12);
	}

	private static Instant instant1Minute() {
		return getInstantOf(2021, 11, 18, 12, 55);
	}

	private static Instant instant1Second() {
		return getInstantOf(2021, 11, 18, 12, 55, 34);
	}

	private static Instant instant1Milli() {
		return getInstantOf(2021, 11, 18, 12, 55, 34, 236);
	}

	private static Instant instant2() {
		return getInstantOf(2022);
	}

	@Test
	public void instantRange() {

		Instant instant = getInstantOf(2020, 12, 16, 8, 16);

		assertTrue(instantIsInRange(instant, getInstantOf(2020), getInstantOf(2021)));
		assertTrue(instantIsInRange(instant, getInstantOf(2020, 12, 16), getInstantOf(2020, 12, 17)));
		assertFalse(instantIsInRange(instant, getInstantOf(2020, 12, 15), getInstantOf(2020, 12, 16)));
		assertTrue(instantIsInRange(instant, getInstantOf(2020, 12, 16, 8, 16), getInstantOf(2020, 12, 16, 8, 17)));
		assertFalse(instantIsInRange(instant, getInstantOf(2020, 12, 16, 8, 15), getInstantOf(2020, 12, 16, 8, 16)));
	}
}
